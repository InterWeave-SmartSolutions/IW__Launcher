/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.database;

import com.interweave.adapter.database.IWColDef;
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
import com.interweave.encrypt.IWIEncrypt;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.sql.RowSetListener;
import sun.jdbc.odbc.ee.DataSource;
import sun.jdbc.rowset.JdbcRowSet;

public class IWSqlSync
implements IWIDataMap {
    protected javax.sql.DataSource dataSource = null;
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
    protected int batchStep = -1;
    protected String escapeQueryQuote = null;
    protected boolean firstCycleOnly = false;

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public boolean scrollCursor(String strSQL, ResultSet crs, String addColName, String addColValue, String addColName1, String addColValue1) throws SQLException {
        int rowcount;
        block38: {
            rowcount = 0;
            this.dataBuffer = new StringBuffer();
            int cols = 0;
            ResultSetMetaData rsm = null;
            if (this.iwRequest.isFilterTransaction()) {
                cols = 1;
            } else {
                rsm = crs.getMetaData();
                cols = rsm.getColumnCount();
            }
            this.iwRequest.lnkIWServices.logConsole("Scroll: MetaData obtained", IWServices.LOG_TRACE, this.iwRequest);
            ArrayList<String> rN = new ArrayList<String>();
            StringBuffer rQN = new StringBuffer();
            StringBuffer rQ = new StringBuffer();
            StringBuffer r2QN = new StringBuffer();
            StringBuffer r2Q = new StringBuffer();
            Integer[] retFormat = IWServices.analyzeParameters(this.curAccess, rN, rQN, rQ, r2QN, r2Q);
            String[] returnName = rN.toArray(new String[0]);
            String replaceQuoteName = rQN.toString();
            String replaceQuote = rQ.toString();
            String replace2QuoteName = r2QN.toString();
            String replace2Quote = r2Q.toString();
            this.iwRequest.lnkIWServices.logConsole("Scroll: Parameters analyzed and prepared: BS= " + this.batchStep + " CV= " + this.iwRequest.getCycleValue() + " CC= " + this.iwRequest.getCycleCounter(), IWServices.LOG_TRACE, this.iwRequest);
            try {
                int ib = 0;
                int bs = -1;
                int scs = this.iwRequest.getStopCycleSchedule();
                boolean stops = false;
                if (this.batchStep > 0 && strSQL.toUpperCase().trim().startsWith("SELECT")) {
                    String tfc = "";
                    if (this.iwRequest.getCycleValue() > 0) {
                        this.iwRequest.setStopCycleSchedule(0);
                        this.iwRequest.setCycleTransactionCounter(this.iwRequest.getCycleTransactionCounter() + 1);
                        tfc = String.valueOf(this.iwRequest.getCycleCounter());
                    } else {
                        try {
                            tfc = this.iwRequest.getParameter("TransactionFlowCounter");
                        }
                        catch (Exception e) {
                            tfc = "";
                        }
                    }
                    if (tfc != null && tfc.trim().length() > 0) {
                        int cnt = -1;
                        try {
                            cnt = Integer.valueOf(tfc);
                        }
                        catch (NumberFormatException e) {
                            cnt = -1;
                        }
                        if (cnt >= 0) {
                            stops = true;
                            bs = cnt * this.batchStep;
                        }
                    }
                }
                while (crs.next()) {
                    if (this.batchStep > 0 && bs >= 0) {
                        if (ib < bs || ib >= bs + this.batchStep) {
                            ++ib;
                            continue;
                        }
                        stops = false;
                        ++ib;
                    }
                    this.iwRequest.lnkIWServices.logConsole("Row " + rowcount + "is being processed", IWServices.LOG_TRACE, this.iwRequest);
                    ++rowcount;
                    ++this.totalRows;
                    if (!this.iwRequest.isFilterTransaction()) {
                        this.dataBuffer.append("        <row number=\"" + rowcount + "\">\n");
                    }
                    int i = 1;
                    while (i <= cols) {
                        block37: {
                            try {
                                String colName = new String();
                                String colLabel = new String();
                                if (!this.iwRequest.isFilterTransaction()) {
                                    colName = rsm.getColumnName(i);
                                    colLabel = rsm.getColumnLabel(i);
                                    if (colLabel.trim().length() > 0 && !colName.equals(colLabel)) {
                                        this.dataBuffer.append("          <col number=\"" + i + "\" name=\"" + colLabel + "\">");
                                    } else {
                                        this.dataBuffer.append("          <col number=\"" + i + "\" name=\"" + colName + "\">");
                                    }
                                }
                                String strData = crs.getString(i);
                                this.iwRequest.lnkIWServices.logConsole("Colunm " + colName + " Label " + colLabel + " processed. Value = " + strData, IWServices.LOG_TRACE, this.iwRequest);
                                strData = crs.wasNull() ? "" : (strData.compareToIgnoreCase("null") == 0 ? "" : strData.trim());
                                if (this.iwRequest.isFilterTransaction()) {
                                    if (strData.length() > 0) {
                                        IWServices.filterRows(strData, this.iwRequest, this.curAccess);
                                    }
                                    break block37;
                                }
                                try {
                                    strData = IWServices.processParameters(this.iwRequest, strData, colName, returnName, replaceQuoteName, replaceQuote, replace2QuoteName, replace2Quote, retFormat);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                    returnName = null;
                                }
                                this.dataBuffer.append(String.valueOf(IWServices.escape(strData)) + "</col>\n");
                            }
                            catch (SQLException ex) {
                                this.iwRequest.lnkIWServices.logError("scrollCursor columns " + ex.getMessage(), IWServices.LOG_ERRORS, ex, this.iwRequest);
                                throw ex;
                            }
                        }
                        ++i;
                    }
                    if (this.iwRequest.isFilterTransaction()) continue;
                    if (addColName != null && addColValue != null) {
                        this.dataBuffer.append("          <col number=\"" + (cols + 1) + "\" name=\"" + addColName + "\">" + addColValue + "</col>\n");
                    }
                    if (addColName1 != null && addColValue1 != null) {
                        this.dataBuffer.append("          <col number=\"" + (cols + 1) + "\" name=\"" + addColName1 + "\">" + addColValue1 + "</col>\n");
                    }
                    this.dataBuffer.append("        </row>\n");
                }
                if (stops) {
                    if (this.iwRequest.getCycleValue() == 0) {
                        this.iwRequest.setStopSchedule(1);
                    } else if (this.iwRequest.getCycleTransactionCounter() == 1) {
                        this.iwRequest.setStopCycleSchedule(1);
                    } else {
                        this.iwRequest.setStopCycleSchedule(scs);
                    }
                }
            }
            catch (SQLException ex) {
                this.iwRequest.lnkIWServices.logError("scrollCursor rows " + ex.getMessage(), IWServices.LOG_ERRORS, ex, this.iwRequest);
                if (this.iwRequest.isFilterTransaction()) break block38;
                if (!this.dataBuffer.toString().endsWith("</row>\n")) {
                    this.dataBuffer.append("        </row>\n");
                }
                throw ex;
            }
        }
        if (!this.iwRequest.isFilterTransaction()) {
            this.mapBuffer.append("      <data rowcount=\"" + rowcount + "\">\n");
            String tt = this.dataBuffer.toString();
            this.mapBuffer.append(tt);
            this.mapBuffer.append("      </data>\n");
            this.dataBuffer = null;
            this.iwRequest.lnkIWServices.logConsole("Scroll: IWP data element created", IWServices.LOG_TRACE, this.iwRequest);
        } else {
            this.iwRequest.lnkIWServices.logConsole("After Filter = " + this.iwRequest.tranBuffer, IWServices.LOG_TRACE, this.iwRequest);
        }
        return rowcount > 0;
    }

    private void executeSql(String strSQL) throws Exception {
        boolean manualCommit;
        int lp;
        String trStr0 = strSQL;
        String nu = null;
        if (trStr0.startsWith("%%__URL__=") && (lp = trStr0.indexOf("%%", 2)) > 10) {
            nu = trStr0.substring(10, lp);
            trStr0 = trStr0.substring(lp + 2);
        }
        if (this.strDSN != null && nu != null && !nu.equals(this.strDSN) && this.conn != null && !this.conn.isClosed()) {
            this.closeConnection();
            this.iwRequest.setLastConnection(null);
            this.conn = null;
        }
        if (!(nu == null || this.strDSN != null && nu.equals(this.strDSN))) {
            this.strDSN = nu;
            this.conn = null;
        }
        if (this.conn == null || this.conn.isClosed()) {
            this.getConn(this.iwRequest, nu != null);
            this.iwRequest.setLastConnection(this.conn);
        }
        boolean oldCommit = this.conn.getAutoCommit();
        boolean bl = manualCommit = this.curAccess.getStatementpost().indexOf("%manual_commit%") >= 0;
        if (manualCommit) {
            this.conn.setAutoCommit(false);
        }
        boolean rollBack = false;
        String trStr = trStr0;
        int li = strSQL.lastIndexOf("`");
        if (li >= 0) {
            trStr = strSQL.substring(0, li + 1);
        }
        String[] str = trStr.split("`");
        this.iwRequest.lnkIWServices.logConsole("The number of Queries = " + str.length, IWServices.LOG_TRACE, this.iwRequest);
        boolean orStatements = false;
        boolean areRows = false;
        StringBuffer preQuery = new StringBuffer();
        int i = 0;
        while (i < str.length) {
            String lq;
            preQuery.append(str[i]);
            if (!(i >= str.length - 1 || (lq = str[i + 1].trim().toLowerCase()).startsWith("select") || lq.startsWith("insert") || lq.startsWith("update") || lq.startsWith("replace") || lq.startsWith("upsert") || lq.startsWith("delete") || lq.startsWith("merge") || lq.startsWith("create") || lq.startsWith("drop") || lq.startsWith("alter") || lq.startsWith("rename") || lq.startsWith("do") || lq.startsWith("truncate") || lq.startsWith("commit") || lq.startsWith("rollback") || lq.startsWith("load") || lq.startsWith("exec") || lq.startsWith("execute") || lq.startsWith("sp_optimizeupdatesync") || lq.startsWith("sp_lastinsertid") || lq.startsWith("sp_batchclear") || lq.startsWith("sp_clearlastinsertid") || lq.startsWith("%%") || lq.startsWith("{"))) {
                preQuery.append("`");
            } else {
                String sqlstr;
                String query = preQuery.toString();
                preQuery = new StringBuffer();
                if (query.trim().length() != 0 && (sqlstr = IWServices.replaceParameters(query, this.curAccess, this.iwRequest).trim()).length() != 0) {
                    if (!orStatements && sqlstr.startsWith("{")) {
                        sqlstr = sqlstr.substring(1, sqlstr.length());
                        orStatements = true;
                        areRows = false;
                        this.iwRequest.lnkIWServices.logConsole("The Or type Query started = " + sqlstr, IWServices.LOG_TRACE, this.iwRequest);
                    }
                    boolean orStatementsPre = orStatements;
                    if (orStatements && sqlstr.endsWith("}")) {
                        sqlstr = sqlstr.substring(0, sqlstr.length() - 1);
                        orStatementsPre = false;
                    }
                    if (!orStatements || !areRows) {
                        Timestamp cts = this.iwRequest.getQueryStartTime();
                        String rts = this.iwRequest.getReturnString();
                        Timestamp[] octs = new Timestamp[10];
                        System.arraycopy(this.iwRequest.getOtherQueryStartTime(), 0, octs, 0, 10);
                        String[] orts = new String[10];
                        System.arraycopy(this.iwRequest.getOtherReturnString(), 0, orts, 0, 10);
                        try {
                            areRows = this.execute(sqlstr);
                        }
                        catch (Exception e) {
                            this.iwRequest.setFlowSuccess(false);
                            this.iwRequest.lnkIWServices.logError("XmlsqParams.statement ", IWServices.LOG_ERRORS, e, this.iwRequest);
                            if (manualCommit) {
                                this.iwRequest.setQueryStartTime(this.iwRequest.getInitialQueryStartTime());
                                this.iwRequest.setReturnString(this.iwRequest.getInitialReturnString());
                                System.arraycopy(this.iwRequest.getInitialOtherQueryStartTime(), 0, this.iwRequest.getOtherQueryStartTime(), 0, 10);
                                System.arraycopy(this.iwRequest.getInitialOtherReturnString(), 0, this.iwRequest.getOtherReturnString(), 0, 10);
                                rollBack = true;
                            }
                            this.iwRequest.setQueryStartTime(cts);
                            this.iwRequest.setReturnString(rts);
                            System.arraycopy(octs, 0, this.iwRequest.getOtherQueryStartTime(), 0, 10);
                            System.arraycopy(orts, 0, this.iwRequest.getOtherReturnString(), 0, 10);
                        }
                    }
                    orStatements = orStatementsPre;
                }
            }
            ++i;
        }
        if (manualCommit) {
            if (rollBack) {
                this.conn.rollback();
            } else {
                this.conn.commit();
            }
            this.conn.setAutoCommit(oldCommit);
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
                String stpre = this.curAccess.getStatementpre();
                if (stpre != null && stpre.length() > 0) {
                    if (this.firstCycleOnly && this.iwRequest.getCycleValue() > 0 && this.iwRequest.getCycleCounter() > 0) {
                        this.iwRequest.lnkIWServices.logConsole("defaultAccess: Next Query in the cycle is skipped", IWServices.LOG_TRACE, this.iwRequest);
                    } else {
                        this.executeSql(stpre);
                        this.iwRequest.lnkIWServices.logConsole("defaultAccess: Query executed", IWServices.LOG_TRACE, this.iwRequest);
                    }
                } else {
                    this.iwRequest.lnkIWServices.logConsole("defaultAccess: Empty query", IWServices.LOG_TRACE, this.iwRequest);
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
        }
    }

    private void update() throws Exception {
        Access access = (Access)((Object)this.accessList.get("update"));
        this.curAccess = null;
        this.iwRequest.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".select", IWServices.LOG_TRACE, this.iwRequest);
        if (access != null) {
            this.curAccess = access;
        }
    }

    private void delete() throws Exception {
        Access access = (Access)((Object)this.accessList.get("delete"));
        this.curAccess = null;
        this.iwRequest.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".select", IWServices.LOG_TRACE, this.iwRequest);
        if (access != null) {
            this.curAccess = access;
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
                            this.iwRequest.lnkIWServices.logError("XmlSql.procedure " + message, IWServices.LOG_ERRORS, e, this.iwRequest);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void closeConnection() {
        if (this.conn == null) {
            this.iwRequest.lnkIWServices.logConsole("Disconnect: null", IWServices.LOG_MINIMUM, this.iwRequest);
            return;
        }
        String string = IWServices.VERSION;
        synchronized (string) {
            try {
                this.iwRequest.lnkIWServices.logConsole("Trying to disconnect", IWServices.LOG_MINIMUM, this.iwRequest);
                if (!this.conn.isClosed()) {
                    this.conn.close();
                }
                this.iwRequest.lnkIWServices.logConsole("Disconnected", IWServices.LOG_MINIMUM, this.iwRequest);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public StringBuffer go(IWRequest request) throws Exception {
        block43: {
            String encryptStr = request.getParameter("encryptclass");
            request.lnkIWServices.logConsole("IWXmlSql.go", IWServices.LOG_TRACE, this.iwRequest);
            this.iwRequest = request;
            this.iwCompress = null;
            if (encryptStr != null && encryptStr.length() > 0) {
                this.iwCompress = (IWIEncrypt)request.lnkIWServices.getObject(encryptStr);
            }
            this.strTransform = null;
            this.mapBuffer = new StringBuffer();
            try {
                String message;
                this.strRet = null;
                this.jrs = new JdbcRowSet();
                this.jrs.addRowSetListener((RowSetListener)new IWNullListner());
                this.jrs.setUrl(this.strDSN);
                this.jrs.setUsername(this.strUser);
                this.jrs.setPassword(this.strPWD);
                this.strMode = request.getParameter("actiontype");
                request.lnkIWServices.logConsole("Action Type = " + this.strMode, IWServices.LOG_TRACE, this.iwRequest);
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
                                    request.lnkIWServices.logError("XmlSql.go " + message2, IWServices.LOG_ERRORS, ex, request);
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
                                    request.lnkIWServices.logError("XmlSql.go " + message3, IWServices.LOG_ERRORS, ex, request);
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
                                    request.lnkIWServices.logError("XmlSql.go " + message, IWServices.LOG_ERRORS, e, request);
                                    throw e;
                                }
                                request.lnkIWServices.logConsole("XmlSql.go " + message, IWServices.LOG_DATA, request);
                                break block43;
                            }
                            catch (Exception ex) {
                                String message4 = ex.getMessage();
                                if (message4.indexOf("No ResultSet was produced") == -1) {
                                    request.lnkIWServices.logError("XmlSql.go " + message4, IWServices.LOG_ERRORS, ex, request);
                                    throw e;
                                }
                                request.lnkIWServices.logConsole("XmlSql.go " + message4, IWServices.LOG_DATA, request);
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
                                request.lnkIWServices.logConsole("XmlSql.go " + message5, IWServices.LOG_DATA, request);
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
                            request.lnkIWServices.logConsole("XmlSql.go " + message6, IWServices.LOG_DATA, request);
                        }
                    }
                    break block43;
                }
                try {
                    this.defaultAccess();
                    request.lnkIWServices.logConsole("Query for Default Access executed " + this.mapBuffer, IWServices.LOG_TRACE, request);
                }
                catch (Exception e) {
                    message = e.getMessage();
                    if (message.indexOf("No ResultSet was produced") == -1) {
                        request.lnkIWServices.logError("XmlSql.go " + message, IWServices.LOG_ERRORS, e, request);
                        throw e;
                    }
                    request.lnkIWServices.logConsole("XmlSql.go " + message, IWServices.LOG_DATA, request);
                }
            }
            catch (SQLException ex) {
                request.setError("XmlSql.go SQLException: " + ex.getMessage());
                request.lnkIWServices.logError(request.getError(), IWServices.LOG_ERRORS, ex, request);
            }
        }
        StringBuffer responseBuffer = new StringBuffer();
        ++this.dataMapID;
        if (!request.isFilterTransaction() && this.iwCompress == null) {
            responseBuffer.append("    <datamap ID=\"" + this.dataMapID + "\" name=\"" + this.dataMap.getName() + "\" rowcount=\"" + this.totalRows + "\">\n");
            responseBuffer.append(this.mapBuffer.toString());
            responseBuffer.append("    </datamap>\n");
        }
        this.mapBuffer = null;
        if (this.strTransform != null) {
            String strResponse = null;
            request.getXsltc().strTransform = this.strTransform;
            strResponse = request.getXsltc().executeTransform(responseBuffer.toString(), request);
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
        Access ca = (Access)((Object)map.getAccess().get(0));
        String sDrv = IWServices.getParameterByReference(map.getDriver(), request, ca);
        this.escapeQueryQuote = null;
        this.batchStep = -1;
        this.firstCycleOnly = false;
        int isd = sDrv.indexOf(":S:");
        int ied = sDrv.indexOf(":E:");
        if (isd >= 0 || ied >= 0) {
            String bst;
            int adei = isd >= 0 && ied >= 0 ? Math.min(isd, ied) : (ied < 0 ? isd : ied);
            this.strDriver = sDrv.substring(0, adei);
            if (isd >= 0) {
                bst = ied < 0 || ied < isd ? sDrv.substring(isd + 3) : sDrv.substring(isd + 3, ied);
                if (Character.isDigit(bst.charAt(0))) {
                    this.batchStep = Integer.valueOf(bst);
                } else {
                    String bstp = request.getParameter(bst);
                    if (bstp.trim().length() > 0) {
                        this.batchStep = Integer.valueOf(bstp);
                    }
                }
                if (this.batchStep == 0) {
                    this.firstCycleOnly = true;
                    this.batchStep = -1;
                }
            }
            if (ied >= 0) {
                bst = isd < 0 || isd < ied ? sDrv.substring(ied + 3) : sDrv.substring(ied + 3, isd);
                this.escapeQueryQuote = bst;
            }
        } else {
            this.strDriver = sDrv;
        }
        this.strDSN = IWServices.getParameterByReference(map.getUrl(), request, ca);
        this.strUser = IWServices.getParameterByReference(map.getUser(), request, ca);
        this.strPWD = IWServices.getParameterByReference(map.getPassword(), request, ca);
        Connection lc = (Connection)request.getLastConnection();
        if (lc != null) {
            this.conn = lc;
            request.lnkIWServices.logConsole("SqlBaseAdaptor: Connection is being reused", IWServices.LOG_TRACE, request);
        }
    }

    protected void getConn(IWRequest request) throws Exception {
        this.getConn(request, false);
    }

    protected void getConn(IWRequest request, boolean late) throws Exception {
        this.getConn(request, late, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void getConn(IWRequest request, boolean late, boolean secondAttempt) throws Exception {
        if (this.strDriver.equals("sun.jdbc.odbc.JdbcOdbcDriver")) {
            this.dataSource = new DataSource();
        } else {
            try {
                Class.forName(this.strDriver);
            }
            catch (ClassNotFoundException ex) {
                request.setQueryStartTime(request.getInitialQueryStartTime());
                request.setConnectionError(true);
                request.setConnectionFailures(request.getConnectionFailures() + 1);
                throw ex;
            }
        }
        String string = IWServices.VERSION;
        synchronized (string) {
            ConnectionThread ct = new ConnectionThread(request, late, secondAttempt);
            ct.start();
            request.lnkIWServices.logConsole("SqlBaseAdaptor: Connection thread started", IWServices.LOG_TRACE, request);
            if (ct.isAlive()) {
                try {
                    ct.join(request.getCONNECT_TO());
                    request.lnkIWServices.logConsole("SqlBaseAdaptor: Connection thread died or join expired", IWServices.LOG_TRACE, request);
                }
                catch (InterruptedException e) {
                    request.lnkIWServices.logConsole("SqlBaseAdaptor: Connected/Interrupted", IWServices.LOG_TRACE, request);
                }
                if (ct.isAlive()) {
                    request.lnkIWServices.logConsole("SqlBaseAdaptor: Interrupted; Connection thread is alive", IWServices.LOG_TRACE, request);
                    if (this.conn == null) {
                        if (!ct.isTwoAttempts()) {
                            ct.interrupt();
                            this.getConn(request, late, true);
                            return;
                        }
                        request.setConnectionError(true);
                        request.setConnectionFailures(request.getConnectionFailures() + 1);
                        request.setQueryStartTime(request.getInitialQueryStartTime());
                    }
                    ct.interrupt();
                }
            }
            if (this.conn == null) {
                request.setQueryStartTime(request.getInitialQueryStartTime());
                request.setConnectionError(true);
                request.setConnectionFailures(request.getConnectionFailures() + 1);
                throw new Exception("Connection Failed");
            }
        }
    }

    public boolean execute(String strSQLIn) throws Exception {
        return this.execute(strSQLIn, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean execute(String strSQLIn, int level) throws Exception {
        boolean noRepIns;
        String addColV1;
        String addColN1;
        String addColV;
        String addColN;
        String strSQL;
        block31: {
            block32: {
                this.iwRequest.lnkIWServices.logConsole(strSQLIn, IWServices.LOG_MINIMUM, this.iwRequest);
                strSQL = strSQLIn;
                addColN = null;
                addColV = null;
                addColN1 = null;
                addColV1 = null;
                noRepIns = true;
                if (!strSQLIn.startsWith("%%")) break block31;
                if (!strSQLIn.startsWith("%%__URL__=")) break block32;
                int lp = strSQLIn.indexOf("%%", 2);
                if (lp > 10) {
                    String strSQL1;
                    String nu = strSQLIn.substring(10, lp);
                    addColN = "__URL__";
                    addColV = nu;
                    if (!this.strDSN.equals(nu)) {
                        this.closeConnection();
                        this.iwRequest.setLastConnection(null);
                        this.conn = null;
                        this.strDSN = nu;
                        this.getConn(this.iwRequest, true);
                        this.iwRequest.setLastConnection(this.conn);
                    }
                    if ((strSQL1 = strSQLIn.substring(lp + 2)).startsWith("%%")) {
                        int pen = strSQL1.lastIndexOf("%%");
                        if (pen > 0) {
                            int peq = strSQL1.indexOf("=", 2);
                            if (peq > 0) {
                                addColN1 = strSQL1.substring(2, peq);
                                addColV1 = strSQL1.substring(peq + 1, pen);
                            } else if (strSQL1.startsWith("%%repeat_insert%%")) {
                                noRepIns = false;
                            }
                            strSQL = strSQL1.substring(pen + 2);
                            break block31;
                        } else {
                            strSQL = strSQL1;
                        }
                        break block31;
                    } else {
                        strSQL = strSQL1;
                    }
                }
                break block31;
            }
            if (strSQLIn.startsWith("%%repeat_insert%%")) {
                noRepIns = false;
                strSQL = strSQLIn.substring(17);
            } else {
                int pen = strSQLIn.lastIndexOf("%%");
                if (pen > 0) {
                    int peq = strSQLIn.indexOf("=", 2);
                    if (peq > 0) {
                        addColN = strSQLIn.substring(2, peq);
                        addColV = strSQLIn.substring(peq + 1, pen);
                    }
                    strSQL = strSQLIn.substring(pen + 2);
                }
            }
        }
        if (this.escapeQueryQuote != null) {
            this.iwRequest.lnkIWServices.logConsole("Before escaping quote: " + strSQL, IWServices.LOG_TRACE, this.iwRequest);
            strSQL = IWServices.escapeQuote(strSQL, this.escapeQueryQuote);
            this.iwRequest.lnkIWServices.logConsole("After escaping quote: " + strSQL, IWServices.LOG_TRACE, this.iwRequest);
        }
        String string = IWServices.VERSION;
        synchronized (string) {
            try {
                Statement stmt = this.conn.createStatement();
                boolean isRs = stmt.execute(strSQL);
                boolean areRows = false;
                this.iwRequest.lnkIWServices.logConsole("SQL Statement executed", IWServices.LOG_TRACE, this.iwRequest);
                if (isRs) {
                    this.rs = stmt.getResultSet();
                    this.iwRequest.lnkIWServices.logConsole("ResultSet obtained", IWServices.LOG_TRACE, this.iwRequest);
                    if (!this.iwRequest.isFilterTransaction()) {
                        ResultSetMetaData rsm = this.rs.getMetaData();
                        this.iwRequest.lnkIWServices.logConsole("MetaData obtained", IWServices.LOG_TRACE, this.iwRequest);
                        this.dataBuffer = new StringBuffer();
                        this.iwRequest.colCount = rsm.getColumnCount();
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
                    }
                    this.iwRequest.lnkIWServices.logConsole("Columns created", IWServices.LOG_TRACE, this.iwRequest);
                    areRows = this.scrollCursor(strSQL, this.rs, addColN, addColV, addColN1, addColV1);
                    this.iwRequest.lnkIWServices.logConsole("IWP created", IWServices.LOG_TRACE, this.iwRequest);
                    while (true) {
                        if (!stmt.getMoreResults()) {
                            stmt.close();
                            break;
                        }
                        this.rs = stmt.getResultSet();
                        areRows = this.scrollCursor(strSQL, this.rs, addColN, addColV, addColN1, addColV1) || areRows;
                    }
                }
                if (!isRs) return false;
                if (!areRows) return false;
                return true;
            }
            catch (SQLException e) {
                this.closeConnection();
                this.iwRequest.setLastConnection(null);
                this.conn = null;
                this.getConn(this.iwRequest);
                this.iwRequest.setLastConnection(this.conn);
                String mes = e.getMessage();
                if ((mes.toLowerCase().indexOf("socket") >= 0 || mes.indexOf("General") >= 0 && mes.indexOf("error") > 0 || mes.indexOf("Network") >= 0 && mes.indexOf("connection") > 0 && mes.indexOf("broken") > 0 || mes.toLowerCase().indexOf("packet") > 0 || mes.toLowerCase().indexOf("client") > 0 && mes.toLowerCase().indexOf("exceeded") > 0 && mes.toLowerCase().indexOf("retry") > 0 && mes.toLowerCase().indexOf("limit") > 0 || mes.toLowerCase().indexOf("scroll") >= 0 && mes.toLowerCase().indexOf("cursor") >= 0 || mes.toLowerCase().indexOf("list") >= 0 && mes.toLowerCase().indexOf("modified") >= 0 && mes.toLowerCase().indexOf("another") >= 0 && mes.toLowerCase().indexOf("user") >= 0) && level < 2) {
                    this.iwRequest.lnkIWServices.logConsole("Warning: " + strSQL + " " + mes, IWServices.LOG_ERRORS, this.iwRequest);
                    if (mes.indexOf("General") >= 0 && mes.indexOf("error") > 0) {
                        return false;
                    }
                    if (strSQL.toLowerCase().trim().startsWith("sp_lastinsertid")) {
                        this.iwRequest.lnkIWServices.logConsole("Error: SP_LASTINSERTID cannot be recovered", IWServices.LOG_ERRORS, this.iwRequest);
                        return false;
                    }
                    if (!noRepIns) return this.execute(strSQLIn, ++level);
                    if (mes.toLowerCase().indexOf("socket") < 0 && (mes.indexOf("Network") < 0 || mes.indexOf("connection") <= 0 || mes.indexOf("broken") <= 0) && mes.toLowerCase().indexOf("packet") <= 0) {
                        if (mes.toLowerCase().indexOf("client") <= 0) return this.execute(strSQLIn, ++level);
                        if (mes.toLowerCase().indexOf("exceeded") <= 0) return this.execute(strSQLIn, ++level);
                        if (mes.toLowerCase().indexOf("retry") <= 0) return this.execute(strSQLIn, ++level);
                        if (mes.toLowerCase().indexOf("limit") <= 0) return this.execute(strSQLIn, ++level);
                    }
                    if (!strSQL.toLowerCase().trim().startsWith("insert")) return this.execute(strSQLIn, ++level);
                    if (strSQL.toLowerCase().indexOf("fqsavetocache") >= 0) {
                        this.iwRequest.lnkIWServices.logConsole("Error: Group insert cannot be recovered", IWServices.LOG_ERRORS, this.iwRequest);
                        return false;
                    }
                    this.iwRequest.lnkIWServices.logConsole("Error: Insert statement cannot be recovered", IWServices.LOG_ERRORS, this.iwRequest);
                    return false;
                }
                this.iwRequest.lnkIWServices.logConsole("SQL Statement exception: " + strSQL + " " + mes, IWServices.LOG_ERRORS, this.iwRequest);
                throw e;
            }
        }
    }

    public String getDriver() {
        return this.strDriver;
    }

    public String getPassword() {
        return this.strPWD;
    }

    public String getUrl() {
        return this.strDSN;
    }

    public String getUser() {
        return this.strUser;
    }

    public class ConnectionThread
    extends Thread {
        private IWRequest request;
        private boolean late;
        private boolean twoAttempts;

        public ConnectionThread(IWRequest request) {
            this.request = request;
            this.late = false;
            this.twoAttempts = false;
        }

        public ConnectionThread(IWRequest request, boolean late) {
            this.request = request;
            this.late = late;
            this.twoAttempts = false;
        }

        /*
         * Unable to fully structure code
         */
        public void run() {
            block17: {
                block16: {
                    block14: {
                        block15: {
                            loginTimeOut = this.request.isLongTimeOut() != false ? 300 : 150;
                            mes = new String("");
                            if (!IWSqlSync.this.strDriver.equals("sun.jdbc.odbc.JdbcOdbcDriver")) break block16;
                            dsn = IWSqlSync.this.strDSN;
                            if (IWSqlSync.this.strDSN.startsWith("jdbc:odbc:")) {
                                dsn = IWSqlSync.this.strDSN.substring(10);
                            }
                            try {
                                ((DataSource)IWSqlSync.this.dataSource).setDatabaseName(dsn);
                                IWSqlSync.this.dataSource.setLoginTimeout(loginTimeOut);
                                this.request.lnkIWServices.logConsole("Trying to connect", IWServices.LOG_MINIMUM, this.request);
                                IWSqlSync.this.conn = IWSqlSync.this.dataSource.getConnection(IWSqlSync.this.strUser, IWSqlSync.this.strPWD);
                                this.request.lnkIWServices.logConsole("Connected", IWServices.LOG_MINIMUM, this.request);
                                break block14;
                            }
                            catch (SQLException e) {
                                v0 = mes = e == null ? "null" : e.getMessage();
                                if (mes.indexOf("Specified driver could not be loaded due to system error") < 0 || mes.indexOf("QRemote-Driver.dll") < 0) break block15;
                                this.request.lnkIWServices.logConsole("Trying to reconnect 500 times", IWServices.LOG_MINIMUM, this.request);
                                cnf = true;
                                mi = 0;
                                ** while (mi < 500)
                            }
lbl-1000:
                            // 1 sources

                            {
                                try {
                                    IWSqlSync.this.conn = IWSqlSync.this.dataSource.getConnection(IWSqlSync.this.strUser, IWSqlSync.this.strPWD);
                                    this.request.lnkIWServices.logConsole("Connected at attempt " + mi, IWServices.LOG_MINIMUM, this.request);
                                    cnf = false;
                                    break;
                                }
                                catch (SQLException em) {
                                    v1 = mes = em == null ? "null" : em.getMessage();
                                    if (mes.indexOf("Specified driver could not be loaded due to system error") < 0 || mes.indexOf("QRemote-Driver.dll") < 0) {
                                        this.request.lnkIWServices.logConsole("SQL Connection exception: " + mes, IWServices.LOG_ERRORS, this.request);
                                        break;
                                    }
                                    ++mi;
                                }
                                continue;
                            }
lbl33:
                            // 3 sources

                            if (cnf) {
                                IWSqlSync.this.conn = null;
                            }
                            break block14;
                        }
                        if (!this.late && mes.indexOf("External exception") < 0) {
                            this.request.setQueryStartTime(this.request.getInitialQueryStartTime());
                            this.request.lnkIWServices.logConsole("SQL Connection exception: " + mes, IWServices.LOG_ERRORS, this.request);
                        }
                        IWSqlSync.this.conn = null;
                    }
                    if ((this.late || mes.indexOf("External exception") >= 0) && IWSqlSync.this.conn == null && !this.twoAttempts) {
                        try {
                            this.twoAttempts = true;
                            ((DataSource)IWSqlSync.this.dataSource).setDatabaseName(dsn);
                            IWSqlSync.this.dataSource.setLoginTimeout(loginTimeOut);
                            this.request.lnkIWServices.logConsole("Trying to connect", IWServices.LOG_MINIMUM, this.request);
                            IWSqlSync.this.conn = IWSqlSync.this.dataSource.getConnection(IWSqlSync.this.strUser, IWSqlSync.this.strPWD);
                            this.request.lnkIWServices.logConsole("Connected", IWServices.LOG_MINIMUM, this.request);
                        }
                        catch (SQLException e) {
                            this.request.setQueryStartTime(this.request.getInitialQueryStartTime());
                            this.request.lnkIWServices.logConsole("SQL Connection exception: " + (e == null ? "null" : e.getMessage()), IWServices.LOG_ERRORS, this.request);
                            IWSqlSync.this.conn = null;
                        }
                    }
                    break block17;
                }
                try {
                    DriverManager.setLoginTimeout(loginTimeOut);
                    this.request.lnkIWServices.logConsole("Trying to connect", IWServices.LOG_MINIMUM, this.request);
                    IWSqlSync.this.conn = DriverManager.getConnection(IWSqlSync.this.strDSN, IWSqlSync.this.strUser, IWSqlSync.this.strPWD);
                    this.request.lnkIWServices.logConsole("Connected", IWServices.LOG_MINIMUM, this.request);
                }
                catch (SQLException ex) {
                    this.request.setQueryStartTime(this.request.getInitialQueryStartTime());
                    this.request.lnkIWServices.logConsole("SQL Connection exception: " + (ex == null ? "null" : ex.getMessage()), IWServices.LOG_ERRORS, this.request);
                    IWSqlSync.this.conn = null;
                }
            }
        }

        public boolean isTwoAttempts() {
            return this.twoAttempts;
        }

        public ConnectionThread(IWRequest request, boolean late, boolean twoAttempts) {
            this.request = request;
            this.late = late;
            this.twoAttempts = twoAttempts;
        }
    }
}

