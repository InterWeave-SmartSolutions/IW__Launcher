/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.http;

import com.interweave.adapter.IWBaseAdaptor;
import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import com.interweave.encrypt.IWBase64Encode;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class IWHttpBaseAdaptor
extends IWBaseAdaptor {
    protected HttpURLConnection httpURLConnection;
    private boolean multipleRequests = false;
    private boolean encoded = false;
    private boolean emptyRequest = false;
    private boolean nameValues = false;
    private boolean proxyKeepAlive = true;
    private boolean unescape = false;
    private boolean noInputStream = false;
    protected String encodingType = null;
    protected static String urlEncoding = "application/x-www-form-urlencoded";
    protected static final String soapActionKey = "HTTP_SOAP_ACTION:";
    protected static final String soapBasicAuthKey = "HTTP_BASIC_AUTH:";
    protected static final String soapWSSEAuthKey = "HTTP_WSSE_AUTH:";
    private String httpRequestMethod;
    private String regularAuthirizationToken = null;
    private String basicAuthirizationCredentials = null;
    private String wsAuthirizationCredentials = null;
    protected String soapAction = null;
    private String encodingCharSet = null;
    private Hashtable<String, String> httpparameters = null;

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.setup(map, request, false);
    }

    public void setup(Datamap map, IWRequest request, boolean xml) throws Exception {
        this.setupInitConnect(map, request);
        this.encodingType = null;
        this.encoded = false;
        this.unescape = false;
        this.emptyRequest = false;
        this.nameValues = false;
        this.regularAuthirizationToken = null;
        this.basicAuthirizationCredentials = null;
        this.wsAuthirizationCredentials = null;
        this.soapAction = null;
        this.httpRequestMethod = "POST";
        this.noInputStream = false;
        if (this.contentType != null && this.contentType.trim().length() > 0) {
            String[] ctp = this.contentType.split(" ");
            this.httpparameters = new Hashtable();
            boolean nameWithSpace = false;
            int cntr = 0;
            String[] stringArray = ctp;
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String ctc = stringArray[n];
                request.lnkIWServices.logConsole("Current Parameter Token in HttpAdaptor: " + ctc + " " + ++cntr, IWServices.LOG_REQUEST, request);
                if (nameWithSpace) {
                    nameWithSpace = false;
                } else if (ctc.startsWith(soapActionKey)) {
                    this.soapAction = ctc.substring(soapActionKey.length()).trim();
                    request.lnkIWServices.logConsole("SOAPAction in HttpAdaptor: " + this.soapAction, IWServices.LOG_REQUEST, request);
                } else if (ctc.startsWith(soapBasicAuthKey)) {
                    String baCred = ctc.substring(soapBasicAuthKey.length()).trim();
                    if (baCred.indexOf(":") < 0 && cntr < ctp.length) {
                        baCred = String.valueOf(baCred) + " " + ctp[cntr];
                        nameWithSpace = true;
                    }
                    this.basicAuthirizationCredentials = IWBase64Encode.base64Encrypt(baCred).replaceAll("\r", "").replaceAll("\n", "");
                    request.lnkIWServices.logConsole("BA creds in HttpAdaptor: " + this.basicAuthirizationCredentials, IWServices.LOG_REQUEST, request);
                } else if (ctc.startsWith(soapWSSEAuthKey)) {
                    int wsci;
                    String wsCred = ctc.substring(soapWSSEAuthKey.length()).trim();
                    if (wsCred.indexOf(":") < 0 && cntr < ctp.length) {
                        wsCred = String.valueOf(wsCred) + " " + ctp[cntr];
                        nameWithSpace = true;
                    }
                    if ((wsci = wsCred.indexOf(":")) >= 0) {
                        String wsUser = wsCred.substring(0, wsci);
                        String wsPassword = wsCred.substring(wsci + 1);
                        SimpleDateFormat createdat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        createdat.setTimeZone(TimeZone.getTimeZone("GMT"));
                        String timestamp = createdat.format(Calendar.getInstance().getTime()).trim();
                        String rand = Long.toString(new Date().getTime());
                        String nonce = IWBase64Encode.base64Encrypt(rand).trim();
                        MessageDigest md = MessageDigest.getInstance("SHA-1");
                        md.reset();
                        md.update(rand.getBytes());
                        md.update(timestamp.getBytes());
                        md.update(wsPassword.getBytes());
                        String passwordDigest = IWBase64Encode.base64Encrypt(md.digest()).trim();
                        this.wsAuthirizationCredentials = "Username=\"" + wsUser.trim() + "\", " + "PasswordDigest=\"" + passwordDigest + "\", " + "Nonce=\"" + nonce + "\", " + "Created=\"" + timestamp + "\"";
                    }
                    request.lnkIWServices.logConsole("WS creds in HttpAdaptor: " + this.wsAuthirizationCredentials, IWServices.LOG_REQUEST, request);
                } else if (ctc.startsWith("PAR[")) {
                    String param = ctc.trim();
                    if (param.indexOf("]") < 0 && cntr < ctp.length) {
                        param = String.valueOf(param) + " " + ctp[cntr];
                        nameWithSpace = true;
                    }
                    int es = param.indexOf("=");
                    int lb = param.indexOf("]");
                    if (es > 4 && lb > 4) {
                        String pnm = param.substring(4, es);
                        String pnv = param.substring(es + 1, lb);
                        if (pnm.equals("Authorization")) {
                            this.regularAuthirizationToken = pnv;
                            request.lnkIWServices.logConsole("Regular Auth in HttpAdaptor: " + this.regularAuthirizationToken, IWServices.LOG_REQUEST, request);
                        } else {
                            this.httpparameters.put(pnm, pnv);
                        }
                        request.lnkIWServices.logConsole("HTTP Parameter: " + pnm + "=" + pnv, IWServices.LOG_REQUEST, request);
                    }
                } else if (ctc.equals("NOINPUT")) {
                    this.noInputStream = true;
                    request.lnkIWServices.logConsole("Request method with no input stream is used", IWServices.LOG_TRACE, request);
                } else if (ctc.equals("NOPROXY")) {
                    this.proxyKeepAlive = false;
                    request.lnkIWServices.logConsole("Request method with no proxy is used", IWServices.LOG_TRACE, request);
                } else if (ctc.equals("GET")) {
                    this.httpRequestMethod = "GET";
                    request.lnkIWServices.logConsole("Get request method is used", IWServices.LOG_TRACE, request);
                } else if (ctc.equals("PUT")) {
                    this.httpRequestMethod = "PUT";
                    request.lnkIWServices.logConsole("Put request method is used", IWServices.LOG_TRACE, request);
                } else if (ctc.equals("EMPTY")) {
                    this.emptyRequest = true;
                    request.lnkIWServices.logConsole("Empty request is used", IWServices.LOG_TRACE, request);
                } else if (ctc.equals("NVP")) {
                    this.nameValues = true;
                    request.lnkIWServices.logConsole("Name/value pairs input is used", IWServices.LOG_TRACE, request);
                } else if (ctc.equals("UNESCAPE")) {
                    this.unescape = true;
                    request.lnkIWServices.logConsole("Unescaping response is required", IWServices.LOG_TRACE, request);
                } else {
                    this.encodingType = ctc.trim();
                    if (this.encodingType.length() == 0) {
                        request.lnkIWServices.logConsole("Empty Encoding in HttpAdaptor", IWServices.LOG_REQUEST, request);
                        this.encodingType = null;
                    } else {
                        request.lnkIWServices.logConsole("Encoding in HttpAdaptor: " + this.encodingType, IWServices.LOG_REQUEST, request);
                        this.encoded = this.encodingType.indexOf(urlEncoding) >= 0;
                        int csi = this.encodingType.indexOf(String.valueOf(urlEncoding) + ":");
                        if (csi >= 0) {
                            int cssp = this.encodingType.indexOf(" ", csi += urlEncoding.length() + 1);
                            this.encodingCharSet = cssp > 0 ? this.encodingType.substring(csi, cssp) : this.encodingType.substring(csi);
                            if (this.encodingCharSet.equalsIgnoreCase("NODECODE") || this.encodingCharSet.equalsIgnoreCase("NOENCODE") || this.encodingCharSet.equalsIgnoreCase("GETENCODE") || this.encodingCharSet.equalsIgnoreCase("NONE")) {
                                this.encodingType = this.encodingType.substring(0, csi - 1);
                            }
                        }
                    }
                }
                ++n;
            }
        }
        try {
            this.connect2HttpServer(xml, request);
        }
        catch (Exception e) {
            request.setConnectionError(true);
            request.setConnectionFailures(request.getConnectionFailures() + 1);
            request.setQueryStartTime(request.getInitialQueryStartTime());
            throw e;
        }
    }

    private void setHTTPParameters(IWRequest request) {
        if (this.httpparameters != null && this.httpparameters.size() > 0) {
            Set<String> ks = this.httpparameters.keySet();
            for (String fn : ks) {
                if (fn == null) continue;
                String vl = this.httpparameters.get(fn);
                if (fn.equalsIgnoreCase("host") && vl.equalsIgnoreCase("__URL__")) {
                    vl = this.httpURLConnection.getURL().getHost();
                }
                if (vl.equalsIgnoreCase("__GUID__")) {
                    vl = String.valueOf(String.valueOf(System.currentTimeMillis())) + String.valueOf(System.currentTimeMillis() * 2L);
                }
                request.lnkIWServices.logConsole("HTTP Parameter out: " + fn + "=" + vl, IWServices.LOG_REQUEST, request);
                try {
                    this.httpURLConnection.setRequestProperty(fn, vl);
                }
                catch (RuntimeException e) {
                    request.lnkIWServices.logConsole("Cannot set HTTP parameter: " + fn + "=" + vl, IWServices.LOG_ERRORS, request);
                }
            }
        }
    }

    private void connect2HttpServer(boolean xml, IWRequest request) throws Exception {
        int qp;
        request.lnkIWServices.logConsole("connect2HttpServer started. HTTP URL= " + this.httpURL, IWServices.LOG_REQUEST, request);
        if (this.httpURL.trim().length() == 0) {
            this.httpURLConnection = null;
            return;
        }
        if (xml) {
            this.httpURL = IWServices.unEscape(this.httpURL);
        }
        int qmp = this.httpURL.indexOf("?");
        int ppp = this.httpURL.indexOf("|");
        if (qmp < 0 && ppp > 0) {
            this.httpURL = String.valueOf(this.httpURL.substring(0, ppp)) + "?" + this.httpURL.substring(ppp + 1);
        }
        if ((qp = this.httpURL.indexOf("?")) > 0) {
            String rurl = this.httpURL.substring(0, qp);
            String params = this.httpURL.substring(qp + 1);
            String ecs = "UTF-8";
            if (!(this.encodingCharSet == null || this.encodingCharSet.equalsIgnoreCase("NOENCODE") || this.encodingCharSet.equalsIgnoreCase("NODECODE") || this.encodingCharSet.equalsIgnoreCase("NONE") || this.encodingCharSet.equalsIgnoreCase("GETENCODE"))) {
                ecs = this.encodingCharSet;
            }
            String[] gprs = params.split("&");
            StringBuffer agprs = new StringBuffer("");
            int aai = 0;
            while (aai < gprs.length) {
                String cgprs = gprs[aai];
                int ai = cgprs.indexOf("=");
                if (ai > 0) {
                    agprs.append(cgprs.substring(0, ai + 1)).append(URLEncoder.encode(cgprs.substring(ai + 1), ecs));
                } else {
                    agprs.append(URLEncoder.encode(cgprs, ecs));
                }
                if (aai < gprs.length - 1) {
                    agprs.append("&");
                }
                ++aai;
            }
            params = agprs.toString();
            this.httpURL = String.valueOf(rurl) + "?" + params;
            request.lnkIWServices.logConsole("connect2HttpServer started. HTTP URL final= " + this.httpURL, IWServices.LOG_REQUEST, request);
        }
        URL httpServerURL = new URL(this.httpURL);
        request.lnkIWServices.logConsole("connect2HttpServer: URL created", IWServices.LOG_REQUEST, request);
        this.httpURLConnection = (HttpURLConnection)httpServerURL.openConnection();
        request.lnkIWServices.logConsole("connect2HttpServer: connection opened", IWServices.LOG_REQUEST, request);
        if (this.httpsOldHost != null && this.httpsOldHost.length() > 0) {
            this.httpURLConnection.setRequestProperty("Host", this.httpsOldHost);
            request.lnkIWServices.logConsole("connect2HttpServer: Old Host set= " + this.httpsOldHost, IWServices.LOG_REQUEST, request);
        }
        this.httpURLConnection.setDoInput(true);
        this.httpURLConnection.setDoOutput(true);
        this.httpURLConnection.setUseCaches(false);
        this.httpURLConnection.setRequestMethod(this.httpRequestMethod);
        if (this.proxyKeepAlive) {
            this.httpURLConnection.setRequestProperty("Proxy-Connection", "Keep-Alive");
            request.lnkIWServices.logConsole("connect2HttpServer: Proxy-Connection set", IWServices.LOG_REQUEST, request);
        }
        if (this.basicAuthirizationCredentials != null) {
            this.httpURLConnection.setRequestProperty("Authorization", "Basic " + this.basicAuthirizationCredentials);
            request.lnkIWServices.logConsole("connect2HttpServer: Basic Auth set " + this.basicAuthirizationCredentials, IWServices.LOG_REQUEST, request);
        }
        if (this.regularAuthirizationToken != null) {
            this.httpURLConnection.setRequestProperty("Authorization", this.regularAuthirizationToken);
            request.lnkIWServices.logConsole("connect2HttpServer: Regular Auth set " + this.regularAuthirizationToken, IWServices.LOG_REQUEST, request);
        }
        if (this.wsAuthirizationCredentials != null) {
            this.httpURLConnection.setRequestProperty("Authorization", "WSSE profile=\"UsernameToken\"");
            this.httpURLConnection.setRequestProperty("X-WSSE", "UsernameToken " + this.wsAuthirizationCredentials);
            request.lnkIWServices.logConsole("connect2HttpServer: WS Auth set " + this.wsAuthirizationCredentials, IWServices.LOG_REQUEST, request);
        }
        if (this.httpRequestMethod.equals("PUT") || this.httpRequestMethod.equals("POST")) {
            String ctProperty = this.encodingType == null ? (xml ? "text/xml; charset=utf-8" : "text/plain") : this.encodingType;
            this.httpURLConnection.setRequestProperty("Content-type", ctProperty);
            request.lnkIWServices.logConsole("connect2HttpServer: Content-type set " + ctProperty, IWServices.LOG_REQUEST, request);
        }
        request.lnkIWServices.logConsole("connect2HttpServer ended", IWServices.LOG_REQUEST, request);
    }

    public StringBuffer go(IWRequest request) throws Exception {
        return this.go(request, "", "", false, null);
    }

    public StringBuffer go(IWRequest request, String prefix, String suffix, boolean isXML, StringBuffer requestBuffer) throws Exception {
        boolean prsu = prefix.length() > 0 || suffix.length() > 0;
        boolean errorHappened = false;
        StringBuffer endResult = new StringBuffer();
        Enumeration enumerate = this.accessList.elements();
        if (enumerate.hasMoreElements()) {
            Access access = (Access)((Object)enumerate.nextElement());
            this.curAccess = null;
            request.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".go", IWServices.LOG_TRACE, request);
            if (access != null) {
                this.curAccess = access;
                String stpre = this.curAccess.getStatementpre();
                if (this.httpRequestMethod.equals("GET") || stpre != null && stpre.trim().length() > 0 || this.emptyRequest) {
                    if (this.multipleRequests) {
                        stpre = String.valueOf(prefix) + stpre + suffix;
                    }
                    request.lnkIWServices.logConsole("Statement Pre: " + stpre, IWServices.LOG_TRACE, request);
                    if (stpre != null && stpre.trim().length() > 0 && isXML && (!this.curAccess.isDynamic() || this.nameValues)) {
                        stpre = IWServices.unEscape(stpre);
                    }
                    if (stpre != null && stpre.trim().length() > 0) {
                        stpre = IWServices.replaceParameters(stpre, this.curAccess, request, this.dataMap);
                    }
                    if (this.httpRequestMethod.equals("GET") || stpre != null && stpre.trim().length() > 0 || this.emptyRequest) {
                        if (this.httpURLConnection != null) {
                            if (stpre != null && stpre.trim().length() > 0) {
                                this.httpURLConnection.setRequestProperty("Content-Length", String.valueOf(stpre.length()));
                            }
                            this.setHTTPParameters(request);
                            Map<String, List<String>> requestProperties = this.httpURLConnection.getRequestProperties();
                            for (Map.Entry<String, List<String>> entry : requestProperties.entrySet()) {
                                request.lnkIWServices.logConsole("HTTP Full Parameter List Entry: " + entry.getKey() + " = " + entry.getValue(), IWServices.LOG_REQUEST, request);
                            }
                            request.lnkIWServices.logConsole("HTTP Request URL: " + this.httpURLConnection.getURL().toString(), IWServices.LOG_REQUEST, request);
                            request.lnkIWServices.logConsole("Http output: " + stpre, IWServices.LOG_HTTP, request);
                        }
                        String[] stprep = null;
                        stprep = stpre != null && stpre.trim().length() > 0 ? (!(this.httpRequestMethod.equals("GET") || this.nameValues || !isXML || this.encoded && this.encodingCharSet != null && this.encodingCharSet.equalsIgnoreCase("GETENCODE")) ? IWServices.splitXML(stpre) : stpre.split("`")) : new String[]{""};
                        request.lnkIWServices.logConsole("Http output splitted on " + stprep.length, IWServices.LOG_REQUEST, request);
                        int ic = 0;
                        while (ic < stprep.length) {
                            if (stpre != null && stpre.trim().length() > 0) {
                                request.lnkIWServices.logConsole("Http output #" + ic + " prepared", IWServices.LOG_REQUEST, request);
                                String stprec = stprep[ic];
                                if (this.httpURLConnection != null) {
                                    this.httpURLConnection.setRequestProperty("Content-Length", String.valueOf(stprec.length()));
                                }
                                if (stprec.startsWith("%URL=") || stprec.startsWith("%URP=") || stprec.startsWith("%URS=")) {
                                    int upe = stprec.lastIndexOf("%");
                                    if (upe > 0 && stprec.contains(" %STP:") && stprec.indexOf("%STP:") == upe) {
                                        upe = stprec.indexOf("%", upe + 11);
                                    }
                                    if (upe > 0) {
                                        this.httpURL = stprec.substring(5, upe);
                                        int qmp = this.httpURL.indexOf("?");
                                        int ppp = this.httpURL.indexOf("|");
                                        if (qmp < 0 && ppp > 0) {
                                            this.httpURL = String.valueOf(this.httpURL.substring(0, ppp)) + "?" + this.httpURL.substring(ppp + 1);
                                        }
                                        String pR = "38";
                                        String pPR = "";
                                        if (this.httpURL.contains("https:") & this.httpURL.contains(" %STP:")) {
                                            int eurl = this.httpURL.indexOf(" %STP:");
                                            int eurle = this.httpURL.indexOf("%", eurl + 2);
                                            int edns = this.httpURL.indexOf("/", 8);
                                            String fUrl = this.httpURL.substring(8, edns > 8 ? edns : eurl);
                                            String stpStr = "";
                                            stpStr = eurle > 0 ? this.httpURL.substring(eurl, eurle + 1) : this.httpURL.substring(eurl);
                                            String stpVal = "";
                                            stpVal = eurle > 0 ? this.httpURL.substring(eurl + 6, eurle).trim() : this.httpURL.substring(eurl + 6).trim();
                                            this.httpsOldHost = fUrl;
                                            this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(fUrl, "127.0.0.1:" + stpVal).replaceFirst(stpStr, "");
                                        } else if (this.httpURL.contains("login.salesforce.com")) {
                                            this.httpsOldHost = "login.salesforce.com";
                                            this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst("login.salesforce.com", "127.0.0.1:38500");
                                        } else if (this.httpURL.contains("www.salesforce.com")) {
                                            this.httpsOldHost = "www.salesforce.com";
                                            this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst("www.salesforce.com", "127.0.0.1:38500");
                                        } else if (this.httpURL.contains(".my.salesforce.com")) {
                                            String s2rpl = String.valueOf(this.httpURL.substring(8, this.httpURL.indexOf(".my.salesforce.com"))) + ".my.salesforce.com";
                                            int extPort = 9 - request.getTranRecReqExt();
                                            this.httpsOldHost = s2rpl;
                                            this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(s2rpl, "127.0.0.1:3849" + extPort);
                                        } else if (this.httpURL.contains("https://na") && this.httpURL.contains("salesforce.com")) {
                                            String sfInst = this.httpURL.substring(10, this.httpURL.indexOf("."));
                                            if (sfInst.length() > 2) {
                                                pPR = sfInst.substring(0, sfInst.length() - 2);
                                                sfInst = sfInst.substring(sfInst.length() - 2);
                                                pR = "40";
                                            }
                                            this.httpsOldHost = "na" + pPR + sfInst + ".salesforce.com";
                                            this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(this.httpsOldHost, "127.0.0.1:" + pR + "5" + (sfInst.length() == 1 ? "0" : "") + sfInst);
                                        } else if (this.httpURL.contains("https://ap") && this.httpURL.contains("salesforce.com")) {
                                            String sfInst = this.httpURL.substring(10, this.httpURL.indexOf("."));
                                            if (sfInst.length() > 2) {
                                                pPR = sfInst.substring(0, sfInst.length() - 2);
                                                sfInst = sfInst.substring(sfInst.length() - 2);
                                                pR = "40";
                                            }
                                            this.httpsOldHost = "ap" + pPR + sfInst + ".salesforce.com";
                                            this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(this.httpsOldHost, "127.0.0.1:" + pR + "7" + (sfInst.length() == 1 ? "0" : "") + sfInst);
                                        } else if (this.httpURL.contains("https://eu") && this.httpURL.contains("salesforce.com")) {
                                            String sfInst = this.httpURL.substring(10, this.httpURL.indexOf("."));
                                            if (sfInst.length() > 2) {
                                                pPR = sfInst.substring(0, sfInst.length() - 2);
                                                sfInst = sfInst.substring(sfInst.length() - 2);
                                                pR = "40";
                                            }
                                            this.httpsOldHost = "eu" + pPR + sfInst + ".salesforce.com";
                                            this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(this.httpsOldHost, "127.0.0.1:" + pR + "8" + (sfInst.length() == 1 ? "0" : "") + sfInst);
                                        } else if (this.httpURL.contains("test.salesforce.com")) {
                                            this.httpsOldHost = "test.salesforce.com";
                                            this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst("test.salesforce.com", "127.0.0.1:38600");
                                        } else if (this.httpURL.contains("https://cs") && this.httpURL.contains("salesforce.com")) {
                                            String sfInst = this.httpURL.substring(10, this.httpURL.indexOf("."));
                                            if (sfInst.length() > 2) {
                                                pPR = sfInst.substring(0, sfInst.length() - 2);
                                                sfInst = sfInst.substring(sfInst.length() - 2);
                                                pR = "40";
                                            }
                                            this.httpsOldHost = "cs" + pPR + sfInst + ".salesforce.com";
                                            this.httpURL = this.httpURL.replaceFirst("https", "http").replaceFirst(this.httpsOldHost, "127.0.0.1:" + pR + "6" + (sfInst.length() == 1 ? "0" : "") + sfInst);
                                        }
                                        request.lnkIWServices.logConsole("IWBaseAdaptor - Proxy service used - " + this.httpURL, IWServices.LOG_HTTP, request);
                                        this.closeConnection();
                                        if (stprec.startsWith("%URP=")) {
                                            Thread.sleep(5000L);
                                        } else if (stprec.startsWith("%URS=")) {
                                            Thread.sleep(10000L);
                                        }
                                        this.connect2HttpServer(isXML, request);
                                        stprec = stprec.length() > upe + 1 ? stprec.substring(upe + 1) : "";
                                        this.httpURLConnection.setRequestProperty("Content-Length", String.valueOf(stprec.length()));
                                        this.setHTTPParameters(request);
                                        if (this.httpsOldHost != null && this.httpsOldHost.length() > 0) {
                                            this.httpURLConnection.setRequestProperty("Host", this.httpsOldHost);
                                            request.lnkIWServices.logConsole("Old Host set= " + this.httpsOldHost, IWServices.LOG_REQUEST, request);
                                        }
                                    }
                                }
                                if (stprec.length() > 0) {
                                    DataOutputStream ost;
                                    if (this.encoded) {
                                        request.lnkIWServices.logConsole("Http output #" + ic + " before encoding=" + stprec, IWServices.LOG_TRACE, request);
                                        if (this.encodingCharSet == null || this.encodingCharSet.equalsIgnoreCase("NODECODE")) {
                                            stprec = URLEncoder.encode(stprec, "UTF-8");
                                        } else if (!(this.encodingCharSet.equalsIgnoreCase("NOENCODE") || this.encodingCharSet.equalsIgnoreCase("NONE") || this.encodingCharSet.equalsIgnoreCase("GETENCODE"))) {
                                            stprec = URLEncoder.encode(stprec, this.encodingCharSet);
                                        } else if (this.encodingCharSet.equalsIgnoreCase("GETENCODE")) {
                                            String[] gprs = stprec.split("&");
                                            StringBuffer agprs = new StringBuffer("");
                                            int aai = 0;
                                            while (aai < gprs.length) {
                                                String cgprs = gprs[aai];
                                                int ai = cgprs.indexOf("=");
                                                if (ai > 0) {
                                                    agprs.append(cgprs.substring(0, ai + 1)).append(URLEncoder.encode(cgprs.substring(ai + 1), "UTF-8"));
                                                } else {
                                                    agprs.append(URLEncoder.encode(cgprs, "UTF-8"));
                                                }
                                                if (aai < gprs.length - 1) {
                                                    agprs.append("&");
                                                }
                                                ++aai;
                                            }
                                            stprec = agprs.toString();
                                        }
                                        this.httpURLConnection.setRequestProperty("Content-Length", String.valueOf(stprec.length()));
                                    }
                                    try {
                                        ost = new DataOutputStream(this.httpURLConnection.getOutputStream());
                                        request.lnkIWServices.logConsole("output stream created", IWServices.LOG_REQUEST, request);
                                    }
                                    catch (IOException e) {
                                        request.setFlowSuccess(false);
                                        if (this.curAccess.getStatementpost().indexOf("%manual_commit%") >= 0) {
                                            request.setQueryStartTime(request.getInitialQueryStartTime());
                                            this.iwRequest.setReturnString(this.iwRequest.getInitialReturnString());
                                            System.arraycopy(this.iwRequest.getInitialOtherQueryStartTime(), 0, this.iwRequest.getOtherQueryStartTime(), 0, 10);
                                            System.arraycopy(this.iwRequest.getInitialOtherReturnString(), 0, this.iwRequest.getOtherReturnString(), 0, 10);
                                        }
                                        request.setConnectionError(false);
                                        request.setConnectionFailures(request.getConnectionFailures() - 1);
                                        throw e;
                                    }
                                    if (!this.nameValues && isXML) {
                                        int elp;
                                        String xDecl;
                                        int ep;
                                        String enc = "UTF-8";
                                        if (this.curAccess.isDynamic() && stprec.trim().startsWith("<?") && stprec.trim().substring(2).trim().startsWith("xml") && (ep = (xDecl = stprec.substring(0, stprec.indexOf("?>"))).indexOf("encoding=\"")) > 0 && (elp = xDecl.indexOf("\"", ep += 10)) > 0) {
                                            enc = xDecl.substring(ep, elp);
                                        }
                                        request.lnkIWServices.logConsole("Http output #" + ic + " prepared for XML:|" + stprec + "| Length=" + stprec.length(), IWServices.LOG_REQUEST, request);
                                        byte[] ce = stprec.getBytes(enc);
                                        ost.write(ce);
                                        request.lnkIWServices.logConsole("Http output #" + ic + " written for XML; Content Type=" + this.httpURLConnection.getRequestProperty("Content-Type") + " Content Lenght=" + this.httpURLConnection.getRequestProperty("Content-Length"), IWServices.LOG_REQUEST, request);
                                    } else {
                                        request.lnkIWServices.logConsole("Http output #" + ic + " prepared for non XML:|" + stprec + "| Length=" + stprec.length(), IWServices.LOG_REQUEST, request);
                                        ost.writeBytes(stprec);
                                        request.lnkIWServices.logConsole("Http output #" + ic + " written for non XML; Content Type=" + this.httpURLConnection.getRequestProperty("Content-Type") + " Content Lenght=" + this.httpURLConnection.getRequestProperty("Content-Length"), IWServices.LOG_REQUEST, request);
                                    }
                                    ost.flush();
                                    ost.close();
                                    request.lnkIWServices.logConsole("Http output #" + ic + " sent", IWServices.LOG_REQUEST, request);
                                    if (requestBuffer != null) {
                                        requestBuffer.append(stprec);
                                    }
                                }
                            }
                            if (this.httpURLConnection != null) {
                                request.lnkIWServices.logConsole("reading started", IWServices.LOG_REQUEST, request);
                                StringBuffer result = new StringBuffer("");
                                int bufferSize = 4096;
                                char[] temp = new char[bufferSize];
                                int inputSuccess = 1;
                                if (!this.noInputStream) {
                                    InputStreamReader ist;
                                    block90: {
                                        ist = null;
                                        try {
                                            ist = new InputStreamReader(this.httpURLConnection.getInputStream());
                                            request.lnkIWServices.logConsole("Input Stream created", IWServices.LOG_REQUEST, request);
                                        }
                                        catch (IOException e) {
                                            inputSuccess = -1;
                                            request.setFlowSuccess(false);
                                            errorHappened = true;
                                            if (this.curAccess.getStatementpost().indexOf("%manual_commit%") >= 0) {
                                                request.setQueryStartTime(request.getInitialQueryStartTime());
                                                this.iwRequest.setReturnString(this.iwRequest.getInitialReturnString());
                                                System.arraycopy(this.iwRequest.getInitialOtherQueryStartTime(), 0, this.iwRequest.getOtherQueryStartTime(), 0, 10);
                                                System.arraycopy(this.iwRequest.getInitialOtherReturnString(), 0, this.iwRequest.getOtherReturnString(), 0, 10);
                                            }
                                            int ec = -3000001;
                                            try {
                                                ec = this.httpURLConnection.getResponseCode();
                                            }
                                            catch (IOException e1) {
                                                request.lnkIWServices.logConsole("No input stream and no response code Exception: " + e + " Nested Exception: " + e1, IWServices.LOG_ERRORS, request);
                                            }
                                            if (ec == -3000001) break block90;
                                            request.lnkIWServices.logConsole("No input stream. Code: " + ec + " Message: " + this.httpURLConnection.getResponseMessage() + " Exception: " + e, IWServices.LOG_ERRORS, request);
                                            if (ec >= 400) {
                                                InputStream eis = this.httpURLConnection.getErrorStream();
                                                if (eis != null) {
                                                    inputSuccess = 1;
                                                    ist = new InputStreamReader(eis);
                                                }
                                            }
                                            result.append("INPUT FAILURE: CODE " + ec + " Message: " + this.httpURLConnection.getResponseMessage());
                                            inputSuccess = 0;
                                        }
                                    }
                                    if (inputSuccess == 1) {
                                        int count;
                                        BufferedReader inb = new BufferedReader(ist);
                                        while ((count = inb.read(temp, 0, bufferSize)) != -1) {
                                            result.append(temp, 0, count);
                                        }
                                        inb.close();
                                        ist.close();
                                    } else if (inputSuccess == -1) {
                                        result.append("INPUT FAILURE");
                                    }
                                }
                                if (this.encoded) {
                                    if (this.encodingCharSet == null || this.encodingCharSet.equalsIgnoreCase("NOENCODE")) {
                                        String ds = URLDecoder.decode(result.toString(), "UTF-8");
                                        result = new StringBuffer(ds);
                                    } else if (!(this.encodingCharSet.equalsIgnoreCase("NODECODE") || this.encodingCharSet.equalsIgnoreCase("NONE") || this.encodingCharSet.equalsIgnoreCase("GETENCODE"))) {
                                        String ds = URLDecoder.decode(result.toString(), this.encodingCharSet);
                                        result = new StringBuffer(ds);
                                    }
                                }
                                if (this.multipleRequests && prsu) {
                                    result = new StringBuffer(this.streapSuffix(this.streapPrefix(result.toString(), prefix), suffix));
                                }
                                if (isXML) {
                                    int bp;
                                    int bsp = 0;
                                    int rsln = result.length();
                                    while ((bp = result.indexOf("`", bsp)) >= 0) {
                                        result.setCharAt(bp, ' ');
                                        bsp = bp + 1;
                                        if (bsp < rsln) continue;
                                    }
                                }
                                if (isXML) {
                                    endResult.append(result);
                                    if (ic < stprep.length - 1) {
                                        String sa = this.httpURLConnection.getRequestProperty("SOAPAction");
                                        this.closeConnection();
                                        this.connect2HttpServer(true, request);
                                        this.setHTTPParameters(request);
                                        if (sa != null && sa.length() > 0) {
                                            this.httpURLConnection.setRequestProperty("SOAPAction", sa);
                                        }
                                        endResult.append("`");
                                    }
                                } else {
                                    endResult.append(this.oneColOneRow("Result", result.toString()));
                                    if (ic < stprep.length - 1) {
                                        this.closeConnection();
                                        this.connect2HttpServer(false, request);
                                        this.setHTTPParameters(request);
                                    }
                                }
                                request.lnkIWServices.logConsole("Http input #" + ic + " recieved", IWServices.LOG_TRACE, request);
                            }
                            ++ic;
                        }
                        request.lnkIWServices.logConsole("HTTP go: Query executed; Result= " + endResult, errorHappened ? IWServices.LOG_ERRORS : IWServices.LOG_REQUEST, request);
                    } else {
                        request.lnkIWServices.logConsole("HTTP go: Empty query after replacing parameters", IWServices.LOG_TRACE, request);
                    }
                } else {
                    request.lnkIWServices.logConsole("HTTP go: Empty query", IWServices.LOG_TRACE, request);
                }
            }
        }
        if (!this.multipleRequests && prsu) {
            endResult = new StringBuffer(this.streapSuffix(this.streapPrefix(endResult.toString(), prefix), suffix));
        }
        if (isXML) {
            if (this.unescape) {
                return new StringBuffer(IWServices.unEscape(endResult.toString()));
            }
            return endResult;
        }
        return this.addDataMapXML(this.addDataXML(endResult));
    }

    private String streapPrefix(String inpStr, String prefix) {
        if (inpStr.startsWith(prefix)) {
            return inpStr.substring(prefix.length());
        }
        return inpStr;
    }

    private String streapSuffix(String inpStr, String suffix) {
        if (inpStr.endsWith(suffix)) {
            return inpStr.substring(0, inpStr.length() - suffix.length());
        }
        return inpStr;
    }

    public void closeConnection() {
        if (this.httpURLConnection != null) {
            this.httpURLConnection.disconnect();
        }
    }

    public final void setMultipleRequests(boolean multipleRequests) {
        this.multipleRequests = multipleRequests;
    }
}

