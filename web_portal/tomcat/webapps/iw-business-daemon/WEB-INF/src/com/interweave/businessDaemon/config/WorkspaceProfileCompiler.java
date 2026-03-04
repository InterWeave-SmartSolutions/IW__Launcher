package com.interweave.businessDaemon.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Generates a per-profile engine overlay from wizard-saved SF2QBConfiguration XML.
 *
 * This is a practical local replacement for the unavailable legacy InterWeave
 * backend compiler. It keeps the original engine template intact and emits
 * a generated profile project that the IDE can inspect.
 */
public final class WorkspaceProfileCompiler {

    private static final String GENERATED_ROOT = "GeneratedProfiles";
    private static final String DEFAULT_RUNTIME_TEMPLATE = "SF2AuthNet";
    private static final String DEFAULT_TS_BASE = "http://localhost:9090/iwtransformationserver";
    private static final String DEFAULT_TS_FAILOVER = "";

    private WorkspaceProfileCompiler() {
    }

    public static CompileResult compileProfile(ServletContext servletContext,
                                               String profileName,
                                               String solutionType,
                                               String configXml) throws IOException {
        if (profileName == null || profileName.trim().isEmpty()) {
            throw new IOException("Profile name is required");
        }

        WorkspaceProfileSyncSupport.MirrorResult mirror =
            WorkspaceProfileSyncSupport.exportProfile(servletContext, profileName, solutionType, configXml);
        File repoRoot = WorkspaceProfileSyncSupport.resolveRepoRoot(servletContext);
        File workspaceRoot = new File(repoRoot, "workspace");
        String safeProfile = WorkspaceProfileSyncSupport.sanitizeProfileKey(profileName);
        String mappedProject = WorkspaceProfileSyncSupport.resolveMappedProjectName(repoRoot, solutionType);
        String templateProject = resolveTemplateProject(workspaceRoot, mappedProject);

        ProfileValues profileValues = ProfileValues.fromXml(LocalUserManagementServlet.sanitizeFullConfig(configXml));
        EndpointSettings endpoints = loadEndpointSettings(repoRoot);

        File generatedRoot = new File(new File(workspaceRoot, GENERATED_ROOT), safeProfile);
        ensureDir(generatedRoot);
        ensureDir(new File(generatedRoot, "configuration"));
        ensureDir(new File(new File(generatedRoot, "configuration"), "im"));
        ensureDir(new File(new File(generatedRoot, "configuration"), "ts"));
        ensureDir(new File(new File(generatedRoot, "configuration"), "profile"));
        ensureDir(new File(new File(generatedRoot, "xslt"), "Site\\new\\xml"));

        writeUtf8(new File(generatedRoot, ".project"), createProjectXml(safeProfile));
        writeUtf8(new File(generatedRoot, "README.md"), createReadme(profileName, solutionType, mappedProject, templateProject));

        File templateProjectRoot = new File(workspaceRoot, templateProject);
        File templateIm = new File(new File(new File(templateProjectRoot, "configuration"), "im"), "config.xml");
        File templateTs = new File(new File(new File(templateProjectRoot, "configuration"), "ts"), "config.xml");
        File templateTransactionsXml = new File(new File(new File(new File(new File(templateProjectRoot, "xslt"), "Site"), "new"), "xml"), "transactions.xml");
        File templateTransactionsXslt = new File(new File(new File(new File(templateProjectRoot, "xslt"), "Site"), "new"), "transactions.xslt");

        File outIm = new File(new File(new File(generatedRoot, "configuration"), "im"), "config.xml");
        File outTs = new File(new File(new File(generatedRoot, "configuration"), "ts"), "config.xml");
        File outProfileXml = new File(new File(new File(generatedRoot, "configuration"), "profile"), "company_configuration.xml");
        File outProfileProperties = new File(new File(new File(generatedRoot, "configuration"), "profile"), "profile.properties");
        File outSelectionProperties = new File(new File(new File(generatedRoot, "configuration"), "profile"), "compiler-selection.properties");
        File outTransactionsXml = new File(new File(new File(new File(new File(generatedRoot, "xslt"), "Site"), "new"), "xml"), "transactions.xml");
        File outSelectionXml = new File(new File(new File(new File(new File(generatedRoot, "xslt"), "Site"), "new"), "xml"), "compiler-selection.xml");
        File outTransactionsXslt = new File(new File(new File(new File(generatedRoot, "xslt"), "Site"), "new"), "transactions.xslt");

        Document imDoc = parseXml(readUtf8(templateIm));
        CompilerSelection selection =
            applyEngineSettings(imDoc, safeProfile, solutionType, endpoints, profileValues);
        writeXml(outIm, imDoc);

        Document tsDoc = parseXml(readUtf8(templateTs));
        applyTsSettings(tsDoc, safeProfile);
        writeXml(outTs, tsDoc);

        writeUtf8(outProfileXml, LocalUserManagementServlet.sanitizeFullConfig(configXml));
        storeProperties(outProfileProperties, buildCompilerProperties(profileName, solutionType, mappedProject,
            templateProject, endpoints, profileValues, mirror, selection));
        storeProperties(outSelectionProperties, buildSelectionProperties(selection));

        copyIfPresent(templateTransactionsXml, outTransactionsXml);
        writeUtf8(outSelectionXml, buildSelectionXml(selection));
        copyIfPresent(templateTransactionsXslt, outTransactionsXslt);

        return new CompileResult(profileName, solutionType, templateProject, mappedProject,
            relativize(repoRoot, generatedRoot), relativize(repoRoot, outIm));
    }

