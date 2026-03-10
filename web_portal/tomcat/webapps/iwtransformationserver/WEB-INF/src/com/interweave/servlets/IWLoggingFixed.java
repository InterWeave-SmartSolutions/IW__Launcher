package com.interweave.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.interweave.connector.IWXsltHttp;

/**
 * Drop-in replacement for IWLogging that fixes NPE when the
 * "applicationname" request parameter is missing.
 *
 * The original IWXsltHttp.processLoggingLevel() reads
 * request.getParameter("applicationname") and passes it directly
 * to IWApplication.getService() without a null check (unlike
 * processDataMap which defaults to "iwtransformationserver").
 * Hashtable.containsKey(null) throws NPE.
 *
 * This wrapper ensures the parameter is always present.
 */
public class IWLoggingFixed extends HttpServlet {

    private static final String DEFAULT_APP = "iwtransformationserver";
    private static final String DEFAULT_LOG_LEVEL = "3";

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final boolean needsApp = req.getParameter("applicationname") == null;
        final boolean needsLevel = req.getParameter("loglevel") == null;
        HttpServletRequest safeReq = req;
        if (needsApp || needsLevel) {
            safeReq = new HttpServletRequestWrapper(req) {
                public String getParameter(String name) {
                    if (needsApp && "applicationname".equals(name)) {
                        return DEFAULT_APP;
                    }
                    if (needsLevel && "loglevel".equals(name)) {
                        return DEFAULT_LOG_LEVEL;
                    }
                    return super.getParameter(name);
                }
            };
        }
        IWXsltHttp xsltTransform = new IWXsltHttp();
        xsltTransform.processLoggingLevel(safeReq);
        resp.sendRedirect("index");
        xsltTransform = null;
    }
}
