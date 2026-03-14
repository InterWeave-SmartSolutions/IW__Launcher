/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.database;

import com.interweave.adapter.database.IWColDef;
import com.interweave.adapter.database.IWSqlBase;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IWEncryptSql
extends IWSqlBase {
    public boolean scrollCursor(ResultSet crs) throws SQLException {
        String strData;
        this.dataBuffer = new StringBuffer();
        try {
            while (crs.next()) {
                ++this.iwRequest.rowCount;
                ++this.totalRows;
                int i = 1;
                while (i <= this.iwRequest.colCount) {
                    try {
                        IWColDef colDef = (IWColDef)this.colList.get(i - 1);
                        strData = crs.getString(i);
                        if (crs.wasNull()) {
                            strData = "";
                        } else if (strData.compareToIgnoreCase("null") == 0) {
                            strData = "";
                        }
                        this.dataBuffer.append(String.valueOf(strData.trim()) + "\t");
                    }
                    catch (SQLException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    ++i;
                }
            }
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
        this.mapBuffer.append(this.dataBuffer.toString());
        this.dataBuffer = null;
        strData = null;
        return true;
    }
}

