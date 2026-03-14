/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.QueryContext;
import com.inerweave.sdk.TransactionContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ProjectActions
implements IRunnableWithProgress {
    public static final int COMPILE_XSLT = 1;
    public static final int MAKE_XSLT_JAR = 2;
    public static final int BUILD_IM_WAR = 4;
    public static final int BUILD_TS_WAR = 8;
    private int operationMask = 0;
    private IProject[] childProjects = null;
    private String errorMessage = null;
    private String allNames = "";
    private boolean defaultStyle = true;
    private boolean splitTransactions = false;
    private IProject currentProject = null;

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public ProjectActions(int operationMask, IProject currentProject, IProject[] childProjects, String allNames, boolean dafaultStyle, boolean splitTransactions) {
        this.operationMask = operationMask;
        this.currentProject = currentProject;
        this.childProjects = childProjects;
        this.allNames = allNames;
        this.defaultStyle = dafaultStyle;
        this.splitTransactions = splitTransactions;
    }

    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        if (monitor.isCanceled()) {
            throw new InterruptedException();
        }
        monitor.beginTask("Project(s) " + this.allNames, 4 + this.childProjects.length * 3);
        ArrayList[] allclasses = new ArrayList[this.childProjects.length + 1];
        if ((this.operationMask & 1) != 0) {
            monitor.subTask("Compiling XSLT for " + this.currentProject.getName());
            allclasses[0] = this.compileXSLT(this.currentProject);
            if (allclasses[0] == null) {
                monitor.done();
                return;
            }
            monitor.worked(1);
            if (monitor.isCanceled()) {
                throw new InterruptedException();
            }
            int i = 0;
            while (i < this.childProjects.length) {
                monitor.subTask("Compiling XSLT for " + this.childProjects[i].getName());
                ConfigContext.loadIwmappingsRoot(this.childProjects[i]);
                allclasses[i + 1] = this.compileXSLT(this.childProjects[i]);
                if (allclasses[i] == null) {
                    monitor.done();
                    return;
                }
                monitor.worked(1);
                if (monitor.isCanceled()) {
                    ConfigContext.loadIwmappingsRoot(this.currentProject);
                    throw new InterruptedException();
                }
                ++i;
            }
            ConfigContext.loadIwmappingsRoot(this.currentProject);
        }
        File[] jarFile = new File[this.childProjects.length + 1];
        if ((this.operationMask & 2) != 0) {
            monitor.subTask("Creating XSLT jar file for " + this.currentProject.getName());
            if (allclasses[0] == null) {
                this.errorMessage = "Unable to create jar without compiling";
                monitor.done();
                return;
            }
            jarFile[0] = this.createXsltJar(this.currentProject, allclasses[0]);
            if (jarFile[0] == null) {
                monitor.done();
                return;
            }
            monitor.worked(1);
            if (monitor.isCanceled()) {
                throw new InterruptedException();
            }
            int i = 0;
            while (i < this.childProjects.length) {
                monitor.subTask("Creating XSLT jar file for " + this.childProjects[i].getName());
                if (allclasses[i + 1] == null) {
                    this.errorMessage = "Unable to create jar without compiling";
                    monitor.done();
                    return;
                }
                jarFile[i + 1] = this.createXsltJar(this.childProjects[i], allclasses[i + 1]);
                if (jarFile[i + 1] == null) {
                    monitor.done();
                    return;
                }
                monitor.worked(1);
                if (monitor.isCanceled()) {
                    throw new InterruptedException();
                }
                ++i;
            }
        }
        if ((this.operationMask & 4) != 0) {
            monitor.subTask("Building IM war file for " + this.currentProject.getName());
            try {
                this.createImWar();
            }
            catch (RuntimeException i) {
                // empty catch block
            }
            if (this.errorMessage != null) {
                monitor.done();
                return;
            }
            monitor.worked(1);
            if (monitor.isCanceled()) {
                throw new InterruptedException();
            }
        }
        if ((this.operationMask & 8) != 0) {
            monitor.subTask("Creating TS war file for " + this.currentProject.getName());
            if (jarFile[0] == null) {
                this.errorMessage = "Unable to build war file without creating jar file";
                monitor.done();
                return;
            }
            this.creatrTSWar(this.currentProject, jarFile[0]);
            if (this.errorMessage != null) {
                monitor.done();
                return;
            }
            monitor.worked(1);
            if (monitor.isCanceled()) {
                throw new InterruptedException();
            }
            int i = 0;
            while (i < this.childProjects.length) {
                monitor.subTask("Creating TS war file for " + this.childProjects[i].getName());
                if (jarFile[i + 1] == null) {
                    this.errorMessage = "Unable to build war file without creating jar file";
                    monitor.done();
                    return;
                }
                this.creatrTSWar(this.childProjects[i], jarFile[i + 1]);
                if (this.errorMessage != null) {
                    monitor.done();
                    return;
                }
                monitor.worked(1);
                if (monitor.isCanceled()) {
                    throw new InterruptedException();
                }
                ++i;
            }
        }
        monitor.done();
    }

    private ArrayList<File> compileXSLT(IProject cpr) {
        String fn = ConfigContext.isHosted() ? "xslt/Site/include/sitetran_host.xslt" : "xslt/Site/include/sitetran_ent.xslt";
        IFile dst = cpr.getFile("xslt/Site/include/sitetran.xslt");
        IFile src = cpr.getFile(fn);
        InputStream ist = null;
        try {
            try {
                ist = src.getContents();
                if (dst.exists()) {
                    dst.delete(true, null);
                }
                dst.create(ist, true, null);
            }
            catch (CoreException e1) {
                this.errorMessage = "Cannot create sitetran.xslt: " + e1.toString();
                ArrayList<File> arrayList = null;
                if (ist != null) {
                    try {
                        ist.close();
                    }
                    catch (IOException e) {
                        this.errorMessage = "Cannot close the input sream: " + e.toString();
                        return null;
                    }
                }
                return arrayList;
            }
        }
        finally {
            if (ist != null) {
                try {
                    ist.close();
                }
                catch (IOException e) {
                    this.errorMessage = "Cannot close the input sream: " + e.toString();
                    return null;
                }
            }
        }
        String trs = "";
        String iwm = "<iwmappings";
        if (ConfigContext.isProductionPackage()) {
            int spi;
            trs = ConfigContext.optimizeTransactions();
            trs = trs.substring(trs.indexOf("<transaction", trs.indexOf(iwm) + iwm.length()), trs.indexOf(String.valueOf(iwm.substring(0, 1)) + "/" + iwm.substring(1)));
            trs = this.splitTransactions ? ((spi = trs.indexOf("<transaction", trs.length() / 2)) >= 0 ? String.valueOf(trs.substring(0, spi)) + "</xsl:template><xsl:template name=\"soltran1\">" + trs.substring(spi) : String.valueOf(trs) + "</xsl:template><xsl:template name=\"soltran1\">") : String.valueOf(trs) + "</xsl:template><xsl:template name=\"soltran1\">";
        }
        dst = cpr.getFile("xslt/Site/new/include/soltran.xslt");
        ByteArrayInputStream bis = null;
        try {
            try {
                if (dst.exists()) {
                    dst.delete(true, null);
                }
                ist = cpr.getFile("xslt/Site/new/include/soltran_start.dat").getContents(true);
                dst.create(ist, true, null);
                bis = new ByteArrayInputStream(trs.getBytes());
                dst.appendContents((InputStream)bis, true, false, null);
                ist.close();
                ist = cpr.getFile("xslt/Site/new/include/soltran_end.dat").getContents(true);
                dst.appendContents(ist, true, false, null);
            }
            catch (IOException e) {
                this.errorMessage = "Cannot create soltran.xslt: " + e.toString();
                ArrayList<File> arrayList = null;
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (ist != null) {
                        ist.close();
                    }
                }
                catch (IOException e2) {
                    this.errorMessage = "Cannot close the input sream: " + e2.toString();
                    return null;
                }
                return arrayList;
            }
            catch (CoreException e1) {
                this.errorMessage = "Cannot create soltran.xslt: " + e1.toString();
                ArrayList<File> arrayList = null;
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (ist != null) {
                        ist.close();
                    }
                }
                catch (IOException e) {
                    this.errorMessage = "Cannot close the input sream: " + e.toString();
                    return null;
                }
                return arrayList;
            }
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (ist != null) {
                    ist.close();
                }
            }
            catch (IOException e) {
                this.errorMessage = "Cannot close the input sream: " + e.toString();
                return null;
            }
        }
        File[] allxslt = ConfigContext.getXsltTransformerFiles(cpr);
        ArrayList<File> allclasses = new ArrayList<File>();
        System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.xsltc.trax.TransformerFactoryImpl");
        String destDir = cpr.getLocation() + "/classes";
        String packageName = "iwtransformationserver";
        File[] fileArray = allxslt;
        int n = 0;
        int n2 = fileArray.length;
        while (n < n2) {
            File cf = fileArray[n];
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setAttribute("generate-translet", Boolean.TRUE);
            factory.setAttribute("package-name", packageName);
            factory.setAttribute("destination-directory", destDir);
            factory.setAttribute("enable-inlining", Boolean.FALSE);
            StreamSource stylesheet = new StreamSource(cf);
            try {
                factory.newTemplates(stylesheet);
            }
            catch (TransformerConfigurationException e) {
                this.errorMessage = "Unable to complile " + cf.getName() + ": " + e.toString();
                return null;
            }
            String afn = cf.getName();
            allclasses.add(new File(String.valueOf(destDir) + "/" + packageName + "/" + afn.replaceFirst(".xslt", ".class")));
            int dp = afn.lastIndexOf(".");
            if (dp > 0) {
                String bafn = String.valueOf(afn.substring(0, dp)) + "$";
                int i = 0;
                while (i < 1000) {
                    File ac = new File(String.valueOf(destDir) + "/" + packageName + "/" + bafn + i + ".class");
                    if (!ac.exists()) break;
                    allclasses.add(ac);
                    ++i;
                }
            }
            ++n;
        }
        return allclasses;
    }

    private File createXsltJar(IProject cpr, ArrayList<File> allclasses) {
        JarOutputStream out;
        byte[] buffer = new byte[10240];
        String jarPath = cpr.getLocation() + "/.deployables/jar/" + "iwsolution_" + cpr.getName() + ".jar";
        File jarFile = new File(jarPath);
        if (jarFile.exists()) {
            jarFile.delete();
        }
        try {
            FileOutputStream foj = new FileOutputStream(jarFile);
            out = new JarOutputStream((OutputStream)foj, new Manifest());
        }
        catch (FileNotFoundException e1) {
            this.errorMessage = "Cannot create jar file: " + e1.toString();
            return null;
        }
        catch (IOException e) {
            this.errorMessage = "Cannot create jar file: " + e.toString();
            return null;
        }
        for (File classFile : allclasses) {
            if (classFile == null || !classFile.exists() || classFile.isDirectory()) {
                this.errorMessage = "Cannot find file to archive: " + classFile.getAbsolutePath();
                return null;
            }
            JarEntry jarAdd = new JarEntry("iwtransformationserver/" + classFile.getName());
            jarAdd.setTime(classFile.lastModified());
            try {
                int nRead;
                out.putNextEntry(jarAdd);
                FileInputStream in = new FileInputStream(classFile);
                while ((nRead = in.read(buffer, 0, buffer.length)) > 0) {
                    out.write(buffer, 0, nRead);
                }
                in.close();
            }
            catch (FileNotFoundException e) {
                this.errorMessage = "Unable to write data to archive: " + e.toString();
                return null;
            }
            catch (IOException e) {
                this.errorMessage = "Unable to write data to archive: " + e.toString();
                return null;
            }
        }
        try {
            out.flush();
            out.close();
        }
        catch (IOException e) {
            this.errorMessage = "Cannot close jar file: " + e.toString();
            return null;
        }
        return jarFile;
    }

    private void createImWar() {
        block19: {
            try {
                ConfigContext.copyFileToProject(this.currentProject, "zip/iwbd_ide.zip", ".deployables/war/iw-business-daemon.war", true);
            }
            catch (IOException e) {
                this.errorMessage = "Cannot copy IM zip file: " + e;
                return;
            }
            catch (CoreException e) {
                this.errorMessage = "Cannot copy IM zip file: " + (Object)((Object)e);
                return;
            }
            try {
                String[] removeJSP = null;
                if (!ConfigContext.isHosted()) {
                    removeJSP = new String[]{"FlowIdAssignment.jsp", "Login.jsp", "Registration.jsp"};
                }
                String oldConfig = "";
                if (!this.defaultStyle) {
                    try {
                        oldConfig = ConfigContext.readFile(this.currentProject.getLocation() + "/configuration/im/config.xml");
                        ConfigContext.saveFileInProject(this.currentProject, "configuration/im/config.old", oldConfig, true);
                        if (!ConfigContext.resetTSUrl(this.currentProject.getName())) {
                            this.errorMessage = "Cannot reset TS URL " + this.currentProject.getName();
                            return;
                        }
                        Vector<TransactionContext> currentTransactionList = ConfigContext.getTransactionList();
                        Vector<QueryContext> currentQueryList = ConfigContext.getQueryList();
                        IProject[] iProjectArray = this.childProjects;
                        int n = 0;
                        int n2 = iProjectArray.length;
                        while (n < n2) {
                            IProject cp = iProjectArray[n];
                            if (ConfigContext.readIMConfigContext(cp)) {
                                if (!ConfigContext.resetTSUrl(cp.getName())) {
                                    this.errorMessage = "Cannot reset TS URL " + cp.getName();
                                    return;
                                }
                            } else {
                                this.errorMessage = "Cannot read IM config for " + cp.getName();
                                return;
                            }
                            Vector<TransactionContext> ctl = ConfigContext.getTransactionList();
                            currentTransactionList.addAll(ctl);
                            Vector<QueryContext> cql = ConfigContext.getQueryList();
                            currentQueryList.addAll(cql);
                            ++n;
                        }
                        if (!ConfigContext.readIMConfigContext(this.currentProject)) {
                            this.errorMessage = "Cannot read IM config for " + this.currentProject.getName();
                            return;
                        }
                        ConfigContext.resetBaseTSUrl(this.currentProject.getName());
                        ConfigContext.setTransactionList(currentTransactionList);
                        ConfigContext.setQueryList(currentQueryList);
                        if (!ConfigContext.writeIMConfigContext(this.currentProject)) {
                            this.errorMessage = "Cannot write IM config for " + this.currentProject.getName();
                            return;
                        }
                    }
                    catch (Exception e) {
                        this.errorMessage = "Cannot create a copy of config.xml file: " + e;
                        return;
                    }
                }
                ConfigContext.modifyJar(this.currentProject.getLocation() + "/.deployables/war/iw-business-daemon", new String[]{this.currentProject.getLocation() + "/configuration/im/config.xml"}, new String[]{"WEB-INF/config.xml"}, removeJSP);
                if (this.defaultStyle) break block19;
                try {
                    ConfigContext.saveFileInProject(this.currentProject, "configuration/im/config.xml", oldConfig, true);
                    this.currentProject.getFile("configuration/im/config.old").delete(true, null);
                }
                catch (CoreException e) {
                    this.errorMessage = "Cannot restore config.xml file from copy: " + (Object)((Object)e);
                    return;
                }
                if (!ConfigContext.readIMConfigContext(this.currentProject)) {
                    this.errorMessage = "Cannot read IM config for " + this.currentProject.getName();
                    return;
                }
            }
            catch (IOException e) {
                this.errorMessage = "Cannot add IM config to war file: " + e;
                return;
            }
        }
    }

    private void creatrTSWar(IProject cpr, File jarFile) {
        String warName = this.defaultStyle ? "iwtransformationserver" : cpr.getName();
        try {
            ConfigContext.copyFileToProject(cpr, "zip/iwts_ide.zip", ".deployables/war/" + warName + ".war", true);
        }
        catch (IOException e) {
            this.errorMessage = "Cannot copy TS zip file: " + e;
            return;
        }
        catch (CoreException e) {
            this.errorMessage = "Cannot copy TS zip file: " + (Object)((Object)e);
            return;
        }
        String[] fileNames = null;
        String[] jarEntries = null;
        if (ConfigContext.isProductionPackage()) {
            fileNames = new String[]{cpr.getLocation() + "/configuration/ts/config.xml", jarFile.getPath()};
            jarEntries = new String[]{"WEB-INF/config.xml", "WEB-INF/lib/" + jarFile.getName()};
        } else {
            String[] stringArray = new String[3];
            stringArray[0] = cpr.getLocation() + "/configuration/ts/config.xml";
            stringArray[1] = jarFile.getPath();
            fileNames = stringArray;
            jarEntries = new String[]{"WEB-INF/config.xml", "WEB-INF/lib/" + jarFile.getName(), "WEB-INF/transactions.xml"};
        }
        int dnp = jarFile.getPath().lastIndexOf(File.separator);
        if (dnp < 0) {
            this.errorMessage = "XSLT Jar file path is corrupted.";
            return;
        }
        String dn = jarFile.getPath().substring(0, dnp);
        File jarDir = new File(dn);
        if (!jarDir.isDirectory()) {
            this.errorMessage = "XSLT Jar file path is corrupted.";
            return;
        }
        File[] jars = jarDir.listFiles();
        if (jars.length > 1) {
            ArrayList<String> fn = new ArrayList<String>();
            ArrayList<String> je = new ArrayList<String>();
            File[] fileArray = jars;
            int n = 0;
            int n2 = fileArray.length;
            while (n < n2) {
                File jf = fileArray[n];
                if (!jf.getName().equals(jarFile.getName())) {
                    fn.add(jf.getPath());
                    je.add("WEB-INF/lib/" + jf.getName());
                }
                ++n;
            }
            String[] fns = fn.toArray(new String[0]);
            String[] jes = je.toArray(new String[0]);
            String[] nfn = new String[fileNames.length + fns.length];
            String[] nje = new String[jarEntries.length + jes.length];
            System.arraycopy(fileNames, 0, nfn, 0, fileNames.length);
            System.arraycopy(fns, 0, nfn, fileNames.length, fns.length);
            System.arraycopy(jarEntries, 0, nje, 0, jarEntries.length);
            System.arraycopy(jes, 0, nje, jarEntries.length, jes.length);
            fileNames = nfn;
            jarEntries = nje;
        }
        try {
            ConfigContext.modifyJar(cpr.getLocation() + "/.deployables/war/" + warName, fileNames, jarEntries, null);
        }
        catch (IOException e) {
            this.errorMessage = "Cannot add files to TS war file: " + e;
            return;
        }
    }
}

