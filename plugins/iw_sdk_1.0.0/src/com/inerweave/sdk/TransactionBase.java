/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import java.sql.Timestamp;
import java.util.Hashtable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TransactionBase {
    private Timestamp transactionStartTime;
    private Hashtable<String, ParameterValue> parameters;
    protected String transactionId;
    private boolean hostedPublic = false;
    private boolean active = true;
    private String primaryTransformationServerURL;
    private String secondaryTransformationServerURL;
    private String primaryTransformationServerURLT;
    private String secondaryTransformationServerURLT;
    private String primaryTransformationServerURL1;
    private String secondaryTransformationServerURL1;
    private String primaryTransformationServerURLT1;
    private String secondaryTransformationServerURLT1;
    private String primaryTransformationServerURLD;
    private String secondaryTransformationServerURLD;
    private String description;
    private String solution;
    private int innerCycles = 0;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPrimaryTransformationServerURL() {
        return this.primaryTransformationServerURL;
    }

    public void setPrimaryTransformationServerURL(String primaryTransformationServerURL) {
        this.primaryTransformationServerURL = primaryTransformationServerURL;
    }

    public String getSecondaryTransformationServerURL() {
        return this.secondaryTransformationServerURL;
    }

    public void setSecondaryTransformationServerURL(String secondaryTransformationServerURL) {
        this.secondaryTransformationServerURL = secondaryTransformationServerURL;
    }

    public boolean isHostedPublic() {
        return this.hostedPublic;
    }

    public void setHostedPublic(boolean hostedPublic) {
        this.hostedPublic = hostedPublic;
    }

    public Timestamp getTransactionStartTime() {
        return this.transactionStartTime;
    }

    public void setTransactionStartTime(Timestamp transactionStartTime) {
        this.transactionStartTime = transactionStartTime;
    }

    public final Hashtable<String, ParameterValue> getParameters() {
        return this.parameters;
    }

    public final void setParameters(Hashtable<String, ParameterValue> parameters) {
        this.parameters = parameters;
    }

    public final void addParameter(String Name2, String Value, boolean fixed, boolean upload, boolean password) {
        this.parameters.put(Name2, new ParameterValue(Value, fixed, upload, password));
    }

    public final void addParameter(String Name2, String Value) {
        this.addParameter(Name2, Value, false, false, false);
    }

    public final ParameterValue retrieveParameter(String Name2) {
        return this.parameters.get(Name2);
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    private TransactionBase(String transactionId, String description, String solution, Timestamp transactionStartTime, Hashtable<String, ParameterValue> parameters, boolean hostedPublic, boolean active, String primaryTransformationServerURL, String secondaryTransformationServerURL, String primaryTransformationServerURLT, String secondaryTransformationServerURLT, String primaryTransformationServerURL1, String secondaryTransformationServerURL1, String primaryTransformationServerURLT1, String secondaryTransformationServerURLT1, String primaryTransformationServerURLD, String secondaryTransformationServerURLD, int innerCycles) {
        this.transactionId = transactionId;
        this.description = description;
        this.solution = solution;
        this.transactionStartTime = transactionStartTime;
        this.parameters = parameters;
        this.hostedPublic = hostedPublic;
        this.active = active;
        this.primaryTransformationServerURL = primaryTransformationServerURL;
        this.secondaryTransformationServerURL = secondaryTransformationServerURL;
        this.primaryTransformationServerURLT = primaryTransformationServerURLT;
        this.secondaryTransformationServerURLT = secondaryTransformationServerURLT;
        this.primaryTransformationServerURL1 = primaryTransformationServerURL1;
        this.secondaryTransformationServerURL1 = secondaryTransformationServerURL1;
        this.primaryTransformationServerURLT1 = primaryTransformationServerURLT1;
        this.secondaryTransformationServerURLT1 = secondaryTransformationServerURLT1;
        this.primaryTransformationServerURLD = primaryTransformationServerURLD;
        this.secondaryTransformationServerURLD = secondaryTransformationServerURLD;
        this.innerCycles = innerCycles;
    }

    public TransactionBase(TransactionBase tb) {
        this(tb.getTransactionId(), tb.getDescription(), tb.getSolution(), tb.getTransactionStartTime(), tb.getParameters(), tb.isHostedPublic(), tb.isActive(), tb.getPrimaryTransformationServerURL(), tb.getSecondaryTransformationServerURL(), tb.getPrimaryTransformationServerURLT(), tb.getSecondaryTransformationServerURLT(), tb.getPrimaryTransformationServerURL1(), tb.getSecondaryTransformationServerURL1(), tb.getPrimaryTransformationServerURLT1(), tb.getSecondaryTransformationServerURLT1(), tb.getPrimaryTransformationServerURLD(), tb.getSecondaryTransformationServerURLD(), tb.getInnerCycles());
    }

    public TransactionBase() {
    }

    public boolean equals(Object arg0) {
        if (!(arg0 instanceof TransactionBase)) {
            return false;
        }
        TransactionBase tb = (TransactionBase)arg0;
        if (!(this.transactionId.equals(tb.transactionId) && this.description.equals(tb.description) && this.solution.equals(tb.solution) && this.transactionStartTime.equals(tb.transactionStartTime) && this.hostedPublic == tb.hostedPublic && this.active == tb.active && (this.primaryTransformationServerURL == null && tb.primaryTransformationServerURL == null || this.primaryTransformationServerURL.equals(tb.primaryTransformationServerURL)) && (this.secondaryTransformationServerURL == null && tb.secondaryTransformationServerURL == null || this.secondaryTransformationServerURL.equals(tb.secondaryTransformationServerURL)) && (this.primaryTransformationServerURLT == null && tb.primaryTransformationServerURLT == null || this.primaryTransformationServerURLT.equals(tb.primaryTransformationServerURLT)) && (this.secondaryTransformationServerURLT == null && tb.secondaryTransformationServerURLT == null || this.secondaryTransformationServerURLT.equals(tb.secondaryTransformationServerURLT)) && (this.primaryTransformationServerURL1 == null && tb.primaryTransformationServerURL1 == null || this.primaryTransformationServerURL1.equals(tb.primaryTransformationServerURL1)) && (this.secondaryTransformationServerURL1 == null && tb.secondaryTransformationServerURL1 == null || this.secondaryTransformationServerURL1.equals(tb.secondaryTransformationServerURL1)) && (this.primaryTransformationServerURLT1 == null && tb.primaryTransformationServerURLT1 == null || this.primaryTransformationServerURLT1.equals(tb.primaryTransformationServerURLT1)) && (this.secondaryTransformationServerURLT1 == null && tb.secondaryTransformationServerURLT1 == null || this.secondaryTransformationServerURLT1.equals(tb.secondaryTransformationServerURLT1)) && (this.primaryTransformationServerURLD == null && tb.primaryTransformationServerURLD == null || this.primaryTransformationServerURLD.equals(tb.primaryTransformationServerURLD)) && (this.secondaryTransformationServerURLD == null && tb.secondaryTransformationServerURLD == null || this.secondaryTransformationServerURLD.equals(tb.secondaryTransformationServerURLD)))) {
            return false;
        }
        if (this.parameters.size() != tb.parameters.size()) {
            return false;
        }
        for (String key : this.parameters.keySet()) {
            ParameterValue tbv = tb.parameters.get(key);
            if (tbv == null) {
                return false;
            }
            ParameterValue thv = this.parameters.get(key);
            if (thv.ParameterValue.equals(tbv.ParameterValue) && thv.fixed == tbv.fixed && thv.upload == tbv.upload && thv.password == tbv.password) continue;
            return false;
        }
        return true;
    }

    public String getPrimaryTransformationServerURLD() {
        return this.primaryTransformationServerURLD;
    }

    public void setPrimaryTransformationServerURLD(String primaryTransformationServerURLD) {
        this.primaryTransformationServerURLD = primaryTransformationServerURLD;
    }

    public String getPrimaryTransformationServerURLT() {
        return this.primaryTransformationServerURLT;
    }

    public void setPrimaryTransformationServerURLT(String primaryTransformationServerURLT) {
        this.primaryTransformationServerURLT = primaryTransformationServerURLT;
    }

    public String getSecondaryTransformationServerURLD() {
        return this.secondaryTransformationServerURLD;
    }

    public void setSecondaryTransformationServerURLD(String secondaryTransformationServerURLD) {
        this.secondaryTransformationServerURLD = secondaryTransformationServerURLD;
    }

    public String getSecondaryTransformationServerURLT() {
        return this.secondaryTransformationServerURLT;
    }

    public void setSecondaryTransformationServerURLT(String secondaryTransformationServerURLT) {
        this.secondaryTransformationServerURLT = secondaryTransformationServerURLT;
    }

    public String getPrimaryTransformationServerURL1() {
        return this.primaryTransformationServerURL1;
    }

    public void setPrimaryTransformationServerURL1(String primaryTransformationServerURL1) {
        this.primaryTransformationServerURL1 = primaryTransformationServerURL1;
    }

    public String getPrimaryTransformationServerURLT1() {
        return this.primaryTransformationServerURLT1;
    }

    public void setPrimaryTransformationServerURLT1(String primaryTransformationServerURLT1) {
        this.primaryTransformationServerURLT1 = primaryTransformationServerURLT1;
    }

    public String getSecondaryTransformationServerURL1() {
        return this.secondaryTransformationServerURL1;
    }

    public void setSecondaryTransformationServerURL1(String secondaryTransformationServerURL1) {
        this.secondaryTransformationServerURL1 = secondaryTransformationServerURL1;
    }

    public String getSecondaryTransformationServerURLT1() {
        return this.secondaryTransformationServerURLT1;
    }

    public void setSecondaryTransformationServerURLT1(String secondaryTransformationServerURLT1) {
        this.secondaryTransformationServerURLT1 = secondaryTransformationServerURLT1;
    }

    public String getSolution() {
        return this.solution == null ? "" : this.solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getInnerCycles() {
        return this.innerCycles;
    }

    public void setInnerCycles(int innerSycles) {
        this.innerCycles = innerSycles;
    }

    public class ParameterValue {
        private String ParameterValue;
        private boolean fixed;
        private boolean upload;
        private boolean password;

        public ParameterValue(String parameterValue, boolean fixed, boolean upload, boolean password) {
            this.ParameterValue = parameterValue;
            this.fixed = fixed;
            this.upload = upload;
            this.password = password;
        }

        private ParameterValue() {
        }

        public final boolean isFixed() {
            return this.fixed;
        }

        public final String getParameterValue() {
            return this.ParameterValue;
        }

        public boolean isPassword() {
            return this.password;
        }

        public boolean isUpload() {
            return this.upload;
        }
    }
}

