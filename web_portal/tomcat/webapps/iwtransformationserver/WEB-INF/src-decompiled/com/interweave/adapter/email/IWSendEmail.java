/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.email;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.IWMappingType;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class IWSendEmail
implements IWIDataMap {
    private Access curAccess = null;
    private String to = null;
    private String attachment = null;
    private String subject = null;
    private String message = null;
    private String from = null;
    private String cc = null;
    private String bcc = null;
    private String url = null;
    private String mailhost = null;
    private String mailer = "SendEmail";
    private String mbox = "OUTBOX";
    private String protocol = null;
    private String host = null;
    private String user = null;
    private String password = null;
    private String record = null;
    private String mapName = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public StringBuffer go(IWRequest request) throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        String strData = null;
        Access access = (Access)((Object)this.accessList.get("procedure"));
        Values values = null;
        int count = 1;
        this.curAccess = null;
        if (access != null) {
            this.curAccess = access;
            values = access.getValues();
            if (values != null) {
                Iterator iterate = values.getParameter().iterator();
                responseBuffer.append("    <datamap ID=\"1\" name=\"" + this.mapName + "\" rowcount=\"" + values.getParameter().size() + "\">\n      <data rowcount=\"" + values.getParameter().size() + "\">\n        <row number=\"1\">\n");
                while (iterate.hasNext()) {
                    strData = null;
                    Parameter param = (Parameter)((Object)iterate.next());
                    Mapping mapping = param.getMapping();
                    String type = mapping.getType();
                    IWMappingType map = new IWMappingType();
                    strData = map.getData(param, request);
                    if (strData == null) {
                        strData = "";
                    }
                    this.setMailParameter(strData, mapping.getContent());
                    responseBuffer.append("            <col number=\"" + count + "\" name=\"" + mapping.getContent() + "\">" + strData + "</col>\n");
                    ++count;
                }
                responseBuffer.append("        </row>\n      </data>\n    </datamap>\n");
            }
        }
        this.sendMessage();
        return responseBuffer;
    }

    public void add(String strName, String strData) {
        this.setMailParameter(strData, strName);
    }

    private void setMailParameter(String strData, String strName) {
        if (strName.compareToIgnoreCase("to") == 0) {
            this.to = strData;
        }
        if (strName.compareToIgnoreCase("attachment") == 0) {
            this.attachment = strData;
        } else if (strName.compareToIgnoreCase("subject") == 0) {
            this.subject = strData;
        } else if (strName.compareToIgnoreCase("message") == 0) {
            this.message = strData;
        } else if (strName.compareToIgnoreCase("from") == 0) {
            this.from = strData;
        } else if (strName.compareToIgnoreCase("cc") == 0) {
            this.cc = strData;
        } else if (strName.compareToIgnoreCase("bcc") == 0) {
            this.bcc = strData;
        } else if (strName.compareToIgnoreCase("url") == 0) {
            this.url = strData;
        } else if (strName.compareToIgnoreCase("mailhost") == 0) {
            this.mailhost = strData;
        } else if (strName.compareToIgnoreCase("mailer ") == 0) {
            this.mailer = strData;
        } else if (strName.compareToIgnoreCase("protocol") == 0) {
            this.protocol = strData;
        } else if (strName.compareToIgnoreCase("host") == 0) {
            this.host = strData;
        } else if (strName.compareToIgnoreCase("user") == 0) {
            this.user = strData;
        } else if (strName.compareToIgnoreCase("password") == 0) {
            this.password = strData;
        } else if (strName.compareToIgnoreCase("record ") == 0) {
            this.record = strData;
        }
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.mapName = map.getName();
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    public void closeConnection() {
    }

    public void sendMessage() {
        try {
            Properties props = System.getProperties();
            if (this.mailhost != null) {
                props.put("mail.smtp.host", this.mailhost);
            }
            Session session = Session.getDefaultInstance((Properties)props, null);
            MimeMessage msg = new MimeMessage(session);
            if (this.from != null && this.from.length() > 0) {
                msg.setFrom((Address)new InternetAddress(this.from));
            } else {
                msg.setFrom();
            }
            msg.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse((String)this.to, (boolean)false));
            if (this.cc != null) {
                msg.setRecipients(Message.RecipientType.CC, (Address[])InternetAddress.parse((String)this.cc, (boolean)false));
            }
            if (this.bcc != null) {
                msg.setRecipients(Message.RecipientType.BCC, (Address[])InternetAddress.parse((String)this.bcc, (boolean)false));
            }
            msg.setSubject(this.subject);
            msg.setHeader("X-Mailer", this.mailer);
            msg.setSentDate(new Date());
            if (this.attachment != null) {
                File file = new File(this.attachment);
                if (file.exists()) {
                    MimeBodyPart mbp1 = new MimeBodyPart();
                    mbp1.setText(this.message);
                    MimeBodyPart mbp2 = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(this.attachment);
                    mbp2.setDataHandler(new DataHandler((DataSource)fds));
                    mbp2.setFileName(fds.getName());
                    MimeMultipart mp = new MimeMultipart();
                    mp.addBodyPart((BodyPart)mbp1);
                    mp.addBodyPart((BodyPart)mbp2);
                    msg.setContent((Multipart)mp);
                } else {
                    msg.setText(this.message);
                }
            } else {
                msg.setText(this.message);
            }
            Transport.send((Message)msg);
            if (this.record != null) {
                Store store = null;
                if (this.url != null) {
                    URLName urln = new URLName(this.url);
                    store = session.getStore(urln);
                    store.connect();
                } else {
                    store = this.protocol != null ? session.getStore(this.protocol) : session.getStore();
                    if (this.host != null || this.user != null || this.password != null) {
                        store.connect(this.host, this.user, this.password);
                    } else {
                        store.connect();
                    }
                }
                Folder folder = store.getFolder(this.record);
                if (folder == null) {
                    System.err.println("Can't get record folder.");
                    System.exit(1);
                }
                if (!folder.exists()) {
                    folder.create(1);
                }
                Message[] msgs = new Message[]{msg};
                folder.appendMessages(msgs);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDriver() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public String getUser() {
        return null;
    }
}

