/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.lotus;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.IWMappingType;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import com.interweave.encrypt.IWIEncrypt;
import com.interweave.lotus.IWColDef;
import com.interweave.lotus.IWNullListner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.sql.RowSetListener;
import sun.jdbc.rowset.JdbcRowSet;

public class IWSQLLotus
implements IWIDataMap {
    protected Connection conn = null;
    protected PreparedStatement pstmt = null;
    protected JdbcRowSet jrs = null;
    protected ResultSet rs = null;
    protected Access curAccess = null;
    protected String strTransform = null;
    protected Vector colNames = new Vector();
    protected Vector colValues = new Vector();
    protected int cols = 0;
    protected String strCountry = "";
    protected String strState = "";
    protected String tempCountry = "";
    protected String tempState = "";
    public IWRequest iwRequest = null;
    public ByteArrayOutputStream strmBytes = new ByteArrayOutputStream();
    public DataOutputStream strmDataType = new DataOutputStream(this.strmBytes);
    public Vector colList = new Vector();
    public IWIEncrypt iwCompress = null;
    protected Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();
    public boolean escapeTags = false;
    public int dataMapID = 0;
    public int totalRows = 0;
    public StringBuffer dataBuffer = null;
    public StringBuffer mapBuffer = null;
    public String strDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
    public String strDSN = "jdbc:odbc:InterWeave";
    public String strUser = "DBA";
    public String strPWD = "sql";
    public String strMode = "default";
    public String strSQL = "";
    protected StringBuffer buffer = null;
    protected String strRet = null;
    protected IWRequest curRequest = null;

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public void closeConnection() {
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    private String createParams(String strSql) {
        String retSQL = strSql;
        String strData = null;
        StringBuffer buffer = new StringBuffer();
        Values values = null;
        values = this.curAccess.getValues();
        if (values != null) {
            List parameters = values.getParameter();
            int paramCount = 0;
            int paramData = 0;
            while (paramData < strSql.length()) {
                char ch;
                if ((ch = strSql.charAt(paramData++)) == '?') {
                    try {
                        Parameter param = (Parameter)((Object)parameters.get(paramCount++));
                        Mapping mapping = param.getMapping();
                        boolean quoted = mapping.getQuoted().compareToIgnoreCase("true") == 0;
                        IWMappingType map = new IWMappingType();
                        strData = map.getData(param, this.iwRequest);
                        if (quoted) {
                            strData = "'" + strData + "'";
                        }
                        buffer.append(strData);
                    }
                    catch (Exception exception) {}
                    continue;
                }
                buffer.append(ch);
            }
            retSQL = buffer.toString();
            buffer = null;
        }
        return retSQL;
    }

    public void scrollCursor(ResultSet crs) throws SQLException {
        String strData;
        ResultSetMetaData rsm;
        int rowcount;
        block9: {
            rowcount = 0;
            rsm = crs.getMetaData();
            this.dataBuffer = new StringBuffer();
            int cols = rsm.getColumnCount();
            try {
                while (crs.next()) {
                    ++this.totalRows;
                    this.dataBuffer.append("        <row number=\"" + ++rowcount + "\">\n");
                    int i = 1;
                    while (i <= cols) {
                        this.dataBuffer.append("          <col number=\"" + i + "\" name=\"" + rsm.getColumnName(i) + "\">");
                        strData = crs.getString(i);
                        if (crs.wasNull()) {
                            strData = "";
                        } else if (strData.compareToIgnoreCase("null") == 0) {
                            strData = "";
                        }
                        this.dataBuffer.append(String.valueOf(strData.trim()) + "</col>\n");
                        ++i;
                    }
                    this.dataBuffer.append("        </row>\n");
                }
            }
            catch (SQLException ex) {
                strData = this.dataBuffer.toString();
                if (strData.endsWith("</row>\n")) break block9;
                this.dataBuffer.append("        </row>\n");
            }
        }
        this.mapBuffer.append("      <data rowcount=\"" + rowcount + "\">\n");
        String tt = this.dataBuffer.toString();
        this.mapBuffer.append(tt);
        this.mapBuffer.append("      </data>\n");
        this.dataBuffer = null;
        strData = null;
        rsm = null;
    }

    private void executeSql(String strSQL) throws Exception {
        strSQL = this.createParams(strSQL);
        try {
            this.execute(strSQL);
        }
        catch (Exception e) {
            this.iwRequest.lnkIWServices.logError("XmlsqParams.select " + e.getMessage(), IWServices.LOG_ERRORS, e, null);
            throw e;
        }
    }

    private void defaultAccess() throws Exception {
        Enumeration enumerate = this.accessList.elements();
        if (enumerate.hasMoreElements()) {
            Access access = (Access)((Object)enumerate.nextElement());
            this.curAccess = null;
            this.iwRequest.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".select", IWServices.LOG_TRACE, this.iwRequest);
            if (access != null) {
                this.curAccess = access;
                String testsql = "";
                testsql = this.curAccess.getStatementpre();
                System.out.println("\n\n testsql:  \n\n" + testsql);
                if (testsql.startsWith("select")) {
                    this.executeSql(this.curAccess.getStatementpre());
                } else {
                    this.executeSql(this.fileToString("message.xml"));
                }
            }
        }
    }

    private void select() throws Exception {
        Access access = (Access)((Object)this.accessList.get("select"));
        this.curAccess = null;
        this.iwRequest.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".select", IWServices.LOG_TRACE, this.iwRequest);
        if (access != null) {
            this.curAccess = access;
            this.executeSql(this.curAccess.getStatementpre());
        }
    }

    private void insert() throws Exception {
        Access access = (Access)((Object)this.accessList.get("insert"));
        this.curAccess = null;
        this.iwRequest.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".select", IWServices.LOG_TRACE, this.iwRequest);
        if (access != null) {
            this.curAccess = access;
            System.out.println("\n\n in insert \n\n");
            this.execute(this.fileToString("message.xml"));
        }
    }

    private void update() throws Exception {
        Access access = (Access)((Object)this.accessList.get("update"));
        this.curAccess = null;
        this.iwRequest.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".select", IWServices.LOG_TRACE, this.iwRequest);
        if (access != null) {
            this.curAccess = access;
            this.execute(this.fileToString("message.xml"));
        }
    }

    private void delete() throws Exception {
        Access access = (Access)((Object)this.accessList.get("delete"));
        this.curAccess = null;
        this.iwRequest.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".select", IWServices.LOG_TRACE, this.iwRequest);
        if (access != null) {
            this.curAccess = access;
            this.execute(this.fileToString("message.xml"));
        }
    }

    private void procedure() throws Exception {
        boolean needsMulti = false;
        boolean multiQuoted = false;
        boolean isFirst = true;
        Object strClause = null;
        String strData = null;
        Access access = (Access)((Object)this.accessList.get("procedure"));
        StringBuffer buffer = new StringBuffer();
        StringBuffer bufferStart = new StringBuffer();
        StringBuffer bufferEnd = new StringBuffer();
        Values values = null;
        Vector<String> multiParam = new Vector<String>();
        this.curAccess = null;
        this.iwRequest.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".procedure", IWServices.LOG_TRACE, this.iwRequest);
        if (access != null) {
            this.curAccess = access;
            this.strSQL = "{call " + access.getStatementpre();
            values = access.getValues();
            if (values != null) {
                buffer.append(" (");
                Iterator iterate = values.getParameter().iterator();
                while (iterate.hasNext()) {
                    boolean quoted;
                    strData = null;
                    if (!isFirst) {
                        buffer.append(", ");
                        if (needsMulti) {
                            bufferEnd.append(", ");
                        }
                    }
                    Parameter param = (Parameter)((Object)iterate.next());
                    Mapping mapping = param.getMapping();
                    String type = mapping.getType();
                    boolean bl = quoted = mapping.getQuoted().compareToIgnoreCase("true") == 0;
                    if (type != null && type != "") {
                        if (type.compareToIgnoreCase("Transform") == 0) {
                            this.strTransform = param.getInput();
                            continue;
                        }
                        if (type.compareToIgnoreCase("TransformEscapeTags") == 0) {
                            this.escapeTags = true;
                            this.strTransform = param.getInput();
                            continue;
                        }
                    }
                    IWMappingType map = new IWMappingType();
                    strData = map.getData(param, this.iwRequest);
                    if (quoted && strData.length() <= 6000) {
                        strData = "'" + strData + "'";
                    }
                    isFirst = false;
                    if (strData.length() > 6000) {
                        needsMulti = true;
                        if (quoted) {
                            multiQuoted = true;
                        }
                        bufferStart.append(String.valueOf(this.strSQL) + buffer.toString());
                        int end = 6000;
                        boolean isDone = false;
                        while (!isDone) {
                            String strTemp = null;
                            if (end < strData.length()) {
                                strTemp = strData.substring(0, end);
                                strData = strData.substring(end);
                                multiParam.add(strTemp);
                                continue;
                            }
                            isDone = true;
                            if (strData.length() <= 0) continue;
                            multiParam.add(strData);
                        }
                    } else if (needsMulti) {
                        bufferEnd.append(strData);
                    }
                    buffer.append(strData);
                }
                buffer.append(")}");
                if (needsMulti) {
                    bufferEnd.append(")}");
                }
                this.strSQL = String.valueOf(this.strSQL) + buffer.toString();
                buffer = null;
            } else {
                this.strSQL = String.valueOf(this.strSQL) + " }";
            }
            this.iwRequest.lnkIWServices.logConsole(this.strSQL, IWServices.LOG_REQUEST, this.iwRequest);
            if (needsMulti) {
                Enumeration enumerate = multiParam.elements();
                while (enumerate.hasMoreElements()) {
                    strData = (String)enumerate.nextElement();
                    if (multiQuoted) {
                        strData = "'" + strData + "'";
                    }
                    this.strSQL = String.valueOf(bufferStart.toString()) + strData + bufferEnd.toString();
                    try {
                        this.execute(this.strSQL);
                    }
                    catch (Exception e) {
                        String message = e.getMessage();
                        if (message.indexOf("No ResultSet was produced") == -1) {
                            this.iwRequest.lnkIWServices.logError("XmlSql.procedure " + message, IWServices.LOG_ERRORS, e, null);
                            throw e;
                        }
                        this.iwRequest.lnkIWServices.logConsole("XmlSql.procedure " + message, IWServices.LOG_DATA, this.iwRequest);
                    }
                }
            } else {
                try {
                    this.execute(this.strSQL);
                }
                catch (Exception e) {
                    String message = e.getMessage();
                    if (message.indexOf("No ResultSet was produced") == -1) {
                        this.iwRequest.lnkIWServices.logError("XmlSql.procedure " + message, IWServices.LOG_ERRORS, e, null);
                        throw e;
                    }
                    this.iwRequest.lnkIWServices.logConsole("XmlSql.procedure " + message, IWServices.LOG_DATA, this.iwRequest);
                }
            }
        } else {
            throw new Exception("XmlSql.procedure No Access Object Error");
        }
    }

    public StringBuffer go(IWRequest request) throws Exception {
        block43: {
            String encryptStr = request.getParameter("encryptclass");
            request.lnkIWServices.logConsole("IWXmlSql.go", IWServices.LOG_TRACE, request);
            this.iwRequest = request;
            this.iwCompress = null;
            if (encryptStr != null && encryptStr.length() > 0) {
                this.iwCompress = (IWIEncrypt)request.lnkIWServices.getObject(encryptStr);
            }
            this.strTransform = null;
            this.mapBuffer = null;
            this.mapBuffer = new StringBuffer();
            try {
                String message;
                this.curRequest = request;
                this.strRet = null;
                this.jrs = new JdbcRowSet();
                this.jrs.addRowSetListener((RowSetListener)new IWNullListner());
                this.jrs.setUrl(this.strDSN);
                this.jrs.setUsername(this.strUser);
                this.jrs.setPassword(this.strPWD);
                this.strMode = request.getParameter("actiontype");
                request.lnkIWServices.logConsole(this.strMode, IWServices.LOG_TRACE, request);
                if (this.strMode != null && this.strMode.length() > 0) {
                    if (this.strMode.compareToIgnoreCase("select") == 0) {
                        try {
                            this.select();
                        }
                        catch (Exception e) {
                            try {
                                this.defaultAccess();
                                break block43;
                            }
                            catch (Exception ex) {
                                String message2 = ex.getMessage();
                                if (message2.indexOf("No ResultSet was produced") == -1) {
                                    request.lnkIWServices.logError("XmlSql.go " + message2, IWServices.LOG_ERRORS, ex, null);
                                    throw e;
                                }
                                request.lnkIWServices.logConsole("XmlSql.go " + message2, IWServices.LOG_DATA, request);
                            }
                        }
                        break block43;
                    }
                    if (this.strMode.compareToIgnoreCase("insert") == 0) {
                        try {
                            this.insert();
                        }
                        catch (Exception e) {
                            try {
                                this.defaultAccess();
                                break block43;
                            }
                            catch (Exception ex) {
                                String message3 = ex.getMessage();
                                if (message3.indexOf("No ResultSet was produced") == -1) {
                                    request.lnkIWServices.logError("XmlSql.go " + message3, IWServices.LOG_ERRORS, ex, null);
                                    throw e;
                                }
                                request.lnkIWServices.logConsole("XmlSql.go " + message3, IWServices.LOG_DATA, request);
                            }
                        }
                        break block43;
                    }
                    if (this.strMode.compareToIgnoreCase("update") == 0) {
                        try {
                            this.update();
                        }
                        catch (Exception e) {
                            try {
                                this.defaultAccess();
                                message = e.getMessage();
                                if (message.indexOf("No ResultSet was produced") == -1) {
                                    request.lnkIWServices.logError("XmlSql.go " + message, IWServices.LOG_ERRORS, e, null);
                                    throw e;
                                }
                                request.lnkIWServices.logConsole("XmlSql.go " + message, IWServices.LOG_DATA, request);
                                break block43;
                            }
                            catch (Exception ex) {
                                String message4 = ex.getMessage();
                                if (message4.indexOf("No ResultSet was produced") == -1) {
                                    request.lnkIWServices.logError("XmlSql.go " + message4, IWServices.LOG_ERRORS, ex, null);
                                    throw e;
                                }
                                request.lnkIWServices.logConsole("XmlSql.go " + message4, IWServices.LOG_DATA, null);
                            }
                        }
                        break block43;
                    }
                    if (this.strMode.compareToIgnoreCase("delete") == 0) {
                        try {
                            this.delete();
                        }
                        catch (Exception e) {
                            try {
                                this.defaultAccess();
                                break block43;
                            }
                            catch (Exception ex) {
                                String message5 = ex.getMessage();
                                if (message5.indexOf("No ResultSet was produced") == -1) {
                                    request.lnkIWServices.logError("XmlSql.go " + message5, IWServices.LOG_ERRORS, ex, request);
                                    throw e;
                                }
                                request.lnkIWServices.logConsole("XmlSql.go " + message5, IWServices.LOG_DATA, null);
                            }
                        }
                        break block43;
                    }
                    if (this.strMode.compareToIgnoreCase("procedure") != 0) break block43;
                    try {
                        this.procedure();
                    }
                    catch (Exception e) {
                        try {
                            this.defaultAccess();
                            break block43;
                        }
                        catch (Exception ex) {
                            String message6 = ex.getMessage();
                            if (message6.indexOf("No ResultSet was produced") == -1) {
                                request.lnkIWServices.logError("XmlSql.go " + message6, IWServices.LOG_ERRORS, ex, request);
                                throw e;
                            }
                            request.lnkIWServices.logConsole("XmlSql.go " + message6, IWServices.LOG_DATA, null);
                        }
                    }
                    break block43;
                }
                try {
                    this.defaultAccess();
                }
                catch (Exception e) {
                    message = e.getMessage();
                    if (message.indexOf("No ResultSet was produced") == -1) {
                        request.lnkIWServices.logError("XmlSql.go " + message, IWServices.LOG_ERRORS, e, null);
                        throw e;
                    }
                    request.lnkIWServices.logConsole("XmlSql.go " + message, IWServices.LOG_DATA, request);
                }
            }
            catch (SQLException ex) {
                request.setError("XmlSql.go SQLException: " + ex.getMessage());
                request.lnkIWServices.logError(request.getError(), IWServices.LOG_ERRORS, ex, null);
            }
        }
        StringBuffer responseBuffer = new StringBuffer();
        ++this.dataMapID;
        if (this.iwCompress == null) {
            responseBuffer.append("    <datamap ID=\"" + this.dataMapID + "\" name=\"" + this.dataMap.getName() + "\" rowcount=\"" + this.totalRows + "\">\n");
            responseBuffer.append(this.mapBuffer.toString());
            responseBuffer.append("    </datamap>\n");
        }
        this.mapBuffer = null;
        if (this.strTransform != null) {
            String strResponse = null;
            request.getXsltc().strTransform = this.strTransform;
            strResponse = request.getXsltc().executeTransform(responseBuffer.toString(), request);
            responseBuffer = null;
            responseBuffer = new StringBuffer();
            responseBuffer.append(strResponse);
        }
        if (this.escapeTags) {
            this.escapeTags = false;
            String responseStr = responseBuffer.toString();
            responseBuffer = null;
            responseBuffer = this.replace(responseStr, "&lt;", "<");
            responseStr = responseBuffer.toString();
            responseBuffer = null;
            responseBuffer = this.replace(responseStr, "&gt;", ">");
            responseStr = responseBuffer.toString();
            responseBuffer = null;
            responseBuffer = this.replace(responseStr, "&quot;", "\"");
        }
        if (this.iwCompress != null) {
            request.rawData = this.iwCompress.encrypt(this.strmBytes.toByteArray());
            responseBuffer = null;
            responseBuffer = new StringBuffer();
        }
        try {
            this.strmBytes.close();
            this.strmDataType.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return responseBuffer;
    }

    public void stringToFile(String fileName, String out) throws Exception {
        File file = new File(fileName);
        BufferedWriter output = null;
        output = new BufferedWriter(new FileWriter(file));
        output.write(out);
        output.close();
        file = null;
        output = null;
    }

    public StringBuffer replace(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();
        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.totalRows = 0;
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
        this.strDriver = map.getDriver();
        this.strDSN = map.getUrl();
        this.strUser = map.getUser();
        this.strPWD = map.getPassword();
        this.getConn();
    }

    public void getConn() throws Exception {
        Class.forName(this.strDriver);
        this.conn = DriverManager.getConnection(this.strDSN, this.strUser, this.strPWD);
    }

    public void execute(String strSQL) throws SQLException {
        block6: {
            this.iwRequest.lnkIWServices.logConsole("Sql Statement\n" + strSQL, IWServices.LOG_DATA, this.iwRequest);
            this.mapBuffer = new StringBuffer();
            try {
                Statement stmt = this.conn.createStatement();
                this.rs = null;
                this.rs = stmt.executeQuery(strSQL);
                if (this.rs == null) break block6;
                ResultSetMetaData rsm = this.rs.getMetaData();
                this.dataBuffer = new StringBuffer();
                this.iwRequest.colCount = rsm.getColumnCount();
                int colCount = rsm.getColumnCount();
                int i = 1;
                while (i <= this.iwRequest.colCount) {
                    IWColDef colDef = new IWColDef();
                    colDef.name = rsm.getColumnName(i);
                    colDef.typeName = rsm.getColumnTypeName(i);
                    colDef.type = rsm.getColumnType(i);
                    colDef.precision = rsm.getPrecision(i);
                    colDef.scale = rsm.getScale(i);
                    colDef.len = rsm.getColumnDisplaySize(i);
                    this.colList.add(colDef);
                    ++i;
                }
                this.scrollCursor(this.rs);
                while (stmt.getMoreResults()) {
                    this.rs = null;
                    this.rs = stmt.getResultSet();
                    this.scrollCursor(this.rs);
                }
                stmt.close();
                this.conn.close();
            }
            catch (Exception e) {
                e.getMessage();
                System.out.println(e.getMessage());
            }
        }
    }

    public String fileToString(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader input = null;
        input = new BufferedReader(new FileReader(file));
        StringBuffer outString = new StringBuffer();
        String line = null;
        while ((line = input.readLine()) != null) {
            outString.append(line);
        }
        input.close();
        line = outString.toString();
        outString = null;
        file = null;
        input = null;
        return line;
    }

    public String getDriver() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public String getUser() {
        return null;
    }
}

