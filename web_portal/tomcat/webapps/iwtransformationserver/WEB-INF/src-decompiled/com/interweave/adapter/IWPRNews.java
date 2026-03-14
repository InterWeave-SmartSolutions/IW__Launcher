/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.core.IWRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

public class IWPRNews
implements IWIDataMap {
    private Access curAccess = null;
    private File rootFile = null;
    private String mapName = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }

    public void closeConnection() {
    }

    public StringBuffer go(IWRequest request) throws Exception {
        return this.goImpl();
    }

    public StringBuffer goImpl() throws Exception {
        StringBuffer responseBuffer = new StringBuffer();
        StringBuffer bodyMarkupBuffer = new StringBuffer();
        File PRNewsFile = new File("C:\\testfile.baycity");
        FileInputStream prNewsStream = new FileInputStream(PRNewsFile);
        int nRecCount = 0;
        byte[] syn = new byte[2];
        while (prNewsStream.available() > 0) {
            prNewsStream.read(syn);
            if (syn[0] == 22) {
                ++nRecCount;
                syn = new byte[2];
            }
            if (syn[0] != 2 && syn[1] != 2) continue;
            prNewsStream.skip(2L);
            bodyMarkupBuffer.append(this.getBody(prNewsStream, nRecCount));
        }
        responseBuffer.append("<datamap ID=\"1\" name=\"newswire\" rowcount=\"" + nRecCount + "\">\n");
        responseBuffer.append("\t<data rowcount=\"" + nRecCount + "\">\n");
        responseBuffer.append(bodyMarkupBuffer.toString());
        responseBuffer.append("\t</data>\n</datamap>\n");
        return responseBuffer;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        this.mapName = map.getName();
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    public static void main(String[] params) throws Exception {
        IWPRNews iwfileAdapter = new IWPRNews();
        System.out.println(iwfileAdapter.goImpl().toString());
    }

    public String getBody(FileInputStream prStream, int currentPosition) throws Exception {
        StringBuffer responseMarkup = new StringBuffer();
        while (prStream.read() != 58) {
        }
        byte[] updateBytes = new byte[6];
        prStream.read(updateBytes);
        String szUpdate = new String(updateBytes);
        StringBuffer headLineBuffer = new StringBuffer();
        byte data = (byte)prStream.read();
        if (szUpdate.equalsIgnoreCase("UPDATE")) {
            data = (byte)prStream.read();
            while (data != 13) {
                headLineBuffer.append((char)data);
                data = (byte)prStream.read();
            }
            prStream.skip(1L);
        } else {
            headLineBuffer.append(szUpdate);
            while (data != 13) {
                headLineBuffer.append((char)data);
                data = (byte)prStream.read();
            }
            prStream.skip(1L);
        }
        int i = 0;
        while (i < 2) {
            while (prStream.read() != 10) {
            }
            ++i;
        }
        prStream.skip(1L);
        StringBuffer articleBuffer = new StringBuffer();
        data = (byte)prStream.read();
        while (data != 3) {
            if (data == 13 || data == 10 || data == 9) {
                data = (byte)prStream.read();
                continue;
            }
            articleBuffer.append((char)data);
            data = (byte)prStream.read();
        }
        String article = articleBuffer.toString();
        int nIdxContact = article.indexOf("CONTACT:");
        String szContact = article.substring(nIdxContact, article.length());
        szContact = szContact.substring(8, szContact.length());
        szContact = szContact.trim();
        String headBuffer = headLineBuffer.toString().trim();
        if ((article = article.trim()).indexOf("+") > -1) {
            responseMarkup.append("\t\t<row number=\"" + currentPosition + "\">\n");
            responseMarkup.append("\t\t\t<col number=\"1\" name=\"error\">Article does not comply to format.</col>\n");
            responseMarkup.append("\t\t</row>\n");
            return responseMarkup.toString();
        }
        responseMarkup.append("\t\t<row number=\"" + currentPosition + "\">\n");
        responseMarkup.append("\t\t\t<col number=\"1\" name=\"headline\">" + headBuffer + "</col>\n");
        responseMarkup.append("\t\t\t<col number=\"2\" name=\"content\">" + article + "</col>\n");
        responseMarkup.append("\t\t\t<col number=\"3\" name=\"contact\">" + szContact + "</col>\n");
        responseMarkup.append("\t\t</row>\n");
        return responseMarkup.toString();
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

