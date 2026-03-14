/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.email;

import com.google.gdata.client.Query;
import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.IEntry;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.EventWho;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.ServiceException;
import com.interweave.adapter.IWBaseAdaptor;
import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IWEmailBaseAdaptor
extends IWBaseAdaptor {
    protected IWServices services;
    protected int port;
    private boolean debug = false;
    private String command = "READ";
    private String mailFolder = "";
    private int maxEmails = 200;
    int level = 0;
    private Session session = null;
    private boolean textMessagePartOnly = true;
    private boolean stringMessagePartOnly = true;

    public void setup(Datamap map, IWRequest request) throws Exception {
        int ci;
        int cti;
        this.setupInitConnect(map, request);
        if (this.contentType.endsWith("+")) {
            this.textMessagePartOnly = false;
            this.contentType = this.contentType.substring(0, this.contentType.length() - 1);
        }
        if (this.contentType.endsWith("*")) {
            this.textMessagePartOnly = false;
            this.stringMessagePartOnly = false;
            this.contentType = this.contentType.substring(0, this.contentType.length() - 1);
        }
        if ((cti = this.contentType.indexOf(":")) >= 0) {
            String ct = this.contentType;
            this.contentType = ct.substring(0, cti);
            this.mailFolder = this.command = ct.substring(cti + 1);
            int cmi = this.command.indexOf(":");
            if (cmi >= 0) {
                this.command = this.command.substring(0, cmi);
                this.mailFolder = this.mailFolder.substring(cmi + 1);
                int cmn = this.mailFolder.indexOf(":");
                if (cmn >= 0) {
                    ct = this.mailFolder;
                    this.mailFolder = ct.substring(0, cmn);
                    this.maxEmails = Integer.valueOf(ct.substring(cmn + 1));
                }
            }
        }
        if ((ci = this.httpURL.indexOf(":")) >= 0) {
            this.port = Integer.valueOf(this.httpURL.substring(ci + 1));
            this.httpURL = this.httpURL.substring(0, ci);
        } else {
            this.port = -1;
        }
        this.debug = request.lnkIWServices.getLogging() == IWServices.LOG_TRACE;
    }

    public StringBuffer go(IWRequest request) throws Exception {
        StringBuffer ret;
        block48: {
            this.iwRequest = request;
            Enumeration enumerate = this.accessList.elements();
            String stpre = null;
            if (enumerate.hasMoreElements()) {
                Access access = (Access)((Object)enumerate.nextElement());
                this.curAccess = null;
                request.lnkIWServices.logConsole(String.valueOf(this.toString()) + ".go", IWServices.LOG_TRACE, request);
                if (access != null) {
                    this.curAccess = access;
                    stpre = this.curAccess.getStatementpre();
                    stpre = IWServices.replaceParameters(stpre, this.curAccess, request, this.dataMap);
                    request.lnkIWServices.logConsole("IWEmailBaseAdaptor data: " + stpre, IWServices.LOG_TRACE, request);
                }
            }
            ret = new StringBuffer("");
            if (this.contentType.equalsIgnoreCase("SMTP") || this.contentType.equalsIgnoreCase("SMTP_SI")) {
                try {
                    if (stpre.trim().length() > 0) {
                        String[] ec;
                        String[] stringArray = ec = stpre.split("`");
                        int n = 0;
                        int n2 = stringArray.length;
                        while (n < n2) {
                            String cec = stringArray[n];
                            String oldUser = this.user;
                            String oldPassword = this.password;
                            try {
                                if (this.contentType.equalsIgnoreCase("SMTP")) {
                                    this.sendMail(cec);
                                    ret.append(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", "E-mail was sent."))));
                                } else if (this.contentType.equalsIgnoreCase("SMTP_SI")) {
                                    if (this.mailFolder.trim().length() == 0) {
                                        ret.append(this.createSentMail(cec));
                                    } else {
                                        ret.append(this.createSentMail(cec, this.mailFolder));
                                    }
                                }
                            }
                            catch (Exception e) {
                                ret.append(this.addDataXML(new StringBuffer(this.oneColOneRow("Error", "No E-mail was sent." + e))));
                                this.user = oldUser;
                                this.password = oldPassword;
                            }
                            ++n;
                        }
                    }
                    ret = this.addDataMapXML(ret);
                }
                catch (Exception e) {
                    ret.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "E-mail can not be sent: " + e)))));
                    throw e;
                }
            }
            if (this.contentType.equalsIgnoreCase("UPSERT_MEETING")) {
                try {
                    if (stpre.trim().length() > 0) {
                        String[] ec;
                        ArrayList<String> rN = new ArrayList<String>();
                        StringBuffer rQN = new StringBuffer();
                        StringBuffer rQ = new StringBuffer();
                        StringBuffer r2QN = new StringBuffer();
                        StringBuffer r2Q = new StringBuffer();
                        this.retFormat = IWServices.analyzeParameters(this.curAccess, rN, rQN, rQ, r2QN, r2Q);
                        this.returnName = rN.toArray(new String[0]);
                        this.replaceQuoteName = rQN.toString();
                        this.replaceQuote = rQ.toString();
                        this.replace2QuoteName = r2QN.toString();
                        this.replace2Quote = r2Q.toString();
                        String[] stringArray = ec = stpre.split("`");
                        int e = 0;
                        int n = stringArray.length;
                        while (e < n) {
                            String cec = stringArray[e];
                            String oldUser = this.user;
                            String oldPassword = this.password;
                            try {
                                ret.append(this.addEvent(cec));
                            }
                            catch (Exception e2) {
                                ret.append(new StringBuffer(this.oneColOneRow("FAILURE", "This meeting cannot be added/modifiued: " + e2)));
                                this.user = oldUser;
                                this.password = oldPassword;
                            }
                            ++e;
                        }
                    }
                    ret = this.addDataMapXML(this.addDataXML(ret));
                }
                catch (Exception e) {
                    ret.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Meeting cannot be added/modifiued: " + e)))));
                    throw e;
                }
            }
            if (this.contentType.toLowerCase().startsWith("pop3") || this.contentType.toLowerCase().startsWith("imap")) {
                ArrayList<String> rN = new ArrayList<String>();
                StringBuffer rQN = new StringBuffer();
                StringBuffer rQ = new StringBuffer();
                StringBuffer r2QN = new StringBuffer();
                StringBuffer r2Q = new StringBuffer();
                this.retFormat = IWServices.analyzeParameters(this.curAccess, rN, rQN, rQ, r2QN, r2Q);
                this.returnName = rN.toArray(new String[0]);
                this.replaceQuoteName = rQN.toString();
                this.replaceQuote = rQ.toString();
                this.replace2QuoteName = r2QN.toString();
                this.replace2Quote = r2Q.toString();
                if (stpre != null && stpre.trim().length() > 0) {
                    String[] ec;
                    String[] stringArray = ec = stpre.split("`");
                    int e = 0;
                    int n = stringArray.length;
                    while (e < n) {
                        String cec = stringArray[e];
                        String oldUser = this.user;
                        String oldPassword = this.password;
                        try {
                            if (this.mailFolder.trim().length() == 0) {
                                ret.append(this.receiveMail(cec));
                            } else {
                                ret.append(this.receiveMail(cec, this.mailFolder));
                            }
                        }
                        catch (Exception e3) {
                            ret.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Email for this user cannot be received: " + e3)))));
                            this.user = oldUser;
                            this.password = oldPassword;
                        }
                        ++e;
                    }
                } else {
                    try {
                        if (this.mailFolder.trim().length() == 0) {
                            ret.append(this.receiveMail(""));
                            break block48;
                        }
                        ret.append(this.receiveMail("", this.mailFolder));
                    }
                    catch (Exception e) {
                        ret.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Email for this user cannot be received: " + e)))));
                    }
                }
            } else {
                if (this.contentType.equalsIgnoreCase("GCONTACTS")) {
                    try {
                        if (stpre != null && stpre.trim().length() > 0) {
                            String[] ec;
                            String[] r2Q = ec = stpre.split("`");
                            int rQ = 0;
                            int r2QN = r2Q.length;
                            while (rQ < r2QN) {
                                String cec = r2Q[rQ];
                                String oldUser = this.user;
                                String oldPassword = this.password;
                                try {
                                    ret.append(this.getAllContacts(cec));
                                }
                                catch (Exception e) {
                                    ret.append(new StringBuffer(this.oneColOneRow("FAILURE", "This Contact cannot be obtained:" + e)));
                                    this.user = oldUser;
                                    this.password = oldPassword;
                                }
                                ++rQ;
                            }
                        } else {
                            ret.append(this.getAllContacts());
                        }
                        ret = this.addDataMapXML(this.addDataXML(ret));
                    }
                    catch (Exception e) {
                        ret.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "All Contacts cannot be obtained: " + e)))));
                        throw e;
                    }
                }
                if (this.contentType.equalsIgnoreCase("GCALENDAR")) {
                    try {
                        if (stpre.trim().length() > 0) {
                            String[] ec;
                            ArrayList<String> rN = new ArrayList<String>();
                            StringBuffer rQN = new StringBuffer();
                            StringBuffer rQ = new StringBuffer();
                            StringBuffer r2QN = new StringBuffer();
                            StringBuffer r2Q = new StringBuffer();
                            this.retFormat = IWServices.analyzeParameters(this.curAccess, rN, rQN, rQ, r2QN, r2Q);
                            this.returnName = rN.toArray(new String[0]);
                            this.replaceQuoteName = rQN.toString();
                            this.replaceQuote = rQ.toString();
                            this.replace2QuoteName = r2QN.toString();
                            this.replace2Quote = r2Q.toString();
                            String[] stringArray = ec = stpre.split("`");
                            int n = 0;
                            int n3 = stringArray.length;
                            while (n < n3) {
                                String cec = stringArray[n];
                                String oldUser = this.user;
                                String oldPassword = this.password;
                                try {
                                    ret.append(this.getAllEvents(cec));
                                }
                                catch (Exception e) {
                                    ret.append(new StringBuffer(this.oneColOneRow("FAILURE", "This User Calendar Events cannot be obtained: " + e)));
                                    this.user = oldUser;
                                    this.password = oldPassword;
                                }
                                ++n;
                            }
                        }
                        ret = this.addDataMapXML(this.addDataXML(ret));
                    }
                    catch (Exception e) {
                        ret.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Calendar Events cannot be obtained: " + e)))));
                        throw e;
                    }
                }
                ret.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Protocol " + this.contentType + " is not supported.")))));
            }
        }
        return ret;
    }

    public void closeConnection() {
    }

    public void sendNotificationEMail(IWRequest request, String url, int port, String context) throws Exception {
        this.sendNotificationEMail(null, request, url, port, String.valueOf(IWServices.getEmailNoteName()) + IWServices.getEmailSuffix(request) + "@interweave.biz", context);
    }

    public void sendNotificationEMail(IWServices services, IWRequest request, String url, int port, String user, String context) throws Exception {
        this.httpURL = url;
        this.port = port;
        this.iwRequest = request;
        this.user = user;
        this.services = services;
        String string = this.password = request == null ? services.getEmailNotePwd(user) : request.lnkIWServices.getEmailNotePwd(user);
        if (request != null) {
            request.lnkIWServices.logConsole("IWEmailBaseAdaptor: Preparing Notification e-mail: " + context, IWServices.LOG_TRACE, request);
        } else if (services != null) {
            services.logConsole("IWEmailBaseAdaptor: Preparing Notification e-mail: " + context, IWServices.LOG_TRACE, null);
        }
        this.sendMail(context);
    }

    private void sendMail(String context) throws Exception {
        if (this.iwRequest != null) {
            this.iwRequest.lnkIWServices.logConsole("Send Email started...", IWServices.LOG_MINIMUM, this.iwRequest);
        }
        int attempts = 3;
        int i = 0;
        while (i < attempts) {
            try {
                Transport.send((Message)this.createMailMessage(context));
                if (this.iwRequest != null) {
                    this.iwRequest.lnkIWServices.logConsole("Message sent...", IWServices.LOG_MINIMUM, this.iwRequest);
                } else if (this.services != null) {
                    this.services.logConsole("Message sent...", IWServices.LOG_MINIMUM, null);
                }
                return;
            }
            catch (MessagingException mex) {
                String emes = mex.getMessage();
                if (!(emes != null && emes.trim().length() != 0 || (emes = mex.toString()) != null && emes.trim().length() != 0)) {
                    emes = "Unknown cause.";
                }
                if (this.iwRequest != null) {
                    this.iwRequest.lnkIWServices.logError("Error sending e-mail " + emes, IWServices.LOG_MINIMUM, (Exception)((Object)mex), this.iwRequest);
                } else if (this.services != null) {
                    this.services.logError("Error sending e-mail " + emes, IWServices.LOG_MINIMUM, (Exception)((Object)mex), null);
                }
                Exception ex = null;
                ex = mex.getNextException();
                if (ex != null) {
                    ex.printStackTrace();
                }
                if (i == attempts - 1) {
                    throw mex;
                }
                Thread.sleep(1000L);
                ++i;
            }
        }
    }

    private MimeMessage createMailMessage(String context) throws Exception {
        return this.createMailMessage(context, true);
    }

    private MimeMessage createMailMessage(String context, boolean keepGlobalPassword) throws Exception {
        int ppos;
        String cas;
        String from;
        String bcc;
        String oldUser = this.user;
        String oldPassword = this.password;
        Properties cp = this.parseMail(context);
        if (this.iwRequest != null) {
            this.iwRequest.lnkIWServices.logConsole("IWEmailBaseAdaptor: Context Parsed. " + cp.toString(), IWServices.LOG_TRACE, this.iwRequest);
        } else if (this.services != null) {
            this.services.logConsole("IWEmailBaseAdaptor: Context Parsed. " + cp.toString(), IWServices.LOG_TRACE, null);
        }
        if (this.httpURL == null || this.httpURL.trim().length() == 0) {
            throw new Exception("Sender is missing");
        }
        String to = cp.getProperty("TO");
        if (to == null || to.trim().length() == 0) {
            throw new Exception("Recipient is missing");
        }
        String cc = cp.getProperty("CC");
        if (cc == null || cc.trim().length() == 0) {
            cc = null;
        }
        if ((bcc = cp.getProperty("BCC")) == null || bcc.trim().length() == 0) {
            bcc = null;
        }
        if ((from = cp.getProperty("FROM")) == null || from.trim().length() == 0) {
            throw new Exception("Sender is missing");
        }
        String subject = cp.getProperty("SUBJECT");
        if (subject == null || subject.trim().length() == 0) {
            throw new Exception("Subject is missing");
        }
        String priority = cp.getProperty("PRIORITY");
        String importance = cp.getProperty("IMPORTANCE");
        String message = cp.getProperty("MESSAGE");
        if (message == null) {
            message = "";
        }
        ArrayList<String> attachments = new ArrayList<String>();
        int k = 0;
        while ((cas = cp.getProperty("ATTACHMENT" + k++)) != null) {
            attachments.add(cas);
        }
        if (this.user.trim().length() == 0) {
            this.user = from;
            this.password = cp.getProperty("PASSWORD");
            if (this.password == null) {
                if (keepGlobalPassword) {
                    this.user = oldUser;
                    this.password = oldPassword;
                }
                throw new Exception("Local Password is missing");
            }
            if (this.iwRequest != null) {
                this.iwRequest.lnkIWServices.logConsole("IWEmailBaseAdaptor: CreateMailMessage: local credentials (" + this.user + ", " + this.password + ")", IWServices.LOG_TRACE, this.iwRequest);
            }
        }
        if (this.iwRequest != null) {
            this.iwRequest.lnkIWServices.logConsole("Creating mail for User " + this.user + " from " + from + " to " + to + " cc " + cc, IWServices.LOG_MINIMUM, this.iwRequest);
        }
        Properties props = new Properties();
        String host = this.httpURL;
        if (!this.httpURL.startsWith("smtp") && !IWServices.usestunnel && (ppos = this.httpURL.indexOf(".")) >= 0) {
            host = "smtp" + this.httpURL.substring(ppos);
        }
        props.put("mail.smtp.host", host);
        if (this.port >= 0) {
            props.put("mail.smtp.port", String.valueOf(this.port));
        }
        props.put("mail.smtp.auth", String.valueOf(this.getUser().length() > 0));
        switch (IWServices.startTLS) {
            case 1: {
                props.put("mail.smtp.starttls.enable", "true");
                break;
            }
            case 3: {
                props.put("mail.smtp.starttls.enable", "true");
            }
            case 2: {
                props.put("mail.smtp.starttls.required", "true");
            }
        }
        IWAuthenticator ma = new IWAuthenticator();
        this.session = Session.getInstance((Properties)props, (Authenticator)ma);
        this.session.setDebug(this.debug);
        try {
            MimeMessage msg = new MimeMessage(this.session);
            if (this.iwRequest != null) {
                this.iwRequest.lnkIWServices.logConsole("IWEmailBaseAdaptor: message created", IWServices.LOG_TRACE, this.iwRequest);
            } else if (this.services != null) {
                this.services.logConsole("IWEmailBaseAdaptor: message created", IWServices.LOG_TRACE, null);
            }
            msg.setFrom((Address)new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, (Address[])this.getInetAddresses(to));
            if (cc != null) {
                msg.setRecipients(Message.RecipientType.CC, (Address[])this.getInetAddresses(cc));
            }
            if (bcc != null) {
                msg.setRecipients(Message.RecipientType.BCC, (Address[])this.getInetAddresses(bcc));
            }
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            if (priority != null && priority.trim().length() > 0) {
                msg.setHeader("X-Priority", priority);
            }
            if (importance != null && importance.trim().length() > 0) {
                msg.setHeader("Importance", importance);
            }
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(cp.getProperty("MESSAGE"));
            MimeMultipart mp = new MimeMultipart();
            mp.addBodyPart((BodyPart)mbp1);
            int j = 0;
            while (j < attachments.size()) {
                MimeBodyPart mbpj = new MimeBodyPart();
                String atts = (String)attachments.get(j);
                if (atts.startsWith("%__FILE__%")) {
                    File af = new File(atts.substring(10));
                    if (af.exists()) {
                        mbpj.attachFile(af);
                    } else {
                        mbpj.setText("WARNING! File Attacment " + atts.substring(10) + " not found.", "us-ascii");
                    }
                } else {
                    mbpj.setText(atts, "us-ascii");
                }
                mp.addBodyPart((BodyPart)mbpj);
                if (this.iwRequest != null) {
                    this.iwRequest.lnkIWServices.logConsole("IWEmailBaseAdaptor part " + j + " to send: " + mbpj.getContent().toString(), IWServices.LOG_TRACE, this.iwRequest);
                } else if (this.services != null) {
                    this.services.logConsole("IWEmailBaseAdaptor part " + j + " to send: " + mbpj.getContent().toString(), IWServices.LOG_TRACE, null);
                }
                ++j;
            }
            msg.setContent((Multipart)mp);
            if (keepGlobalPassword) {
                this.user = oldUser;
                this.password = oldPassword;
            }
            return msg;
        }
        catch (MessagingException mex) {
            if (this.iwRequest != null) {
                this.iwRequest.lnkIWServices.logError("Error creating e-mail " + mex.getMessage(), IWServices.LOG_TRACE, (Exception)((Object)mex), this.iwRequest);
            } else if (this.services != null) {
                this.services.logError("Error creating e-mail " + mex.getMessage(), IWServices.LOG_TRACE, (Exception)((Object)mex), null);
            }
            Exception ex = null;
            ex = mex.getNextException();
            if (ex != null) {
                ex.printStackTrace();
            }
            if (keepGlobalPassword) {
                this.user = oldUser;
                this.password = oldPassword;
            }
            throw mex;
        }
    }

    InternetAddress[] getInetAddresses(String into) throws AddressException {
        String[] tst = into.split(";");
        InternetAddress[] address = new InternetAddress[tst.length];
        int i = 0;
        while (i < tst.length) {
            address[i] = new InternetAddress(tst[i]);
            ++i;
        }
        return address;
    }

    private StringBuffer receiveMail(String inputString) throws Exception {
        return this.receiveMail(inputString, "INBOX");
    }

    private StringBuffer receiveMail(String inputString, String folderName) throws Exception {
        this.iwRequest.lnkIWServices.logConsole("Getting Emails from foler " + folderName + " started...", IWServices.LOG_MINIMUM, this.iwRequest);
        this.iwRequest.lnkIWServices.logConsole("Input: " + inputString, IWServices.LOG_TRACE, this.iwRequest);
        String oldUser = this.user;
        String oldPassword = this.password;
        String protocol = this.contentType.toLowerCase();
        StringBuffer ret = new StringBuffer();
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", protocol);
        Session session = Session.getInstance((Properties)props, null);
        Store store = session.getStore(protocol);
        Date startTime = null;
        Date endTime = null;
        int insc = inputString.indexOf(";");
        if (insc >= 0) {
            String[] ec = inputString.split(";");
            if (ec[0].trim().length() > 0) {
                startTime = new Date(Timestamp.valueOf(ec[0]).getTime());
            }
            if (ec[1].trim().length() > 0) {
                endTime = new Date(Timestamp.valueOf(ec[1]).getTime());
            }
            if (this.user.trim().length() == 0 && ec.length > 2) {
                this.user = ec[2].trim();
                if (ec.length > 3) {
                    this.password = ec[3];
                }
            }
        }
        this.iwRequest.lnkIWServices.logConsole("Obtaining E-mails for User " + this.user, IWServices.LOG_MINIMUM, this.iwRequest);
        try {
            store.connect(this.httpURL, this.user, this.password);
        }
        catch (MessagingException e) {
            ret.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "User" + this.user + "cannot connect")))));
            this.user = oldUser;
            this.password = oldPassword;
            return ret;
        }
        Folder folder = store.getFolder(folderName);
        folder.open(1);
        Message[] messages = null;
        int maxMes = folder.getMessageCount();
        this.iwRequest.lnkIWServices.logConsole("User: " + this.user + " Password: " + this.password + "Toal messages: " + maxMes, IWServices.LOG_TRACE, this.iwRequest);
        if (maxMes > this.maxEmails) {
            Date rd1 = folder.getMessage(1).getReceivedDate();
            Date rd2 = folder.getMessage(2).getReceivedDate();
            messages = rd2.after(rd1) ? folder.getMessages(maxMes - this.maxEmails + 1, maxMes) : folder.getMessages(1, this.maxEmails);
        } else {
            messages = folder.getMessages();
        }
        if (messages == null) {
            ret.append(this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "User" + this.user + "has no messages")))));
            this.user = oldUser;
            this.password = oldPassword;
            return ret;
        }
        StringBuffer msg = new StringBuffer();
        Message[] messageArray = messages;
        int n = 0;
        int n2 = messageArray.length;
        while (n < n2) {
            Message cm = messageArray[n];
            this.iwRequest.lnkIWServices.logConsole("Any message: " + cm.toString(), IWServices.LOG_TRACE, this.iwRequest);
            if (!(cm.isSet(Flags.Flag.DELETED) && !this.command.equals("READALL") || this.command.equals("READNEW") && !cm.isSet(Flags.Flag.RECENT))) {
                this.iwRequest.lnkIWServices.logConsole("Applied message: " + cm.toString(), IWServices.LOG_TRACE, this.iwRequest);
                Date rd = cm.getReceivedDate();
                if (!(startTime != null && (rd.before(startTime) || rd.equals(startTime)) || endTime != null && rd.after(endTime))) {
                    Date sd;
                    Address ca;
                    int n3;
                    int n4;
                    Address[] addressArray;
                    this.iwRequest.lnkIWServices.logConsole(cm.getFrom()[0] + "\t" + cm.getSubject() + "\t" + cm.getFlags(), IWServices.LOG_TRACE, this.iwRequest);
                    msg.setLength(0);
                    int mi = 0;
                    if (folderName.equals("INBOX")) {
                        msg.append(this.oneCol("To", this.user, mi++));
                    } else if (cm.getRecipients(Message.RecipientType.TO) != null) {
                        addressArray = cm.getRecipients(Message.RecipientType.TO);
                        n4 = 0;
                        n3 = addressArray.length;
                        while (n4 < n3) {
                            ca = addressArray[n4];
                            msg.append(this.oneCol("To", ca.toString(), mi++));
                            ++n4;
                        }
                    }
                    if (cm.getFrom() != null) {
                        addressArray = cm.getFrom();
                        n4 = 0;
                        n3 = addressArray.length;
                        while (n4 < n3) {
                            ca = addressArray[n4];
                            msg.append(this.oneCol("From", ca.toString(), mi++));
                            ++n4;
                        }
                    }
                    if (cm.getRecipients(Message.RecipientType.CC) != null) {
                        addressArray = cm.getRecipients(Message.RecipientType.CC);
                        n4 = 0;
                        n3 = addressArray.length;
                        while (n4 < n3) {
                            ca = addressArray[n4];
                            msg.append(this.oneCol("Cc", ca.toString(), mi++));
                            ++n4;
                        }
                    }
                    if (cm.getRecipients(Message.RecipientType.BCC) != null) {
                        addressArray = cm.getRecipients(Message.RecipientType.BCC);
                        n4 = 0;
                        n3 = addressArray.length;
                        while (n4 < n3) {
                            ca = addressArray[n4];
                            msg.append(this.oneCol("Bcc", ca.toString(), mi++));
                            ++n4;
                        }
                    }
                    if (cm.getReplyTo() != null) {
                        addressArray = cm.getReplyTo();
                        n4 = 0;
                        n3 = addressArray.length;
                        while (n4 < n3) {
                            ca = addressArray[n4];
                            msg.append(this.oneCol("ReplyTo", ca.toString(), mi++));
                            ++n4;
                        }
                    }
                    if (cm.getSubject() != null) {
                        msg.append(this.oneCol("Subject", cm.getSubject(), mi++));
                    }
                    if ((sd = cm.getSentDate()) != null) {
                        msg.append(this.oneCol("Sent", new Timestamp(sd.getTime()).toString(), mi++));
                        msg.append(this.oneCol("SentHash", String.valueOf(sd.hashCode()), mi++));
                    }
                    if (cm.getReceivedDate() != null) {
                        msg.append(this.oneCol("Received", new Timestamp(rd.getTime()).toString().toString(), mi++));
                        msg.append(this.oneCol("ReceivedHash", String.valueOf(rd.hashCode()), mi++));
                    }
                    this.iwRequest.lnkIWServices.logConsole("IWEmailBaseAdaptor: ReceiveMail Flags set for " + cm.getSubject() + " " + (cm.isSet(Flags.Flag.RECENT) ? "RECENT" : "OLD") + " " + (cm.isSet(Flags.Flag.SEEN) ? "SEEN" : "NOT SEEN"), IWServices.LOG_TRACE, this.iwRequest);
                    String fl = "";
                    if (cm.isSet(Flags.Flag.RECENT)) {
                        fl = "New";
                    } else if (cm.isSet(Flags.Flag.SEEN)) {
                        fl = "Seen";
                    } else if (!cm.isSet(Flags.Flag.SEEN)) {
                        fl = "NotSeen";
                    }
                    msg.append(this.oneCol("Flag", fl, mi++));
                    msg = this.addRowXML(msg);
                    this.level = 0;
                    StringBuffer mc = this.partContext((Part)((MimePart)cm));
                    ret.append(this.addDataXML(msg.append(mc)));
                    if (this.command.equals("MOVE")) {
                        cm.setFlag(Flags.Flag.DELETED, true);
                    }
                }
            }
            ++n;
        }
        if (this.command.equals("MOVE")) {
            folder.expunge();
        }
        folder.close(true);
        store.close();
        this.user = oldUser;
        this.password = oldPassword;
        return this.addDataMapXML(ret);
    }

    private StringBuffer createSentMail(String context) throws Exception {
        return this.createSentMail(context, "Sent Items");
    }

    private StringBuffer createSentMail(String context, String folderName) throws Exception {
        this.iwRequest.lnkIWServices.logConsole("Create Sent Email started...", IWServices.LOG_MINIMUM, this.iwRequest);
        String oldUser = this.user;
        String oldPassword = this.password;
        MimeMessage msg = this.createMailMessage(context, false);
        StringBuffer ret = new StringBuffer();
        String prot = this.httpURL;
        int ppos = this.httpURL.indexOf(".");
        if (ppos >= 0) {
            prot = String.valueOf(this.httpURL.substring(0, ppos)) + (this.httpURL.indexOf("gmail") >= 0 ? "s" : "");
        }
        if (this.session == null) {
            ret.append(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Session does not exist"))));
            this.user = oldUser;
            this.password = oldPassword;
            return ret;
        }
        Store store = this.session.getStore(prot);
        store.connect(this.httpURL, this.user, this.password);
        Folder folder = store.getFolder(folderName);
        if (!folder.exists()) {
            ret.append(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Folder " + folderName + " does not exist"))));
            this.user = oldUser;
            this.password = oldPassword;
            return ret;
        }
        this.iwRequest.lnkIWServices.logConsole("Sent Items Folder: " + folder.getName() + " " + folder.getFullName() + " " + folder.getType(), IWServices.LOG_TRACE, this.iwRequest);
        folder.appendMessages(new Message[]{msg});
        store.close();
        this.user = oldUser;
        this.password = oldPassword;
        ret.append(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", "E-mail was stored in " + folderName + " folder."))));
        return ret;
    }

    private StringBuffer partContext(Part p) throws Exception {
        StringBuffer ret = new StringBuffer();
        int mi = 0;
        String ct = p.getContentType();
        if (p.isMimeType("text/plain")) {
            String[] lc;
            String[] stringArray = lc = IWServices.readLineContext(p.getContent().toString());
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cs = stringArray[n];
                ret.append(this.oneCol("MessageLine", cs, mi++));
                ++n;
            }
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            ++this.level;
            int count = mp.getCount();
            int i = 0;
            while (i < count) {
                ret.append(this.partContext((Part)mp.getBodyPart(i)));
                ++i;
            }
            --this.level;
        } else if (p.isMimeType("message/rfc822")) {
            ++this.level;
            ret.append(this.partContext((Part)p.getContent()));
            --this.level;
        } else if (!this.textMessagePartOnly) {
            Object o = p.getContent();
            if (o instanceof String) {
                ret.append(this.oneCol("MessageLine", o.toString(), mi++));
            } else if (!this.stringMessagePartOnly) {
                if (o instanceof InputStream) {
                    int c;
                    StringBuffer ib = new StringBuffer();
                    InputStream is = (InputStream)o;
                    while ((c = is.read()) != -1) {
                        ib.append(c);
                    }
                    ret.append(this.oneCol("MessageLine", ib.toString(), mi++));
                } else {
                    this.iwRequest.lnkIWServices.logConsole("IWEmailBaseAdaptor: This is an unknown type: " + o.getClass().getName(), IWServices.LOG_ERRORS, this.iwRequest);
                }
            }
        }
        return this.level == 0 ? this.addRowXML(ret) : ret;
    }

    /*
     * Unable to fully structure code
     */
    private Properties parseMail(String mailContext) throws Exception {
        ret = new Properties();
        ret.setProperty("HOST", this.httpURL);
        parser = new DOMParser();
        temp = mailContext.getBytes();
        bis = new ByteArrayInputStream(temp);
        try {
            parser.parse(new InputSource(bis));
        }
        catch (SAXException e) {
            ict = 0;
            ** while (ict < temp.length)
        }
lbl-1000:
        // 1 sources

        {
            if (!(temp[ict] == 9 || temp[ict] == 10 || temp[ict] == 13 || temp[ict] >= 32 && temp[ict] <= 55295 || temp[ict] >= 57344 && temp[ict] <= 65533 || temp[ict] >= 65536 && temp[ict] <= 0x10FFFF)) {
                temp[ict] = 32;
            }
            ++ict;
            continue;
        }
lbl17:
        // 1 sources

        this.iwRequest.lnkIWServices.logConsole("XML go: Reduced string to parse: " + new String(temp), IWServices.LOG_TRACE, this.iwRequest);
        parser = new DOMParser();
        bis = new ByteArrayInputStream(temp);
        try {
            parser.parse(new InputSource(bis));
        }
        catch (SAXException e1) {
            throw e1;
        }
        catch (IOException e1) {
            throw e1;
        }
        catch (IOException e) {
            throw e;
        }
        conDoc = parser.getDocument();
        bdConf = conDoc.getDocumentElement();
        if (!bdConf.getTagName().equals("IWEMail")) {
            throw new Exception("Wrong root element!");
        }
        tob = new StringBuffer();
        tos = bdConf.getElementsByTagName("To");
        i = 0;
        while (i < tos.getLength()) {
            tdn = (Element)tos.item(i);
            tob.append(tdn.getTextContent());
            if (i < tos.getLength() - 1) {
                tob.append(";");
            }
            ++i;
        }
        ret.setProperty("TO", tob.toString());
        ccb = new StringBuffer();
        ccs = bdConf.getElementsByTagName("Cc");
        i = 0;
        while (i < ccs.getLength()) {
            cdn = (Element)ccs.item(i);
            ccb.append(cdn.getTextContent());
            if (i < ccs.getLength() - 1) {
                ccb.append(";");
            }
            ++i;
        }
        ret.setProperty("CC", ccb.toString());
        bccb = new StringBuffer();
        bccs = bdConf.getElementsByTagName("Bcc");
        i = 0;
        while (i < bccs.getLength()) {
            bcdn = (Element)bccs.item(i);
            bccb.append(bcdn.getTextContent());
            if (i < bccs.getLength() - 1) {
                bccb.append(";");
            }
            ++i;
        }
        ret.setProperty("BCC", bccb.toString());
        fros = bdConf.getElementsByTagName("From");
        if (fros.getLength() != 1) {
            throw new Exception("From element is missing or too many From elements!");
        }
        fdn = (Element)fros.item(0);
        ret.setProperty("FROM", fdn.getTextContent());
        frop = bdConf.getElementsByTagName("Password");
        if (frop.getLength() > 1) {
            throw new Exception("Too many Password elements!");
        }
        if (frop.getLength() == 1) {
            pdn = (Element)frop.item(0);
            ret.setProperty("PASSWORD", pdn.getTextContent());
        }
        if ((subs = bdConf.getElementsByTagName("Subject")).getLength() != 1) {
            throw new Exception("Subject element is missing or too many Subject elements!");
        }
        sbn = (Element)subs.item(0);
        ret.setProperty("SUBJECT", sbn.getTextContent());
        prts = bdConf.getElementsByTagName("Priority");
        if (prts.getLength() > 1) {
            throw new Exception("Too many Priority elements!");
        }
        if (prts.getLength() == 1) {
            prtn = (Element)prts.item(0);
            ret.setProperty("PRIORITY", prtn.getTextContent());
        }
        if ((imps = bdConf.getElementsByTagName("Importance")).getLength() > 1) {
            throw new Exception("Too many Importance elements!");
        }
        if (imps.getLength() == 1) {
            impn = (Element)imps.item(0);
            ret.setProperty("IMPORTANCE", impn.getTextContent());
        }
        if ((mss = bdConf.getElementsByTagName("Message")).getLength() != 1) {
            throw new Exception("Message element is missing or too many Message elements!");
        }
        msn = (Element)mss.item(0);
        ret.setProperty("MESSAGE", msn.getTextContent());
        ats = bdConf.getElementsByTagName("Attachment");
        ai = 0;
        while (ai < ats.getLength()) {
            atn = (Element)ats.item(ai);
            ret.setProperty("ATTACHMENT" + ai, atn.getTextContent());
            if (this.iwRequest != null) {
                this.iwRequest.lnkIWServices.logConsole("IWEmailBaseAdaptor text attachment " + ai + " to send: " + atn.getTextContent(), IWServices.LOG_TRACE, this.iwRequest);
            } else if (this.services != null) {
                this.services.logConsole("IWEmailBaseAdaptor text attachment " + ai + " to send: " + atn.getTextContent(), IWServices.LOG_TRACE, null);
            }
            ++ai;
        }
        atf = bdConf.getElementsByTagName("FileAttachment");
        i = 0;
        while (i < atf.getLength()) {
            atn = (Element)atf.item(i);
            ain = ai + i;
            ret.setProperty("ATTACHMENT" + ain, "%__FILE__%" + atn.getTextContent());
            if (this.iwRequest != null) {
                this.iwRequest.lnkIWServices.logConsole("IWEmailBaseAdaptor file attachment " + ain + " to send: " + "%__FILE__%" + atn.getTextContent(), IWServices.LOG_TRACE, this.iwRequest);
            } else if (this.services != null) {
                this.services.logConsole("IWEmailBaseAdaptor file attachment " + ain + " to send: " + "%__FILE__%" + atn.getTextContent(), IWServices.LOG_TRACE, null);
            }
            ++i;
        }
        return ret;
    }

    private StringBuffer getAllContacts() throws ServiceException, IOException {
        return this.getAllContacts("");
    }

    private StringBuffer getAllContacts(String inputString) throws ServiceException, IOException {
        String oldUser = this.user;
        String oldPassword = this.password;
        this.iwRequest.lnkIWServices.logConsole("GetAllContacts started...", IWServices.LOG_MINIMUM, this.iwRequest);
        StringBuffer ret = new StringBuffer();
        ContactsService myService = new ContactsService("Interweave-BasaEmailAdaptor-1");
        this.iwRequest.lnkIWServices.logConsole("Service is created", IWServices.LOG_TRACE, this.iwRequest);
        int insc = inputString.indexOf(";");
        if (insc >= 0) {
            String[] ec = inputString.split(";");
            if (this.user.trim().length() == 0 && ec.length > 0) {
                this.user = ec[0].trim();
                if (ec.length > 1) {
                    this.password = ec[1];
                }
            }
        }
        try {
            myService.setUserCredentials(this.user, this.password);
        }
        catch (Exception e) {
            ret.append(new StringBuffer(this.oneColOneRow("FAILURE", "User" + this.user + "cannot connect")));
            this.user = oldUser;
            this.password = oldPassword;
            return ret;
        }
        this.iwRequest.lnkIWServices.logConsole("Credentials are set:" + this.user + " " + this.password, IWServices.LOG_TRACE, this.iwRequest);
        URL feedUrl = new URL("http://www.google.com/m8/feeds/contacts/default/full");
        this.iwRequest.lnkIWServices.logConsole("URL is set", IWServices.LOG_TRACE, this.iwRequest);
        ContactFeed resultFeed = (ContactFeed)myService.getFeed(feedUrl, ContactFeed.class);
        this.iwRequest.lnkIWServices.logConsole(resultFeed.getTitle().getPlainText(), IWServices.LOG_TRACE, this.iwRequest);
        int i = 0;
        while (i < resultFeed.getEntries().size()) {
            StringBuffer emi;
            StringBuffer ent = new StringBuffer("");
            StringBuffer em = new StringBuffer("");
            ContactEntry entry = (ContactEntry)resultFeed.getEntries().get(i);
            if (entry.hasName()) {
                Name name = entry.getName();
                if (name.hasFullName()) {
                    String fullNameToDisplay = name.getFullName().getValue();
                    if (name.getFullName().hasYomi()) {
                        fullNameToDisplay = String.valueOf(fullNameToDisplay) + " (" + name.getFullName().getYomi() + ")";
                    }
                    em.append(this.oneCol("FullName", fullNameToDisplay, 0));
                    this.iwRequest.lnkIWServices.logConsole("\t\t" + fullNameToDisplay, IWServices.LOG_TRACE, this.iwRequest);
                } else {
                    this.iwRequest.lnkIWServices.logConsole("\t\t (no full name found)", IWServices.LOG_TRACE, this.iwRequest);
                }
                if (name.hasNamePrefix()) {
                    em.append(this.oneCol("Prexix", name.getNamePrefix().getValue(), 1));
                    this.iwRequest.lnkIWServices.logConsole("\t\t" + name.getNamePrefix().getValue(), IWServices.LOG_TRACE, this.iwRequest);
                } else {
                    this.iwRequest.lnkIWServices.logConsole("\t\t (no name prefix found)", IWServices.LOG_TRACE, this.iwRequest);
                }
                if (name.hasGivenName()) {
                    String givenNameToDisplay = name.getGivenName().getValue();
                    if (name.getGivenName().hasYomi()) {
                        givenNameToDisplay = String.valueOf(givenNameToDisplay) + " (" + name.getGivenName().getYomi() + ")";
                    }
                    em.append(this.oneCol("GivenName", givenNameToDisplay, 2));
                    this.iwRequest.lnkIWServices.logConsole("\t\t" + givenNameToDisplay, IWServices.LOG_TRACE, this.iwRequest);
                } else {
                    this.iwRequest.lnkIWServices.logConsole("\t\t (no given name found)", IWServices.LOG_TRACE, this.iwRequest);
                }
                if (name.hasAdditionalName()) {
                    String additionalNameToDisplay = name.getAdditionalName().getValue();
                    if (name.getAdditionalName().hasYomi()) {
                        additionalNameToDisplay = String.valueOf(additionalNameToDisplay) + " (" + name.getAdditionalName().getYomi() + ")";
                    }
                    em.append(this.oneCol("AdditionalName", additionalNameToDisplay, 3));
                    this.iwRequest.lnkIWServices.logConsole("\t\t" + additionalNameToDisplay, IWServices.LOG_TRACE, this.iwRequest);
                } else {
                    this.iwRequest.lnkIWServices.logConsole("\t\t (no additional name found)", IWServices.LOG_TRACE, this.iwRequest);
                }
                if (name.hasFamilyName()) {
                    Iterator familyNameToDisplay = name.getFamilyName().getValue();
                    if (name.getFamilyName().hasYomi()) {
                        familyNameToDisplay = String.valueOf(familyNameToDisplay) + " (" + name.getFamilyName().getYomi() + ")";
                    }
                    em.append(this.oneCol("FamilyName", (String)((Object)familyNameToDisplay), 4));
                    this.iwRequest.lnkIWServices.logConsole("\t\t" + (String)((Object)familyNameToDisplay), IWServices.LOG_TRACE, this.iwRequest);
                } else {
                    this.iwRequest.lnkIWServices.logConsole("\t\t (no family name found)", IWServices.LOG_TRACE, this.iwRequest);
                }
                if (name.hasNameSuffix()) {
                    em.append(this.oneCol("Suffix", name.getNameSuffix().getValue(), 4));
                    this.iwRequest.lnkIWServices.logConsole("\t\t" + name.getNameSuffix().getValue(), IWServices.LOG_TRACE, this.iwRequest);
                } else {
                    this.iwRequest.lnkIWServices.logConsole("\t\t (no name suffix found)", IWServices.LOG_TRACE, this.iwRequest);
                }
                em = this.addRowXML(em);
                this.addRowSetXML(em, 0, "ContactNames");
                ent.append(em);
            } else {
                this.iwRequest.lnkIWServices.logConsole("\t (no name found)", IWServices.LOG_TRACE, this.iwRequest);
            }
            this.iwRequest.lnkIWServices.logConsole("Email addresses:", IWServices.LOG_TRACE, this.iwRequest);
            em = new StringBuffer("");
            for (Email email : entry.getEmailAddresses()) {
                emi = new StringBuffer("");
                emi.append(this.oneCol("EmailAddress", email.getAddress(), 0));
                this.iwRequest.lnkIWServices.logConsole(" " + email.getAddress(), IWServices.LOG_TRACE, this.iwRequest);
                if (email.getRel() != null) {
                    emi.append(this.oneCol("Rel", email.getRel(), 1));
                    this.iwRequest.lnkIWServices.logConsole(" rel:" + email.getRel(), IWServices.LOG_TRACE, this.iwRequest);
                }
                if (email.getLabel() != null) {
                    emi.append(this.oneCol("Label", email.getLabel(), 2));
                    this.iwRequest.lnkIWServices.logConsole(" label:" + email.getLabel(), IWServices.LOG_TRACE, this.iwRequest);
                }
                if (email.getPrimary()) {
                    this.iwRequest.lnkIWServices.logConsole(" (primary) ", IWServices.LOG_TRACE, this.iwRequest);
                }
                emi.append(this.oneCol("Primary", email.getPrimary() ? "1" : "0", 3));
                em.append(this.addRowXML(emi));
                this.iwRequest.lnkIWServices.logConsole("\n", IWServices.LOG_TRACE, this.iwRequest);
            }
            this.addRowSetXML(em, 0, "EmailAddresses");
            ent.append(em);
            em = new StringBuffer("");
            this.iwRequest.lnkIWServices.logConsole("IM addresses:", IWServices.LOG_TRACE, this.iwRequest);
            for (Im im : entry.getImAddresses()) {
                emi = new StringBuffer("");
                emi.append(this.oneCol("EmailAddress", im.getAddress(), 0));
                this.iwRequest.lnkIWServices.logConsole(" " + im.getAddress(), IWServices.LOG_TRACE, this.iwRequest);
                if (im.getRel() != null) {
                    emi.append(this.oneCol("Rel", im.getRel(), 1));
                    this.iwRequest.lnkIWServices.logConsole(" rel:" + im.getRel(), IWServices.LOG_TRACE, this.iwRequest);
                }
                if (im.getLabel() != null) {
                    emi.append(this.oneCol("Label", im.getLabel(), 2));
                    this.iwRequest.lnkIWServices.logConsole(" label:" + im.getLabel(), IWServices.LOG_TRACE, this.iwRequest);
                }
                if (im.getProtocol() != null) {
                    emi.append(this.oneCol("Protocol", im.getProtocol(), 3));
                    this.iwRequest.lnkIWServices.logConsole(" protocol:" + im.getProtocol(), IWServices.LOG_TRACE, this.iwRequest);
                }
                emi.append(this.oneCol("Primary", im.getPrimary() != false ? "1" : "0", 4));
                if (im.getPrimary().booleanValue()) {
                    this.iwRequest.lnkIWServices.logConsole(" (primary) ", IWServices.LOG_TRACE, this.iwRequest);
                }
                em.append(this.addRowXML(emi));
                this.iwRequest.lnkIWServices.logConsole("\n", IWServices.LOG_TRACE, this.iwRequest);
            }
            this.addRowSetXML(em, 0, "IMAddresses");
            ent.append(em);
            em = new StringBuffer("");
            this.iwRequest.lnkIWServices.logConsole("Groups:", IWServices.LOG_TRACE, this.iwRequest);
            for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
                emi = new StringBuffer("");
                String groupHref = group.getHref();
                emi.append(this.oneCol("Id", groupHref, 0));
                this.iwRequest.lnkIWServices.logConsole("  Id: " + groupHref, IWServices.LOG_TRACE, this.iwRequest);
                em.append(this.addRowXML(emi));
            }
            this.addRowSetXML(em, 0, "Groups");
            ent.append(em);
            em = new StringBuffer("");
            this.iwRequest.lnkIWServices.logConsole("Extended Properties:", IWServices.LOG_TRACE, this.iwRequest);
            for (ExtendedProperty property : entry.getExtendedProperties()) {
                emi = new StringBuffer("");
                if (property.getValue() != null) {
                    emi.append(this.oneCol(property.getName(), property.getValue(), 0));
                    this.iwRequest.lnkIWServices.logConsole("  " + property.getName() + "(value) = " + property.getValue(), IWServices.LOG_TRACE, this.iwRequest);
                } else if (property.getXmlBlob() != null) {
                    emi.append(this.oneCol(property.getName(), property.getXmlBlob().getBlob(), 0));
                    this.iwRequest.lnkIWServices.logConsole("  " + property.getName() + "(xmlBlob)= " + property.getXmlBlob().getBlob(), IWServices.LOG_TRACE, this.iwRequest);
                }
                em.append(this.addRowXML(emi));
            }
            this.addRowSetXML(em, 0, "ExtendedProperties");
            ent.append(em);
            em = new StringBuffer("");
            em.append(this.oneCol("ContactETag", entry.getEtag(), 0));
            this.iwRequest.lnkIWServices.logConsole("Contact's ETag: " + entry.getEtag(), IWServices.LOG_TRACE, this.iwRequest);
            Link photoLink = entry.getContactPhotoLink();
            if (photoLink != null) {
                String photoLinkHref = photoLink.getHref();
                em.append(this.oneCol("PhotoLink", photoLinkHref, 1));
                this.iwRequest.lnkIWServices.logConsole("Photo Link: " + photoLinkHref, IWServices.LOG_TRACE, this.iwRequest);
                if (photoLink.getEtag() != null) {
                    em.append(this.oneCol("PhotoLinkFlag", photoLink.getEtag(), 2));
                    this.iwRequest.lnkIWServices.logConsole("Contact Photo's ETag: " + photoLink.getEtag(), IWServices.LOG_TRACE, this.iwRequest);
                }
            }
            ent.append(this.addRowXML(em));
            this.addRowSetXML(ent, 0, "Contacts");
            ret.append(ent);
            ++i;
        }
        this.user = oldUser;
        this.password = oldPassword;
        return ret;
    }

    /*
     * Unable to fully structure code
     */
    private StringBuffer addEvent(String inputString) throws Exception {
        oldUser = this.user;
        oldPassword = this.password;
        parser = new DOMParser();
        temp = inputString.getBytes();
        bis = new ByteArrayInputStream(temp);
        try {
            parser.parse(new InputSource(bis));
        }
        catch (SAXException e) {
            ict = 0;
            ** while (ict < temp.length)
        }
lbl-1000:
        // 1 sources

        {
            if (!(temp[ict] == 9 || temp[ict] == 10 || temp[ict] == 13 || temp[ict] >= 32 && temp[ict] <= 55295 || temp[ict] >= 57344 && temp[ict] <= 65533 || temp[ict] >= 65536 && temp[ict] <= 0x10FFFF)) {
                temp[ict] = 32;
            }
            ++ict;
            continue;
        }
lbl16:
        // 1 sources

        this.iwRequest.lnkIWServices.logConsole("XML go: Reduced string to parse: " + new String(temp), IWServices.LOG_TRACE, this.iwRequest);
        parser = new DOMParser();
        bis = new ByteArrayInputStream(temp);
        try {
            parser.parse(new InputSource(bis));
        }
        catch (SAXException e1) {
            throw e1;
        }
        catch (IOException e1) {
            throw e1;
        }
        catch (IOException e) {
            throw e;
        }
        conDoc = parser.getDocument();
        bdConf = conDoc.getDocumentElement();
        if (!bdConf.getTagName().equals("IWMeeting")) {
            throw new Exception("Wrong root element!");
        }
        subs = bdConf.getElementsByTagName("Organizer");
        if (subs.getLength() != 1) {
            throw new Exception("Organizer element is missing or too many Organizer elements!");
        }
        sbn = (Element)subs.item(0);
        organizer = new EventWho();
        organizer.setEmail(sbn.getTextContent());
        if (this.user.trim().length() == 0) {
            this.user = organizer.getEmail();
            subs = bdConf.getElementsByTagName("Password");
            if (subs.getLength() != 1) {
                throw new Exception("Local Password element is missing or too many Local Password elements!");
            }
            sbn = (Element)subs.item(0);
            this.password = sbn.getTextContent();
        }
        myURL = "http://www.google.com/calendar/feeds/" + this.user + "/private/full";
        postUrl = new URL(myURL);
        ret = new StringBuffer();
        myService = new CalendarService("Interweave-BasaEmailAdaptor-1");
        this.iwRequest.lnkIWServices.logConsole("Service is created", IWServices.LOG_TRACE, this.iwRequest);
        try {
            myService.setUserCredentials(this.user, this.password);
        }
        catch (Exception e) {
            ret.append(new StringBuffer(this.oneColOneRow("FAILURE", "User" + this.user + "cannot connect")));
            this.user = oldUser;
            this.password = oldPassword;
            return ret;
        }
        this.iwRequest.lnkIWServices.logConsole("Credentials are set:" + this.user + " " + this.password, IWServices.LOG_TRACE, this.iwRequest);
        myEntry = new CalendarEventEntry();
        subs = bdConf.getElementsByTagName("Title");
        if (subs.getLength() != 1) {
            throw new Exception("Title element is missing or too many Title elements!");
        }
        sbn = (Element)subs.item(0);
        myEntry.setTitle((TextConstruct)new PlainTextConstruct(sbn.getTextContent()));
        subs = bdConf.getElementsByTagName("Subject");
        if (subs.getLength() != 1) {
            throw new Exception("Details element is missing or too many Details elements!");
        }
        sbn = (Element)subs.item(0);
        myEntry.setContent((TextConstruct)new PlainTextConstruct(sbn.getTextContent()));
        subs = bdConf.getElementsByTagName("StartTime");
        if (subs.getLength() != 1) {
            throw new Exception("StartTime element is missing or too many StartTime elements!");
        }
        sbn = (Element)subs.item(0);
        tzs = this.iwRequest.getTimeZoneShift();
        startTime = DateTime.parseDateTime((String)sbn.getTextContent());
        if (tzs != 0) {
            startTime.setValue(startTime.getValue() - (long)tzs * 3600000L);
        }
        if ((subs = bdConf.getElementsByTagName("EndTime")).getLength() > 1) {
            throw new Exception("Too many EndTime elements!");
        }
        endTime = new DateTime();
        endTime.setTzShift(startTime.getTzShift());
        endTime.setValue(startTime.getValue() + 3600000L);
        if (subs.getLength() == 1 && (et = (sbn = (Element)subs.item(0)).getTextContent().trim()).length() > 0) {
            endTime = DateTime.parseDateTime((String)et);
        }
        eventTimes = new When();
        eventTimes.setStartTime(startTime);
        eventTimes.setEndTime(endTime);
        myEntry.addTime(eventTimes);
        myEntry.addParticipant(organizer);
        subs = bdConf.getElementsByTagName("Participant");
        i = 0;
        while (i < subs.getLength()) {
            cdn = (Element)subs.item(i);
            participant = new EventWho();
            participant.setEmail(cdn.getTextContent());
            myEntry.addParticipant(participant);
            ++i;
        }
        entry = (CalendarEventEntry)myService.insert(postUrl, (IEntry)myEntry);
        ent = new StringBuffer("");
        em = new StringBuffer("");
        em = new StringBuffer("");
        em.append(this.oneCol("Title", entry.getTitle().getPlainText(), 0));
        em.append(this.oneCol("ContactETag", entry.getEtag(), 1));
        em.append(this.oneCol("Id", entry.getId(), 2));
        em.append(this.oneCol("IcalUID", entry.getIcalUID(), 3));
        this.iwRequest.lnkIWServices.logConsole("New Event's ETag, Id, UID: " + entry.getEtag() + ", " + entry.getId() + ", " + entry.getIcalUID(), IWServices.LOG_TRACE, this.iwRequest);
        em.append(this.oneCol("Edited", entry.getEdited().toString(), 0));
        ent.append(this.addRowXML(em));
        ret.append(ent);
        this.user = oldUser;
        this.password = oldPassword;
        return ret;
    }

    private StringBuffer getAllEvents(String inputString) throws ServiceException, IOException {
        String oldUser = this.user;
        String oldPassword = this.password;
        this.iwRequest.lnkIWServices.logConsole("GetAllEvents started...", IWServices.LOG_MINIMUM, this.iwRequest);
        DateTime startTime = DateTime.now();
        DateTime endTime = new DateTime(startTime.getValue() + 604800000L, startTime.getTzShift().intValue());
        int insc = inputString.indexOf(";");
        if (insc >= 0) {
            String et;
            int iet;
            String[] ec = inputString.split(";");
            String st = ec[0].trim();
            int ist = st.indexOf(" ");
            if (ist > 0) {
                st = String.valueOf(st.substring(0, ist)) + "T" + st.substring(ist + 1) + "Z";
            }
            if ((iet = (et = ec[1].trim()).indexOf(" ")) > 0) {
                et = String.valueOf(et.substring(0, iet)) + "T" + et.substring(iet + 1) + "Z";
            }
            int tzs = this.iwRequest.getTimeZoneShift();
            startTime = DateTime.parseDateTime((String)st);
            if (tzs != 0) {
                startTime.setValue(startTime.getValue() - (long)tzs * 3600000L);
            }
            startTime.setValue(startTime.getValue() + 1L);
            endTime = DateTime.parseDateTime((String)et);
            if (tzs != 0) {
                endTime.setValue(endTime.getValue() - (long)tzs * 3600000L);
            }
            if (this.user.trim().length() == 0 && ec.length > 2) {
                this.user = ec[2].trim();
                if (ec.length > 3) {
                    this.password = ec[3];
                }
            }
        }
        this.iwRequest.lnkIWServices.logConsole("Getting Events for User " + this.user, IWServices.LOG_MINIMUM, this.iwRequest);
        String myURL = "http://www.google.com/calendar/feeds/" + this.user + "/private/full";
        StringBuffer ret = new StringBuffer();
        CalendarService myService = new CalendarService("Interweave-BasaEmailAdaptor-1");
        this.iwRequest.lnkIWServices.logConsole("Service is created", IWServices.LOG_TRACE, this.iwRequest);
        try {
            myService.setUserCredentials(this.user, this.password);
        }
        catch (Exception e) {
            ret.append(new StringBuffer(this.oneColOneRow("FAILURE", "User" + this.user + "cannot connect")));
            this.user = oldUser;
            this.password = oldPassword;
            return ret;
        }
        this.iwRequest.lnkIWServices.logConsole("Credentials are set:" + this.user + " " + this.password, IWServices.LOG_TRACE, this.iwRequest);
        URL feedUrl = new URL(myURL);
        CalendarQuery myQuery = new CalendarQuery(feedUrl);
        myQuery.setUpdatedMin(startTime);
        myQuery.setUpdatedMax(endTime);
        this.iwRequest.lnkIWServices.logConsole("Query is created and set", IWServices.LOG_TRACE, this.iwRequest);
        CalendarEventFeed resultFeed = (CalendarEventFeed)myService.query((Query)myQuery, CalendarEventFeed.class);
        this.iwRequest.lnkIWServices.logConsole(resultFeed.getTitle().getPlainText(), IWServices.LOG_TRACE, this.iwRequest);
        int i = 0;
        while (i < resultFeed.getEntries().size()) {
            StringBuffer emi;
            StringBuffer ent = new StringBuffer("");
            StringBuffer em = new StringBuffer("");
            CalendarEventEntry entry = (CalendarEventEntry)resultFeed.getEntries().get(i);
            this.iwRequest.lnkIWServices.logConsole("Attendees:", IWServices.LOG_TRACE, this.iwRequest);
            for (EventWho email : entry.getParticipants()) {
                emi = new StringBuffer("");
                emi.append(this.oneCol("EmailAddress", email.getEmail(), 0));
                this.iwRequest.lnkIWServices.logConsole(" " + email.getEmail(), IWServices.LOG_TRACE, this.iwRequest);
                if (email.getRel() != null) {
                    emi.append(this.oneCol("Rel", email.getRel(), 1));
                    this.iwRequest.lnkIWServices.logConsole(" rel:" + email.getRel(), IWServices.LOG_TRACE, this.iwRequest);
                }
                if (email.getAttendeeStatus() != null) {
                    emi.append(this.oneCol("Status", email.getAttendeeStatus(), 2));
                    this.iwRequest.lnkIWServices.logConsole(" status:" + email.getAttendeeStatus(), IWServices.LOG_TRACE, this.iwRequest);
                }
                if (email.getAttendeeType() != null) {
                    emi.append(this.oneCol("Type", email.getAttendeeType(), 3));
                    this.iwRequest.lnkIWServices.logConsole(" type:" + email.getAttendeeType(), IWServices.LOG_TRACE, this.iwRequest);
                }
                em.append(this.addRowXML(emi));
                this.iwRequest.lnkIWServices.logConsole("\n", IWServices.LOG_TRACE, this.iwRequest);
            }
            this.addRowSetXML(em, 0, "Attendees");
            ent.append(em);
            em = new StringBuffer("");
            this.iwRequest.lnkIWServices.logConsole("Times:", IWServices.LOG_TRACE, this.iwRequest);
            for (EventWho email : entry.getTimes()) {
                emi = new StringBuffer("");
                emi.append(this.oneCol("StartTime", email.getStartTime().toString(), 0));
                this.iwRequest.lnkIWServices.logConsole("StartTime:" + email.getStartTime().toString(), IWServices.LOG_TRACE, this.iwRequest);
                if (email.getRel() != null) {
                    emi.append(this.oneCol("Rel", email.getRel(), 1));
                    this.iwRequest.lnkIWServices.logConsole(" rel:" + email.getRel(), IWServices.LOG_TRACE, this.iwRequest);
                }
                if (email.getEndTime() != null) {
                    emi.append(this.oneCol("EndTime", email.getEndTime().toString(), 2));
                    this.iwRequest.lnkIWServices.logConsole(" EndTime:" + email.getEndTime().toString(), IWServices.LOG_TRACE, this.iwRequest);
                }
                em.append(this.addRowXML(emi));
                this.iwRequest.lnkIWServices.logConsole("\n", IWServices.LOG_TRACE, this.iwRequest);
            }
            this.addRowSetXML(em, 0, "DateTimes");
            ent.append(em);
            em = new StringBuffer("");
            int cnm = 0;
            em.append(this.oneCol("Title", entry.getTitle().getPlainText(), cnm++));
            em.append(this.oneCol("ContactETag", entry.getEtag(), cnm++));
            TextConstruct smt = entry.getSummary();
            String smr = "";
            if (smt != null) {
                smr = smt.getPlainText();
            }
            em.append(this.oneCol("Summary", smr, cnm++));
            smr = "";
            TextContent smc = (TextContent)entry.getContent();
            if (smc != null) {
                smr = smc.getContent().getPlainText();
            }
            em.append(this.oneCol("Description", smr, cnm++));
            em.append(this.oneCol("Id", entry.getId(), cnm++));
            em.append(this.oneCol("IcalUID", entry.getIcalUID(), cnm++));
            this.iwRequest.lnkIWServices.logConsole("Event's ETag, Id, UID: " + entry.getEtag() + ", " + entry.getId() + ", " + entry.getIcalUID(), IWServices.LOG_TRACE, this.iwRequest);
            em.append(this.oneCol("Edited", entry.getEdited().toString(), cnm++));
            ent.append(this.addRowXML(em));
            this.addRowSetXML(ent, 0, "Events");
            ret.append(ent);
            ++i;
        }
        this.user = oldUser;
        this.password = oldPassword;
        return ret;
    }

    public class IWAuthenticator
    extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(IWEmailBaseAdaptor.this.getUser(), IWEmailBaseAdaptor.this.getPassword());
        }
    }
}

