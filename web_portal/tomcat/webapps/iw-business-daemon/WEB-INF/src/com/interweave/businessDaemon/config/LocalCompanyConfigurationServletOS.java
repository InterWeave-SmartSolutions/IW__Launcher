package com.interweave.businessDaemon.config;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interweave.businessDaemon.ConfigContext;
import com.interweave.businessDaemon.TransactionContext;
import com.interweave.businessDaemon.TransactionThread;
import com.interweave.businessDaemon.TransactionBase;

/**
 * LocalCompanyConfigurationServletOS - Null-safe replacement for compiled
 * CompanyConfigurationServletOS.
 *
 * The original compiled servlet NPEs at line 74 when the TransactionThread
 * is not found in ConfigContext (e.g. if the static context was cleared
 * between login and form submission).
 *
 * This servlet reproduces the exact same logic but adds null checks and
 * auto-recovers by creating the missing TransactionThread on the fly.
 */
public class LocalCompanyConfigurationServletOS extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- Extract parameters (same as original) ---
        String profile = "";
        String oldProfile = null;
        String solutionType = "";
        String brand = "";
        String solutions = "";

        Map<String, String[]> par = request.getParameterMap();
        Set<String> ks = par.keySet();

        for (String fn : ks) {
            String vl = par.get(fn)[0];
            if ("CurrentProfile".equals(fn)) {
                profile = vl;
            } else if ("Solution".equals(fn)) {
                solutionType = vl;
            } else if ("OldProfile".equals(fn)) {
                oldProfile = vl;
            } else if ("PortalBrand".equals(fn)) {
                brand = vl.trim();
            } else if ("PortalSolutions".equals(fn)) {
                solutions = vl.trim();
            }
        }

        // Build portal brand/solutions query string
        String brandSol = "";
        if (brand != null && brand.length() > 0) {
            brandSol = brandSol + "&PortalBrand=" + brand;
        }
        if (solutions != null && solutions.length() > 0) {
            brandSol = brandSol + "&PortalSolutions=" + solutions;
        }

        // --- Look up the TransactionThread (with null safety) ---
        TransactionThread ptt = null;

        if (oldProfile == null) {
            // New registration path
            TransactionContext ctx = ConfigContext.getCompanyRegistration();
            if (ctx != null) {
                Hashtable threads = ctx.getTransactionThreads();
                if (threads != null) {
                    ptt = (TransactionThread) threads.get(profile);
                }
            }
        } else {
            // Edit/update path
            TransactionContext ctx = ConfigContext.getUpdateCompany();
            if (ctx != null) {
                Hashtable threads = ctx.getTransactionThreads();
                if (threads != null) {
                    ptt = (TransactionThread) threads.get(profile);
                }
            }
        }

        // --- Auto-recover: if TransactionThread is missing, recreate it ---
        if (ptt == null) {
            log("TransactionThread not found for profile '" + profile
                + "' — recreating (session may have been lost from static context)");

            String defaultConfig = "<SF2QBConfiguration></SF2QBConfiguration>";
            TransactionContext ctx;

            if (oldProfile == null) {
                ctx = ConfigContext.getCompanyRegistration();
                if (ctx == null) {
                    log("ConfigContext.getCompanyRegistration() is null — cannot recover");
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Session state lost. Please log in again.");
                    return;
                }
            } else {
                ctx = ConfigContext.getUpdateCompany();
                if (ctx == null) {
                    log("ConfigContext.getUpdateCompany() is null — cannot recover");
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Session state lost. Please log in again.");
                    return;
                }
            }

            ptt = ctx.addTransactionThread(profile);
            if (ptt != null) {
                ptt.putParameter("configuration", defaultConfig);
                ptt.setCompanyConfiguration(defaultConfig);
            } else {
                log("Failed to create TransactionThread for profile: " + profile);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Failed to initialize configuration. Please log in again.");
                return;
            }
        }

        // --- Build the configuration XML from form parameters ---
        // Get current configuration string
        String configStr = "<SF2QBConfiguration></SF2QBConfiguration>";
        try {
            TransactionBase.ParameterValue pv = ptt.getParameters().get("configuration");
            if (pv != null) {
                configStr = pv.getParameterValue();
            }
        } catch (Exception e) {
            log("Could not read existing configuration, using default", e);
        }

        StringBuffer cnfg = new StringBuffer(configStr);

        // Iterate parameters and build/update XML config (same logic as original)
        for (String fn : ks) {
            String vl = par.get(fn)[0];

            // Skip non-config params
            if ("CurrentProfile".equals(fn)) continue;
            if ("submit".equals(fn)) continue;
            if ("OldProfile".equals(fn)) continue;
            if ("Solution".equals(fn)) continue;
            if ("PortalBrand".equals(fn)) continue;
            if ("PortalSolutions".equals(fn)) continue;

            String ft = "<" + fn + ">";
            int ftpos = cnfg.indexOf(ft);

            if (ftpos < 0) {
                // Tag doesn't exist — append it
                cnfg.append(ft + vl + "</" + fn + ">");
            } else {
                // Tag exists — replace value
                ftpos = ftpos + ft.length();
                int ftlpos = cnfg.indexOf("</", ftpos);
                if (ftlpos >= 0) {
                    cnfg.replace(ftpos, ftlpos, vl);
                }
            }
        }

        // Store updated configuration back
        ptt.putParameter("configuration", cnfg.toString());

        // Set admin logged in flag (same as original)
        ConfigContext.setAdminLoggedIn(true);

        // --- Forward to detail page (same as original) ---
        String forwardUrl = "/CompanyConfigurationDetail.jsp?CurrentProfile=" + profile
            + (oldProfile != null ? "&OldProfile=" + oldProfile : "")
            + "&Solution=" + solutionType
            + "&Navigation=F"
            + brandSol;

        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}
