/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.datastorage.netcool;

import com.interweave.adapter.database.IWSqlBase;
import com.interweave.core.IWRequest;
import com.jniwrapper.AnsiString;
import com.jniwrapper.DefaultLibraryLoader;
import com.jniwrapper.Function;
import com.jniwrapper.Library;
import com.jniwrapper.Parameter;
import java.sql.SQLException;

public class IWNetCool
extends IWSqlBase {
    public void getConn(IWRequest request) throws Exception {
    }

    public boolean execute(String strSQL) throws SQLException {
        System.out.println("NetCool query: " + strSQL);
        DefaultLibraryLoader.getInstance().addPath(".");
        Function cFunc = new Library("IWNCFreeTDS.so").getFunction("executeRawSQL");
        cFunc.setCallingConvention((byte)1);
        Parameter[] prmtrs = new Parameter[]{new AnsiString(this.strUser), new AnsiString(this.strPWD), new AnsiString("iwtransformationserver"), new AnsiString(this.strDSN), new AnsiString(strSQL)};
        cFunc.invoke(null, prmtrs);
        this.mapBuffer.append("      <data rowcount=\"0\">\n");
        this.mapBuffer.append("      </data>\n");
        return true;
    }
}

