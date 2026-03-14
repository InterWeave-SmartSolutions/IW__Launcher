/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter;

public interface IWITransform {
    public String execute(String var1, String var2) throws Exception;

    public void setXsl(String var1);

    public int executionStatus();

    public String executionMessage();

    public String getStrName();

    public void setStrName(String var1);

    public String getStrRule();

    public void setStrRule(String var1);

    public String getStrNext();

    public void setStrNext(String var1);

    public String getStrFail();

    public void setStrFail(String var1);

    public IWITransform getINext();

    public void setINext(IWITransform var1);

    public IWITransform getIFail();

    public void setIFail(IWITransform var1);
}

