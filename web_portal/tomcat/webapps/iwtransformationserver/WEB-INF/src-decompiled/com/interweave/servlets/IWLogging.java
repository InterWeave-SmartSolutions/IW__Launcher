/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.servlets;

import com.interweave.connector.IWXsltHttp;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IWLogging
extends HttpServlet {
    private IWXsltHttp xsltTransform = null;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.xsltTransform = new IWXsltHttp();
        this.xsltTransform.processLoggingLevel(req);
        resp.sendRedirect("index");
        this.xsltTransform = null;
    }
}