    private static CompilerSelection applyEngineSettings(Document doc, String safeProfile,
                                                         String solutionType,
                                                         EndpointSettings endpoints,
                                                         ProfileValues values) {
        Element root = doc.getDocumentElement();
        root.setAttribute("Name", "BD_" + safeProfile);
        root.setAttribute("PrimaryTSURL", endpoints.tsBase);
        root.setAttribute("PrimaryTSURLT", endpoints.tsBase);
        root.setAttribute("PrimaryTSURL1", endpoints.tsBase);
        root.setAttribute("PrimaryTSURLT1", endpoints.tsBase);
        root.setAttribute("PrimaryTSURLD", endpoints.tsBase);
        root.setAttribute("FailoverURL", endpoints.failover);
        root.setAttribute("RunAtStartUp", "0");

        boolean sandbox = "yes".equalsIgnoreCase(values.get("SandBoxUsed"));
        String sfUrl = sandbox
            ? "https://test.salesforce.com/services/Soap/u/47.0"
            : "https://login.salesforce.com/services/Soap/u/47.0";
        CompilerSelection selection = new CompilerSelection(resolveCompilerModule(solutionType));

        NodeList allNodes = root.getChildNodes();
        for (int i = 0; i < allNodes.getLength(); i++) {
            Node node = allNodes.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element item = (Element) node;
            if (!"TransactionDescription".equals(item.getTagName()) && !"Query".equals(item.getTagName())) {
                continue;
            }
            boolean active = shouldEnableItem(selection.moduleName, item.getTagName(),
                item.getAttribute("Id"), values);
            item.setAttribute("IsActive", active ? "true" : "false");
            if ("TransactionDescription".equals(item.getTagName()) && !active) {
                item.setAttribute("RunAtStartUp", "false");
            }
            normalizeItemEndpoints(item);
            applyParameterValues(item, values, sfUrl);
            selection.record(item.getTagName(), item.getAttribute("Id"), active);
        }
        return selection;
    }

    private static void normalizeItemEndpoints(Element item) {
        item.setAttribute("PrimaryTSURL", "");
        item.setAttribute("SecondaryTSURL", "");
        item.setAttribute("PrimaryTSURLT", "");
        item.setAttribute("SecondaryTSURLT", "");
        item.setAttribute("PrimaryTSURL1", "null");
        item.setAttribute("SecondaryTSURL1", "null");
        item.setAttribute("PrimaryTSURLT1", "null");
        item.setAttribute("SecondaryTSURLT1", "null");
        item.setAttribute("PrimaryTSURLD", "");
        item.setAttribute("SecondaryTSURLD", "");
    }

