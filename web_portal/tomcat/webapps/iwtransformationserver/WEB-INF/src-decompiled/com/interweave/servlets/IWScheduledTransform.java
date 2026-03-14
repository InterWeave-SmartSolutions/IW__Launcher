/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.servlets;

import com.interweave.connector.IWXsltHttp;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IWScheduledTransform
extends HttpServlet
implements Servlet {
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IWXsltHttp xsltTransform = new IWXsltHttp();
        PrintWriter out = response.getWriter();
        xsltTransform.processDataMap(request);
        out.write(xsltTransform.getScheduledResponse());
        out.close();
    }
}

