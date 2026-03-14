/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.webservice;

import com.interweave.actionscript.IWASResponse;
import flashgateway.io.ASObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IWISQL {
    Connection conn = null;
    public String strDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
    public String strDSN = "jdbc:odbc:InterWeave";
    public String strUser = "webuser";
    public String strPWD = "rq74we56";
    public String strSearch = "Select varName, varValue From iwTheme";
    private static Log log = LogFactory.getLog(IWISQL.class);
    private String errors = "";
    private ResultSet rs = null;
    private Statement s = null;
    private ResultSetMetaData rsm = null;

    public IWASResponse executeASTransaction(ASObject iwIn) {
        return null;
    }

    public void cleanup() {
        log.info((Object)"Cleanup Connection");
        if (this.rs != null) {
            try {
                this.rs.close();
            }
            catch (Exception ex) {
                this.errors = String.valueOf(this.errors) + ex.toString();
            }
        }
        if (this.s != null) {
            try {
                this.s.close();
            }
            catch (Exception ex) {
                this.errors = String.valueOf(this.errors) + ex.toString();
            }
        }
        if (this.conn != null) {
            try {
                this.conn.close();
            }
            catch (Exception ex) {
                this.errors = String.valueOf(this.errors) + ex.toString();
            }
        }
        this.rsm = null;
        this.rs = null;
        this.s = null;
        this.conn = null;
    }

    public void getConn() throws Exception {
        Class.forName(this.strDriver);
        this.conn = DriverManager.getConnection(this.strDSN, this.strUser, this.strPWD);
        log.info((Object)("Connected to: " + this.strDSN + ":[" + this.strUser + "]:[" + this.strPWD + "]"));
    }
}

