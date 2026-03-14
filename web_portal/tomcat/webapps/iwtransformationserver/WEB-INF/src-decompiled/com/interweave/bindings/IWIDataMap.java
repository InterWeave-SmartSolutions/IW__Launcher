/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.bindings;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.core.IWRequest;
import java.util.Hashtable;

public interface IWIDataMap {
    public void setup(Datamap var1, IWRequest var2) throws Exception;

    public StringBuffer go(IWRequest var1) throws Exception;

    public Access getCurAccess();

    public Hashtable getAccessList();

    public void closeConnection();

    public String getDriver();

    public String getUrl();

    public String getUser();

    public String getPassword();
}

