/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.database;

import com.interweave.adapter.database.IWNullListner;
import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.IWMappingType;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.sql.RowSetListener;
import sun.jdbc.rowset.JdbcRowSet;

public class IWSQLNumber
implements IWIDataMap {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private JdbcRowSet jrs = null;
    private ResultSet rs = null;
    private Access curAccess = null;
    private String strTransform = null;
    private int offset = 0;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();
    public int dataMapID = 0;
    public int totalRows = 0;
    public StringBuffer dataBuffer = null;
    public StringBuffer mapBuffer = null;
    public String strDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
    public String strDSN = "jdbc:odbc:TriPenHotels";
    public String strUser = "webuser";
    public String strPWD = "rq74we56";
    public String strMode = "Select";
    public String strSQL = "Select * from coreRFP FORXML RAW";
    private StringBuffer buffer = null;
    private String strRet = null;
    private IWRequest curRequest = null;

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public void closeConnection() {
        try {
            this.conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void procedure(IWRequest request) throws Exception {
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
        request.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".procedure", IWServices.LOG_TRACE, request);
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
                    if (type != null && type != "" && type.compareToIgnoreCase("Transform") == 0) {
                        this.strTransform = param.getInput();
                        continue;
                    }
                    IWMappingType map = new IWMappingType();
                    strData = map.getData(param, request);
                    if (quoted && strData.length() <= 6000) {
                        strData = "'" + strData + "'";
                    }
                    request.lnkIWServices.logConsole("\n###########################\nParameter:\n\tInput: " + param.getInput() + "\n\tType: " + mapping.getType() + "\n\tQuoted: " + mapping.getQuoted() + "\n\tContent: " + mapping.getContent() + "\n\tValue: " + strData + "\n###########################\n", IWServices.LOG_PARAMETERS, request);
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
            request.lnkIWServices.logConsole(this.strSQL, IWServices.LOG_REQUEST, request);
            if (needsMulti) {
                Enumeration enumerate = multiParam.elements();
                while (enumerate.hasMoreElements()) {
                    strData = (String)enumerate.nextElement();
                    if (multiQuoted) {
                        strData = "'" + strData + "'";
                    }
                    this.strSQL = String.valueOf(bufferStart.toString()) + strData + bufferEnd.toString();
                    try {
                        this.pstmt = this.conn.prepareStatement(this.strSQL);
                        this.pstmt.executeQuery();
                        this.rs = null;
                        this.rs = this.pstmt.getResultSet();
                        if (this.rs != null) {
                            this.scrollCursor(this.rs);
                            while (this.pstmt.getMoreResults()) {
                                this.rs = null;
                                this.rs = this.pstmt.getResultSet();
                                this.scrollCursor(this.rs);
                            }
                        }
                        this.pstmt.close();
                        this.conn.close();
                    }
                    catch (Exception e) {
                        String message = e.getMessage();
                        if (message.indexOf("No ResultSet was produced") == -1) {
                            request.lnkIWServices.logError("SQLNumber.procedure " + message, IWServices.LOG_ERRORS, e, null);
                            throw e;
                        }
                        request.lnkIWServices.logConsole("SQLNumber.procedure " + message, IWServices.LOG_ERRORS, request);
                    }
                }
            } else {
                try {
                    this.pstmt = this.conn.prepareStatement(this.strSQL);
                    this.pstmt.executeQuery();
                    this.rs = null;
                    this.rs = this.pstmt.getResultSet();
                    if (this.rs != null) {
                        this.scrollCursor(this.rs);
                        while (this.pstmt.getMoreResults()) {
                            this.rs = null;
                            this.rs = this.pstmt.getResultSet();
                            this.scrollCursor(this.rs);
                        }
                    }
                    this.pstmt.close();
                    this.conn.close();
                }
                catch (Exception e) {
                    String message = e.getMessage();
                    if (message.indexOf("No ResultSet was produced") == -1) {
                        request.lnkIWServices.logError("SQLNumber.procedure " + message, IWServices.LOG_ERRORS, e, null);
                        throw e;
                    }
                    request.lnkIWServices.logConsole("SQLNumber.procedure " + message, IWServices.LOG_ERRORS, request);
                }
            }
        } else {
            throw new Exception("SQLNumber.procedure No Access Object Error");
        }
    }

    public StringBuffer go(IWRequest request) throws Exception {
        this.strTransform = null;
        request.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".go", IWServices.LOG_TRACE, request);
        try {
            this.curRequest = request;
            this.strRet = null;
            this.jrs = new JdbcRowSet();
            this.jrs.addRowSetListener((RowSetListener)new IWNullListner());
            this.jrs.setUrl(this.strDSN);
            this.jrs.setUsername(this.strUser);
            this.jrs.setPassword(this.strPWD);
            this.strMode = request.getParameter("actiontype");
            if (this.strMode != null && this.strMode.length() > 0) {
                if (this.strMode.compareToIgnoreCase("procedure") == 0) {
                    this.procedure(request);
                }
            } else {
                try {
                    this.procedure(request);
                }
                catch (Exception e) {
                    String message = e.getMessage();
                    if (message.indexOf("No ResultSet was produced") == -1) {
                        request.lnkIWServices.logError("SQLNumber.go " + message, IWServices.LOG_ERRORS, e, null);
                        throw e;
                    }
                    request.lnkIWServices.logConsole("SQL STRING 2 " + this.strSQL, IWServices.LOG_ERRORS, request);
                    request.lnkIWServices.logConsole("SQLNumber.go " + message, IWServices.LOG_ERRORS, request);
                }
            }
        }
        catch (SQLException ex) {
            request.lnkIWServices.logConsole("SQL STRING 3 " + this.strSQL, IWServices.LOG_ERRORS, request);
            request.setError("SQLNumber.go SQLException: " + ex.getMessage());
            request.lnkIWServices.logError(request.getError(), IWServices.LOG_ERRORS, ex, null);
        }
        StringBuffer responseBuffer = new StringBuffer();
        ++this.dataMapID;
        responseBuffer.append(this.mapBuffer.toString());
        this.mapBuffer = null;
        if (this.strTransform != null) {
            String strResponse = null;
            request.getXsltc().strTransform = this.strTransform;
            strResponse = request.getXsltc().executeTransform(responseBuffer.toString(), request);
            responseBuffer = null;
            responseBuffer = new StringBuffer();
            responseBuffer.append(strResponse);
        }
        return responseBuffer;
    }

    public StringBuffer goSQL(String strSQL) throws Exception {
        this.getConn();
        this.strTransform = null;
        try {
            this.strRet = null;
            this.jrs = new JdbcRowSet();
            this.jrs.addRowSetListener((RowSetListener)new IWNullListner());
            this.jrs.setUrl(this.strDSN);
            this.jrs.setUsername(this.strUser);
            this.jrs.setPassword(this.strPWD);
            this.pstmt = this.conn.prepareStatement(strSQL);
            this.pstmt.executeQuery();
            this.rs = null;
            this.rs = this.pstmt.getResultSet();
            if (this.rs != null) {
                this.scrollCursor(this.rs);
                while (this.pstmt.getMoreResults()) {
                    this.rs = null;
                    this.rs = this.pstmt.getResultSet();
                    this.scrollCursor(this.rs);
                }
            }
            this.pstmt.close();
            this.conn.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL STRING 3 " + strSQL);
        }
        StringBuffer responseBuffer = new StringBuffer();
        ++this.dataMapID;
        return responseBuffer;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.totalRows = 0;
        this.dataMap = map;
        this.mapBuffer = null;
        this.mapBuffer = new StringBuffer();
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
        try {
            Class.forName(this.strDriver);
        }
        catch (ClassNotFoundException ex) {
            System.out.println("SQLNumber.getConn " + ex.getMessage());
            throw ex;
        }
        try {
            System.out.println("Connecting to: DSN[" + this.strDSN + "] User[" + this.strUser + "] Password[" + this.strPWD + "]");
            this.conn = DriverManager.getConnection(this.strDSN, this.strUser, this.strPWD);
        }
        catch (SQLException ex) {
            System.out.println("SQLNumber.getConn " + ex.getMessage());
            throw ex;
        }
    }

    public void scrollCursor(ResultSet crs) throws SQLException {
        String strData;
        ResultSetMetaData rsm;
        block5: {
            boolean rowcount = false;
            rsm = crs.getMetaData();
            this.dataBuffer = new StringBuffer();
            int cols = rsm.getColumnCount();
            try {
                int i = 1;
                while (i <= cols) {
                    this.dataBuffer.append(String.valueOf(rsm.getColumnName(i)) + " AS , -- (" + (i + this.offset - 1) + ")\n");
                    ++i;
                }
                this.offset += cols - 3;
                this.dataBuffer.append("\n");
            }
            catch (SQLException ex) {
                strData = this.dataBuffer.toString();
                if (strData.endsWith("\n")) break block5;
                this.dataBuffer.append("\n");
            }
        }
        this.dataBuffer = null;
        strData = null;
        rsm = null;
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

