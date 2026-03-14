/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.database;

import com.interweave.adapter.database.IWColDef;
import com.interweave.adapter.database.IWSqlBase;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IWXmlSql
extends IWSqlBase {
    public boolean scrollCursor(ResultSet crs) throws SQLException {
        String strData;
        block9: {
            this.dataBuffer = new StringBuffer();
            try {
                while (crs.next()) {
                    ++this.iwRequest.rowCount;
                    ++this.totalRows;
                    this.dataBuffer.append("<row number=\"" + this.iwRequest.rowCount + "\">");
                    int i = 1;
                    while (i <= this.iwRequest.colCount) {
                        strData = crs.getString(i);
                        if (crs.wasNull()) {
                            strData = "";
                        } else if (strData.compareToIgnoreCase("null") == 0) {
                            strData = "";
                        }
                        IWColDef colDef = (IWColDef)this.colList.get(i - 1);
                        this.dataBuffer.append("<col number=\"" + i + "\" name=\"" + colDef.name + "\">" + strData.trim() + "</col>");
                        ++i;
                    }
                    this.dataBuffer.append("</row>");
                }
            }
            catch (SQLException ex) {
                strData = this.dataBuffer.toString();
                if (strData.endsWith("</row>")) break block9;
                this.dataBuffer.append("</row>");
            }
        }
        this.mapBuffer.append("<data rowcount=\"" + this.iwRequest.rowCount + "\">" + this.dataBuffer.toString() + "</data>");
        this.dataBuffer = null;
        strData = null;
        return true;
    }
}