    private static void applyParameterValues(Element item, ProfileValues values, String sfUrl) {
        NodeList nodes = item.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element child = (Element) node;
            if (!"Parameter".equals(child.getTagName())) {
                continue;
            }
            String name = child.getAttribute("Name");
            if ("SFURL".equals(name)) {
                child.setAttribute("Value", sfUrl);
            } else if ("TransactionSourceName".equals(name) && values.has("SourceName")) {
                child.setAttribute("Value", values.get("SourceName"));
            } else if ("ReturnString".equals(name) && values.has("Env2Con")) {
                child.setAttribute("Value", "?Env2Con=" + values.get("Env2Con"));
            } else if ("TestMode".equals(name)) {
                child.setAttribute("Value", "yes".equalsIgnoreCase(values.get("SandBoxUsed")) ? "true" : "");
            }
        }
    }

    private static void applyTsSettings(Document doc, String safeProfile) {
        Element root = doc.getDocumentElement();
        root.setAttribute("Name", "TS_" + safeProfile);
        root.setAttribute("IsPrimary", "1");
        root.setAttribute("IsHosted", "0");
    }

    private static Properties buildCompilerProperties(String profileName,
                                                      String solutionType,
                                                      String mappedProject,
                                                      String templateProject,
                                                      EndpointSettings endpoints,
                                                      ProfileValues values,
                                                      WorkspaceProfileSyncSupport.MirrorResult mirror,
                                                      CompilerSelection selection) {
        Properties p = new Properties();
        p.setProperty("profile_name", profileName);
        p.setProperty("solution_type", solutionType != null ? solutionType : "");
        p.setProperty("mapped_project", mappedProject != null ? mappedProject : "");
        p.setProperty("template_project", templateProject);
        p.setProperty("ts_base_url", endpoints.tsBase);
        p.setProperty("failover_url", endpoints.failover);
        p.setProperty("compiler_module", selection.moduleName);
        p.setProperty("active_transaction_count", String.valueOf(selection.activeTransactions.size()));
        p.setProperty("inactive_transaction_count", String.valueOf(selection.inactiveTransactions.size()));
        p.setProperty("active_query_count", String.valueOf(selection.activeQueries.size()));
        p.setProperty("inactive_query_count", String.valueOf(selection.inactiveQueries.size()));
        p.setProperty("sync_mirror_path", mirror.syncPath != null ? mirror.syncPath : "");
        p.setProperty("project_mirror_path", mirror.projectPath != null ? mirror.projectPath : "");

        addIfPresent(p, "SyncTypeAC", values);
        addIfPresent(p, "SyncTypeSO", values);
        addIfPresent(p, "SyncTypeInv", values);
        addIfPresent(p, "SyncTypeSR", values);
        addIfPresent(p, "SyncTypePrd", values);
        addIfPresent(p, "SFIntUsr", values);
        addIfPresent(p, "SFPswd", values);
        addIfPresent(p, "QDSN0", values);
        addIfPresent(p, "QBIntUsr0", values);
        addIfPresent(p, "QBPswd0", values);
        addIfPresent(p, "Env2Con", values);
        addIfPresent(p, "SandBoxUsed", values);
        return p;
    }

    private static void addIfPresent(Properties p, String key, ProfileValues values) {
        if (values.has(key)) {
            p.setProperty(key, values.get(key));
        }
    }

    private static Properties buildSelectionProperties(CompilerSelection selection) {
        Properties p = new Properties();
        p.setProperty("compiler_module", selection.moduleName);
        p.setProperty("active_transactions", join(selection.activeTransactions));
        p.setProperty("inactive_transactions", join(selection.inactiveTransactions));
        p.setProperty("active_queries", join(selection.activeQueries));
        p.setProperty("inactive_queries", join(selection.inactiveQueries));
        p.setProperty("active_transaction_count", String.valueOf(selection.activeTransactions.size()));
        p.setProperty("inactive_transaction_count", String.valueOf(selection.inactiveTransactions.size()));
        p.setProperty("active_query_count", String.valueOf(selection.activeQueries.size()));
        p.setProperty("inactive_query_count", String.valueOf(selection.inactiveQueries.size()));
        return p;
    }

    private static String buildSelectionXml(CompilerSelection selection) {
        StringBuilder sb = new StringBuilder();
        sb.append("<compilerSelection module=\"").append(selection.moduleName).append("\">");
        appendItemsXml(sb, "transaction", "active", selection.activeTransactions);
        appendItemsXml(sb, "transaction", "inactive", selection.inactiveTransactions);
        appendItemsXml(sb, "query", "active", selection.activeQueries);
        appendItemsXml(sb, "query", "inactive", selection.inactiveQueries);
        sb.append("</compilerSelection>");
        return sb.toString();
    }

    private static void appendItemsXml(StringBuilder sb, String tagName, String state, java.util.List<String> ids) {
        sb.append("<").append(tagName).append("Set state=\"").append(state).append("\">");
        for (int i = 0; i < ids.size(); i++) {
            sb.append("<").append(tagName).append(" id=\"").append(ids.get(i)).append("\"/>");
        }
        sb.append("</").append(tagName).append("Set>");
    }

    private static String resolveCompilerModule(String solutionType) {
        String normalized = trim(solutionType).toUpperCase();
        if ("CRM2QB3".equals(normalized)) {
            return "CRM2QB3";
        }
        return "GENERIC";
    }

    private static boolean shouldEnableItem(String moduleName, String tagName, String id, ProfileValues values) {
        if ("CRM2QB3".equals(moduleName)) {
            return shouldEnableCrm2qb3Item(tagName, id, values);
        }
        return true;
    }

    private static boolean shouldEnableCrm2qb3Item(String tagName, String id, ProfileValues values) {
        boolean accountEnabled = isSyncEnabled(values.get("SyncTypeAC"));
        boolean salesEnabled = isSyncEnabled(values.get("SyncTypeSO"));
        boolean inventoryEnabled = isSyncEnabled(values.get("SyncTypeInv"));
        boolean serviceEnabled = isSyncEnabled(values.get("SyncTypeSR"));
        boolean productEnabled = isSyncEnabled(values.get("SyncTypePrd"));
        boolean anyEnabled = accountEnabled || salesEnabled || inventoryEnabled || serviceEnabled || productEnabled;

        if ("TransactionDescription".equals(tagName)) {
            if ("BPMTransactions2Auth".equals(id)
                || "SFTransactions2Auth".equals(id)
                || "SFTransactions2AuthN".equals(id)
                || "SFTransactions2AuthDRS".equals(id)) {
                return anyEnabled;
            }
            return false;
        }

        if (id == null || id.length() == 0) {
            return anyEnabled;
        }

        if (id.startsWith("Creatio2")) {
            return accountEnabled || salesEnabled;
        }
        if (id.startsWith("SFAcct") || id.startsWith("SFLead")) {
            return accountEnabled;
        }
        if (id.startsWith("SFOpp") || id.startsWith("SFSO") || id.startsWith("SFTran") || id.startsWith("SFCO")) {
            return salesEnabled;
        }
        if (id.startsWith("Sugar")) {
            return false;
        }
        if (id.contains("Prod") || id.contains("Item")) {
            return productEnabled;
        }
        if (id.contains("Inv")) {
            return inventoryEnabled;
        }
        if (id.contains("SR") || id.contains("Service")) {
            return serviceEnabled;
        }
        return anyEnabled;
    }

    private static boolean isSyncEnabled(String syncValue) {
        String normalized = trim(syncValue);
        return normalized.length() > 0 && !"None".equalsIgnoreCase(normalized);
    }

    private static String join(java.util.List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(items.get(i));
        }
        return sb.toString();
    }

    private static String resolveTemplateProject(File workspaceRoot, String mappedProject) throws IOException {
        if (mappedProject != null && mappedProject.length() > 0) {
            File mappedIm = new File(new File(new File(new File(workspaceRoot, mappedProject), "configuration"), "im"), "config.xml");
            if (mappedIm.isFile() && hasRuntimeDefinitions(mappedIm)) {
                return mappedProject;
            }
        }
        File fallbackIm = new File(new File(new File(new File(workspaceRoot, DEFAULT_RUNTIME_TEMPLATE), "configuration"), "im"), "config.xml");
        if (!fallbackIm.isFile()) {
            throw new IOException("Runtime template project not found: " + DEFAULT_RUNTIME_TEMPLATE);
        }
        return DEFAULT_RUNTIME_TEMPLATE;
    }

    private static boolean hasRuntimeDefinitions(File xmlFile) {
        try {
            Document doc = parseXml(readUtf8(xmlFile));
            Element root = doc.getDocumentElement();
            return root.getElementsByTagName("TransactionDescription").getLength() > 0
                || root.getElementsByTagName("Query").getLength() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static EndpointSettings loadEndpointSettings(File repoRoot) throws IOException {
        Properties env = new Properties();
        File envFile = new File(repoRoot, ".env");
        if (envFile.isFile()) {
            InputStream in = null;
            try {
                in = new BufferedInputStream(new FileInputStream(envFile));
                env.load(in);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }

        String mode = trim(env.getProperty("TS_MODE"));
        if (mode.length() == 0) {
            mode = "local";
        }
        String tsBase = "legacy".equalsIgnoreCase(mode)
            ? trim(env.getProperty("TS_BASE_LEGACY"))
            : trim(env.getProperty("TS_BASE_LOCAL"));
        String failover = "legacy".equalsIgnoreCase(mode)
            ? trim(env.getProperty("TS_FAILOVER_LEGACY"))
            : trim(env.getProperty("TS_FAILOVER_LOCAL"));
        if (tsBase.length() == 0) {
            tsBase = DEFAULT_TS_BASE;
        }
        if (failover.length() == 0) {
            failover = DEFAULT_TS_FAILOVER;
        }
        return new EndpointSettings(tsBase, failover, mode);
    }

    private static Document parseXml(String xml) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new IOException("Failed to parse XML", e);
        }
    }

    private static void writeXml(File file, Document doc) throws IOException {
        OutputStream out = null;
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            out = new BufferedOutputStream(new FileOutputStream(file));
            transformer.transform(new DOMSource(doc), new StreamResult(out));
        } catch (Exception e) {
            throw new IOException("Failed to write XML: " + file.getAbsolutePath(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static void storeProperties(File file, Properties p) throws IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            p.store(out, "Generated by WorkspaceProfileCompiler");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static void copyIfPresent(File source, File target) throws IOException {
        if (!source.isFile()) {
            return;
        }
        writeUtf8(target, readUtf8(source));
    }

    private static void ensureDir(File dir) throws IOException {
        if (!dir.isDirectory() && !dir.mkdirs()) {
            throw new IOException("Could not create directory: " + dir.getAbsolutePath());
        }
    }

    private static void writeUtf8(File file, String content) throws IOException {
        OutputStream out = null;
        OutputStreamWriter writer = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(content);
        } finally {
            if (writer != null) {
                writer.close();
            } else if (out != null) {
                out.close();
            }
        }
    }

    private static String readUtf8(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        Reader reader = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            reader = new InputStreamReader(in, "UTF-8");
            char[] buf = new char[4096];
            int read;
            while ((read = reader.read(buf)) >= 0) {
                sb.append(buf, 0, read);
            }
        } finally {
            if (reader != null) {
                reader.close();
            } else if (in != null) {
                in.close();
            }
        }
        return sb.toString();
    }

    private static String createProjectXml(String safeProfile) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<projectDescription>\n"
            + "  <name>GP_" + safeProfile + "</name>\n"
            + "  <comment></comment>\n"
            + "  <projects></projects>\n"
            + "  <buildSpec></buildSpec>\n"
            + "  <natures></natures>\n"
            + "</projectDescription>\n";
    }

    private static String createReadme(String profileName, String solutionType,
                                       String mappedProject, String templateProject) {
        return "# Generated Profile Compiler Output\n\n"
            + "This project is generated from wizard-saved `SF2QBConfiguration` for:\n\n"
            + "- Profile: `" + profileName + "`\n"
            + "- Solution: `" + (solutionType != null ? solutionType : "") + "`\n"
            + "- Mapped project: `" + (mappedProject != null ? mappedProject : "") + "`\n"
            + "- Engine template: `" + templateProject + "`\n\n"
            + "Do not edit this by hand if you want the wizard to remain the source of truth.\n";
    }

    private static String trim(String value) {
        return value != null ? value.trim() : "";
    }

    private static String relativize(File repoRoot, File file) {
        String root = repoRoot.getAbsolutePath();
        String path = file.getAbsolutePath();
        if (path.startsWith(root)) {
            String rel = path.substring(root.length());
            if (rel.startsWith("\\") || rel.startsWith("/")) {
                return rel.substring(1);
            }
            return rel;
        }
        return path;
    }

    private static final class EndpointSettings {
        final String tsBase;
        final String failover;
        final String mode;

        EndpointSettings(String tsBase, String failover, String mode) {
            this.tsBase = tsBase;
            this.failover = failover;
            this.mode = mode;
        }
    }

    private static final class CompilerSelection {
        final String moduleName;
        final java.util.List<String> activeTransactions = new java.util.ArrayList<String>();
        final java.util.List<String> inactiveTransactions = new java.util.ArrayList<String>();
        final java.util.List<String> activeQueries = new java.util.ArrayList<String>();
        final java.util.List<String> inactiveQueries = new java.util.ArrayList<String>();

        CompilerSelection(String moduleName) {
            this.moduleName = moduleName;
        }

        void record(String tagName, String id, boolean active) {
            if (id == null || id.length() == 0) {
                return;
            }
            if ("TransactionDescription".equals(tagName)) {
                (active ? activeTransactions : inactiveTransactions).add(id);
            } else if ("Query".equals(tagName)) {
                (active ? activeQueries : inactiveQueries).add(id);
            }
        }
    }

    private static final class ProfileValues {
        final Map<String, String> values = new LinkedHashMap<String, String>();

        static ProfileValues fromXml(String xml) throws IOException {
            ProfileValues p = new ProfileValues();
            Document doc = parseXml(xml);
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node instanceof Element) {
                    Element element = (Element) node;
                    p.values.put(element.getTagName(), readElementText(element));
                }
            }
            return p;
        }

        String get(String key) {
            String value = values.get(key);
            return value != null ? value : "";
        }

        boolean has(String key) {
            return values.containsKey(key) && get(key).length() > 0;
        }

        private static String readElementText(Element element) {
            StringBuilder sb = new StringBuilder();
            Node child = element.getFirstChild();
            while (child != null) {
                short type = child.getNodeType();
                if (type == Node.TEXT_NODE || type == Node.CDATA_SECTION_NODE) {
                    String value = child.getNodeValue();
                    if (value != null) {
                        sb.append(value);
                    }
                }
                child = child.getNextSibling();
            }
            return sb.toString().trim();
        }
    }

    public static final class CompileResult {
        public final String profileName;
        public final String solutionType;
        public final String templateProject;
        public final String mappedProject;
        public final String generatedRoot;
        public final String engineConfigPath;

        CompileResult(String profileName, String solutionType, String templateProject,
                      String mappedProject, String generatedRoot, String engineConfigPath) {
            this.profileName = profileName;
            this.solutionType = solutionType;
            this.templateProject = templateProject;
            this.mappedProject = mappedProject;
            this.generatedRoot = generatedRoot;
            this.engineConfigPath = engineConfigPath;
        }
    }
}
