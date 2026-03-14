/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.servlets;

import com.interweave.connector.IWXsltHttp;
import com.interweave.core.IWApplication;
import com.interweave.core.IWServices;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IWIndex
extends HttpServlet {
    public void init() throws ServletException {
        String applicationName = this.getServletConfig().getInitParameter("application");
        System.out.println("Initializing IWIndex with the name " + applicationName);
        IWServices lnkIWServices = IWApplication.getService(applicationName, this.getServletContext().getRealPath(""));
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        IWXsltHttp xsltTransform = new IWXsltHttp();
        String appName = req.getContextPath().substring(1);
        PrintWriter out = resp.getWriter();
        xsltTransform.processIndexTransaction(req, appName);
        out.write(xsltTransform.getResponse(req));
    }

    public void destroy() {
    }
}

