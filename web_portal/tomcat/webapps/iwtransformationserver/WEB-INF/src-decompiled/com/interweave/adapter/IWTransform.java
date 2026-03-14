/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter;

import com.interweave.adapter.IWITransform;

public class IWTransform {
    private String strName = "";
    private String strNext = "";
    private String strFail = "";
    private String strRule = "";
    private IWITransform transformNext = null;
    private IWITransform transformFail = null;
    protected int iStatus = TRAN_FAIL;
    protected String strMapDir = null;
    private String strExecutionMessage = null;
    public static int TRAN_TERMINATE = -2;
    public static int TRAN_FAIL = -1;
    public static int TRAN_SUCCESS = 1;
    public static int TRAN_CONTINUE = 2;

    public String getStrName() {
        return this.strName;
    }

    public void setStrName(String value) {
        this.strName = value;
    }

    public String getStrRule() {
        return this.strRule;
    }

    public void setStrRule(String value) {
        this.strRule = value;
    }

    public String getStrNext() {
        return this.strNext;
    }

    public void setStrNext(String value) {
        this.strNext = value;
    }

    public String getStrFail() {
        return this.strFail;
    }

    public void setStrFail(String value) {
        this.strFail = value;
    }

    public IWITransform getINext() {
        return this.transformNext;
    }

    public void setINext(IWITransform value) {
        this.transformNext = value;
    }

    public IWITransform getIFail() {
        return this.transformFail;
    }

    public void setIFail(IWITransform value) {
        this.transformFail = value;
    }

    public String executionMessage() {
        return this.strExecutionMessage;
    }

    public int executionStatus() {
        return this.iStatus;
    }
}

