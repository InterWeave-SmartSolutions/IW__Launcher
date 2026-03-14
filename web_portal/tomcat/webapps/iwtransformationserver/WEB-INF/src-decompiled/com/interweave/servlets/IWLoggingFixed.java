/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.servlets;

import com.interweave.connector.IWXsltHttp;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class IWLoggingFixed
extends HttpServlet {
    private static final String DEFAULT_APP = "iwtransformationserver";
    private static final String DEFAULT_LOG_LEVEL = "3";

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        this.doPost(httpServletRequest, httpServletResponse);
    }

    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        final boolean bl = httpServletRequest.getParameter("applicationname") == null;
        final boolean bl2 = httpServletRequest.getParameter("loglevel") == null;
        Object object = httpServletRequest;
        if (bl || bl2) {
            object = new HttpServletRequestWrapper(this, httpServletRequest){
                final /* synthetic */ IWLoggingFixed this$0;
                {
                    this.this$0 = iWLoggingFixed;
                    super(httpServletRequest);
                }

                public String getParameter(String string) {
                    if (bl && "applicationname".equals(string)) {
                        return IWLoggingFixed.DEFAULT_APP;
                    }
                    if (bl2 && "loglevel".equals(string)) {
                        return IWLoggingFixed.DEFAULT_LOG_LEVEL;
                    }
                    return super.getParameter(string);
                }
            };
        }
        IWXsltHttp iWXsltHttp = new IWXsltHttp();
        iWXsltHttp.processLoggingLevel((HttpServletRequest)object);
        httpServletResponse.sendRedirect("index");
        iWXsltHttp = null;
    }
}

