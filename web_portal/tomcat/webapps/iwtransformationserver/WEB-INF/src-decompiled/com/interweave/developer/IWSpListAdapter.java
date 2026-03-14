/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.developer;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Hashtable;
import sun.jdbc.rowset.JdbcRowSet;

public class IWSpListAdapter
implements IWIDataMap {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private JdbcRowSet jrs = null;
    private ResultSet rs = null;
    private Access curAccess = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();
    public int dataMapID = 0;
    public int totalRows = 0;
    public StringBuffer dataBuffer = null;
    public StringBuffer mapBuffer = null;
    public String strTranname = "";
    public String strDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
    public String strDSN = "jdbc:odbc:InterWeave";
    public String strUser = "webuser";
    public String strPWD = "rq74we56";
    public String strProcedure = "";

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public StringBuffer go(IWRequest request) throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        Object strTransformIn = null;
        Object strTransformOut = null;
        Object strTransformPackageOut = null;
        Object strTransformPackageOutEscape = null;
        Access access = (Access)((Object)this.accessList.get("procedure"));
        Object values = null;
        this.curAccess = null;
        request.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".go", IWServices.LOG_TRACE, request);
        if (access != null) {
            this.curAccess = access;
            this.strDriver = request.getParameter("driver");
            this.strDSN = request.getParameter("dsn");
            this.strUser = request.getParameter("username");
            this.strPWD = request.getParameter("userpassword");
            this.strProcedure = request.getParameter("procedure");
            String strTran = request.getParameter("tranname");
            this.strTranname = request.getParameter("proctran");
            this.getConn();
            if (strTran.compareToIgnoreCase("sqlprocedure") == 0) {
                this.procedure();
            } else if (strTran.compareToIgnoreCase("sqlprocedures") == 0) {
                this.procedures();
            }
            responseBuffer.append("    <datamap ID=\"" + this.dataMapID + "\" name=\"" + this.dataMap.getName() + "\" rowcount=\"" + this.totalRows + "\">\n");
            responseBuffer.append(this.mapBuffer.toString());
            responseBuffer.append("    </datamap>\n");
            this.mapBuffer = null;
        }
        return responseBuffer;
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

    public void closeConnection() {
    }

    public void procedures() throws Exception {
        DatabaseMetaData dbMeta = null;
        this.mapBuffer = new StringBuffer();
        this.getConn();
        dbMeta = this.conn.getMetaData();
        ResultSet rs = dbMeta.getProcedures(null, null, null);
        this.scrollCursor(rs);
    }

    public void procedure() throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        DatabaseMetaData dbMeta = null;
        this.mapBuffer = new StringBuffer();
        this.getConn();
        dbMeta = this.conn.getMetaData();
        ResultSet rs = dbMeta.getProcedureColumns(null, null, this.strProcedure, null);
        responseBuffer.append("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n");
        responseBuffer.append("<xsl:output omit-xml-declaration=\"yes\"/>\n");
        responseBuffer.append("<xsl:template name=\"" + this.strTranname + "\">\n");
        responseBuffer.append("<transaction name=\"" + this.strTranname + "\" type=\"sequential\">\n");
        responseBuffer.append("<transform/>\n");
        responseBuffer.append("<classname>com.interweave.adapter.database.IWXmlSql</classname>\n");
        responseBuffer.append("<datamap name=\"" + this.strTranname + "\">\n");
        responseBuffer.append("<driver>" + this.strDriver + "</driver>\n");
        responseBuffer.append("<url>" + this.strDSN + "</url>\n");
        responseBuffer.append("<user>" + this.strUser + "</user>\n");
        responseBuffer.append("<password>" + this.strPWD + "</password>\n");
        responseBuffer.append("<access type=\"procedure\">\n");
        responseBuffer.append("<statementpre>" + this.strProcedure + "</statementpre>\n");
        responseBuffer.append("<statementpost/>\n");
        responseBuffer.append("<values>\n");
        this.procCursor(rs);
        responseBuffer.append(this.mapBuffer.toString());
        responseBuffer.append("</values>\n");
        responseBuffer.append("</access>\n");
        responseBuffer.append("</datamap>\n");
        responseBuffer.append("</transaction>\n");
        responseBuffer.append("</xsl:template>\n");
        responseBuffer.append("</xsl:stylesheet>\n");
        String fileName = "c:/soaptest/" + this.strTranname + ".xslt";
        this.stringToFile(fileName, responseBuffer.toString());
        this.mapBuffer = null;
        this.mapBuffer = new StringBuffer();
        this.mapBuffer.append("");
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

    public void procCursor(ResultSet crs) throws SQLException {
        int rowcount = 0;
        ResultSetMetaData rsm = crs.getMetaData();
        this.dataBuffer = new StringBuffer();
        int cols = rsm.getColumnCount();
        while (true) {
            try {
                if (!crs.next()) break;
                ++rowcount;
                ++this.totalRows;
                String strColName = crs.getString(4);
                if (strColName.compareToIgnoreCase("@RETURN_VALUE") == 0) continue;
                if (strColName.indexOf("@") != -1) {
                    strColName = strColName.substring(1);
                }
                String strColType = crs.getString(7);
                this.dataBuffer.append("<parameter>\n");
                this.dataBuffer.append("<input>" + strColName + "</input>\n");
                if (strColType.compareToIgnoreCase("int") == 0) {
                    this.dataBuffer.append("<mapping quoted=\"false\">" + strColName + "</mapping>\n");
                } else {
                    this.dataBuffer.append("<mapping quoted=\"true\">" + strColName + "</mapping>\n");
                }
                this.dataBuffer.append("</parameter>\n");
            }
            catch (SQLException sQLException) {
                // empty catch block
                break;
            }
        }
        this.mapBuffer.append(this.dataBuffer.toString());
        this.dataBuffer = null;
        Object strData = null;
        rsm = null;
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
        this.mapBuffer.append(this.dataBuffer.toString());
        this.mapBuffer.append("      </data>\n");
        this.dataBuffer = null;
        strData = null;
        rsm = null;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.totalRows = 0;
        this.dataMap = map;
        this.mapBuffer = null;
        this.mapBuffer = new StringBuffer();
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    public void getConn() throws Exception {
        Class.forName(this.strDriver);
        this.conn = DriverManager.getConnection(this.strDSN, this.strUser, this.strPWD);
    }

    public static void main(String[] params) throws Exception {
        IWSpListAdapter iwspListAdapter = new IWSpListAdapter();
        iwspListAdapter.procedure();
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

