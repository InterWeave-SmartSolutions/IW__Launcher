/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.http;

import com.interweave.adapter.http.IWXMLHierarchicalAdaptor;
import com.interweave.core.IWRequest;

public class IWJSonCookieAdaptor
extends IWXMLHierarchicalAdaptor {
    public StringBuffer go(IWRequest request) throws Exception {
        return super.go(request, true, true);
    }
}

