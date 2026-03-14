/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.session;

import com.interweave.core.IWServices;
import com.interweave.session.IWSession;
import com.interweave.session.IWSessionMonitor;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import org.doomdark.uuid.UUIDGenerator;

public class IWSessionManager {
    public String sessionSelectTran = "SessionSelect";
    public String sessionInsertTran = "SessionNew";
    public String sessionUpdateTran = "SessionUpdate";
    public String sessionDeleteTran = "SessionDelete";
    private Hashtable sessions = new Hashtable();
    private IWSessionMonitor lnkIWSessionMonitor = new IWSessionMonitor();
    private IWServices lnkIWServices = null;

    public IWSessionManager(IWServices lnkServices) {
        this.lnkIWServices = lnkServices;
    }

    public IWSession setSession(HttpServletRequest request) {
        boolean counter = true;
        IWSession iwSession = null;
        String sessionID = request.getParameter("sessionid");
        if (sessionID != null && this.sessions.size() > 0 && this.sessions.containsKey(sessionID)) {
            this.lnkIWServices.logConsole("Retreiving Session: " + this.lnkIWServices.appName + ":" + request.getSession().getId(), IWServices.LOG_ERRORS, null);
            iwSession = (IWSession)this.sessions.get(sessionID);
            iwSession.setValues(request);
            long curTime = System.currentTimeMillis();
            if (iwSession.timeOut * 60000L + iwSession.startTime < curTime) {
                return null;
            }
            iwSession.startTime = System.currentTimeMillis();
        } else {
            iwSession = new IWSession(this.lnkIWServices);
            UUIDGenerator uuid_gen = UUIDGenerator.getInstance();
            sessionID = uuid_gen.generateTimeBasedUUID().toString();
            this.sessions.put(sessionID, iwSession);
            iwSession.setValues(request);
            iwSession.setValue("sessionid", sessionID);
            this.lnkIWServices.logConsole("Initializing Session: " + this.lnkIWServices.appName + ":" + sessionID, IWServices.LOG_REQUEST, null);
            Enumeration enumerate = this.lnkIWServices.appvars.keys();
            String key = null;
            String value = null;
            while (enumerate.hasMoreElements()) {
                key = (String)enumerate.nextElement();
                value = (String)this.lnkIWServices.appvars.get(key);
                iwSession.setValue(key, value);
                key = null;
                value = null;
            }
        }
        iwSession.setValues(request);
        return iwSession;
    }

    public IWSession setSession(String sessionid) {
        IWSession iwSession = null;
        if (this.sessions.size() > 0 && this.sessions.containsKey(sessionid)) {
            this.lnkIWServices.logConsole("Retreiving Session: " + this.lnkIWServices.appName + ":" + sessionid, IWServices.LOG_ERRORS, null);
            iwSession = (IWSession)this.sessions.get(sessionid);
        } else {
            this.lnkIWServices.logConsole("Initializing Session: " + this.lnkIWServices.appName + ":" + sessionid, IWServices.LOG_REQUEST, null);
            iwSession = new IWSession(this.lnkIWServices);
            this.sessions.put(sessionid, iwSession);
            iwSession.setValue("sessionid", sessionid);
            this.lnkIWServices.logConsole("Initializing Session: " + this.lnkIWServices.appName + ":" + sessionid, IWServices.LOG_REQUEST, null);
            Enumeration enumerate = this.lnkIWServices.appvars.keys();
            String key = null;
            String value = null;
            while (enumerate.hasMoreElements()) {
                key = (String)enumerate.nextElement();
                value = (String)this.lnkIWServices.appvars.get(key);
                iwSession.setValue(key, value);
                key = null;
                value = null;
            }
        }
        return iwSession;
    }

    public void dumpKeys(String prompt, Hashtable theHash) {
        Enumeration enumerate = theHash.keys();
        System.out.println(prompt);
        while (enumerate.hasMoreElements()) {
            System.out.println("\t" + (String)enumerate.nextElement());
        }
    }

    public void remove(IWSession session) {
        this.sessions.remove(session.getValue("sessionid"));
    }
}

