/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import com.inerweave.sdk.TransactionBase;

public class TransactionContext
extends TransactionBase {
    private boolean runAtStartUp = true;
    private boolean stateful;
    private int numberOfExecutions = 0;
    private long transactionInterval;
    private long transactionShiftFromHartBeat;

    public final boolean isRunAtStartUp() {
        return this.runAtStartUp;
    }

    public final void setRunAtStartUp(boolean runAtStartUp) {
        this.runAtStartUp = runAtStartUp;
    }

    public final int getNumberOfExecutions() {
        return this.numberOfExecutions;
    }

    public final void setNumberOfExecutions(int numberOfExecutions) {
        this.numberOfExecutions = numberOfExecutions;
    }

    public static String toXML(TransactionContext transactionContext) {
        String rootTag = "TransactionDescription Id=\"" + transactionContext.getTransactionId() + "\" Description=\"" + transactionContext.getDescription() + "\" Solution=\"" + transactionContext.getSolution() + "\" Interval=\"" + transactionContext.getTransactionInterval() + "\" Shift=\"" + transactionContext.getTransactionShiftFromHartBeat() + "\" RunAtStartUp=\"" + transactionContext.isRunAtStartUp() + "\" NumberOfExecutions=\"" + transactionContext.getNumberOfExecutions() + "\" InnerCycles=\"" + transactionContext.getInnerCycles() + "\" PrimaryTSURL=\"" + transactionContext.getPrimaryTransformationServerURL() + "\" SecondaryTSURL=\"" + transactionContext.getSecondaryTransformationServerURL() + "\" PrimaryTSURLT=\"" + transactionContext.getPrimaryTransformationServerURLT() + "\" SecondaryTSURLT=\"" + transactionContext.getSecondaryTransformationServerURLT() + "\" PrimaryTSURL1=\"" + transactionContext.getPrimaryTransformationServerURL1() + "\" SecondaryTSURL1=\"" + transactionContext.getSecondaryTransformationServerURL1() + "\" PrimaryTSURLT1=\"" + transactionContext.getPrimaryTransformationServerURLT1() + "\" SecondaryTSURLT1=\"" + transactionContext.getSecondaryTransformationServerURLT1() + "\" PrimaryTSURLD=\"" + transactionContext.getPrimaryTransformationServerURLD() + "\" SecondaryTSURLD=\"" + transactionContext.getSecondaryTransformationServerURLD() + "\" IsActive=\"" + transactionContext.isActive() + "\" IsPublic=\"" + transactionContext.isHostedPublic() + "\" IsStateful=\"" + transactionContext.isStateful();
        StringBuffer transactionInfo = new StringBuffer("<" + rootTag + "\">");
        for (String key : transactionContext.getParameters().keySet()) {
            transactionInfo.append("<Parameter Name=\"" + key + "\" Value=\"" + transactionContext.retrieveParameter(key).getParameterValue() + "\"" + (transactionContext.retrieveParameter(key).isFixed() ? " Fixed=\"true\"" : "") + (transactionContext.retrieveParameter(key).isUpload() ? " Upload=\"true\"" : "") + (transactionContext.retrieveParameter(key).isPassword() ? " Password=\"true\"" : "") + " />");
        }
        transactionInfo.append("<Parameter Name=\"QueryStartTime\" Value=\"" + transactionContext.getTransactionStartTime().toString() + "\"/></" + rootTag.substring(0, rootTag.indexOf(" ")) + ">");
        return transactionInfo.toString();
    }

    public TransactionContext() {
    }

    public TransactionContext(TransactionContext transactionContext) {
        super(transactionContext);
        this.transactionInterval = transactionContext.getTransactionInterval();
        this.transactionShiftFromHartBeat = transactionContext.getTransactionShiftFromHartBeat();
        this.runAtStartUp = transactionContext.isRunAtStartUp();
        this.numberOfExecutions = transactionContext.getNumberOfExecutions();
        this.stateful = transactionContext.isStateful();
    }

    public boolean isStateful() {
        return this.stateful;
    }

    public void setStateful(boolean stateful) {
        this.stateful = stateful;
    }

    public long getTransactionInterval() {
        return this.transactionInterval;
    }

    public void setTransactionInterval(long transactionInterval) {
        this.transactionInterval = transactionInterval;
    }

    public long getTransactionShiftFromHartBeat() {
        return this.transactionShiftFromHartBeat;
    }

    public void setTransactionShiftFromHartBeat(long transactionShiftFromHartBeat) {
        this.transactionShiftFromHartBeat = transactionShiftFromHartBeat;
    }
}

