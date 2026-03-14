/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import com.inerweave.sdk.TransactionBase;

public class QueryContext
extends TransactionBase {
    public static String toXML(QueryContext queryContext) {
        String rootTag = "Query Id=\"" + queryContext.getTransactionId() + "\" Description=\"" + queryContext.getDescription() + "\" Solution=\"" + queryContext.getSolution() + "\" PrimaryTSURL=\"" + queryContext.getPrimaryTransformationServerURL() + "\" SecondaryTSURL=\"" + queryContext.getSecondaryTransformationServerURL() + "\" PrimaryTSURLT=\"" + queryContext.getPrimaryTransformationServerURLT() + "\" SecondaryTSURLT=\"" + queryContext.getSecondaryTransformationServerURLT() + "\" PrimaryTSURL1=\"" + queryContext.getPrimaryTransformationServerURL1() + "\" SecondaryTSURL1=\"" + queryContext.getSecondaryTransformationServerURL1() + "\" PrimaryTSURLT1=\"" + queryContext.getPrimaryTransformationServerURLT1() + "\" SecondaryTSURLT1=\"" + queryContext.getSecondaryTransformationServerURLT1() + "\" PrimaryTSURLD=\"" + queryContext.getPrimaryTransformationServerURLD() + "\" SecondaryTSURLD=\"" + queryContext.getSecondaryTransformationServerURLD() + "\" InnerCycles=\"" + queryContext.getInnerCycles() + "\" IsActive=\"" + queryContext.isActive() + "\" IsPublic=\"" + queryContext.isHostedPublic();
        StringBuffer queryInfo = new StringBuffer("<" + rootTag + "\">");
        for (String key : queryContext.getParameters().keySet()) {
            queryInfo.append("<Parameter Name=\"" + key + "\" Value=\"" + queryContext.retrieveParameter(key).getParameterValue() + "\"" + (queryContext.retrieveParameter(key).isFixed() ? " Fixed=\"true\"" : "") + (queryContext.retrieveParameter(key).isUpload() ? " Upload=\"true\"" : "") + (queryContext.retrieveParameter(key).isPassword() ? " Password=\"true\"" : "") + " />");
        }
        queryInfo.append("<Parameter Name=\"QueryStartTime\" Value=\"" + queryContext.getTransactionStartTime().toString() + "\"/></" + rootTag.substring(0, rootTag.indexOf(" ")) + ">");
        return queryInfo.toString();
    }

    public QueryContext(QueryContext queryContext) {
        super(queryContext);
    }

    public QueryContext() {
    }
}

