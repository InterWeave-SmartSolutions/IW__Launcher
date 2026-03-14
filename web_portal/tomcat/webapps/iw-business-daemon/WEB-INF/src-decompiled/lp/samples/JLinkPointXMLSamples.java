/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.samples.JLinkPointSample;
import lp.samples.XMLSampleData;

public class JLinkPointXMLSamples
extends JLinkPointSample {
    String sXML = "";

    public static void main(String[] args) {
        JLinkPointXMLSamples smpl = new JLinkPointXMLSamples();
        System.out.println("\n*****************************************************");
        System.out.println("Credit Card SALE With Minimum Fields required for AVS");
        System.out.println("and Card Code fraud prevention measures ");
        System.out.println("******************************************************");
        smpl.setXML(XMLSampleData.AVS_CVM);
        smpl.process();
        System.out.println("\n*****************************************************");
        System.out.println("Minimum Required Fields for a FORCED TICKET Transaction");
        System.out.println("*****************************************************");
        smpl.setXML(XMLSampleData.FORCED_TICKET);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("Required Fields for Credit Card SALE with ITEMS Transaction");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.ITEMS_W_ESD);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("An Example Level 2 Purchasing Card SALE Transaction");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.L2PURCHASING_CARD);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("Minimum Required Fields for a Credit Card CREDIT Transaction");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.RETURN);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("An Example Credit Card SALE with all commonly used fields included");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.SALE_MAXINFO);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("Minimum Required Fields for a Credit Card SALE");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.SALE_MININFO);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("An Example of Shipping Calculation Transaction");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.SHIPPING);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("An Example of Tax Calculation Transaction");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.TAX);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("An Example of PreAuth/PostAuth/Void Credit Card Transactions");
        System.out.println("**********************************************************");
        System.out.println("\nFirst run PREAUTH transaction:");
        smpl.setXML(XMLSampleData.PREAUTH);
        smpl.process();
        if (smpl.R_Error.length() == 0) {
            System.out.println("\nNow run POSTAUTH using 'oid' recieved as r_order");
            System.out.println("from PREAUTH transaction");
            smpl.setXML(smpl.patchXML_OID(XMLSampleData.POSTAUTH));
            smpl.process();
        }
        if (smpl.R_Error.length() == 0) {
            System.out.println("\nNow VOID transaction using its 'oid' ");
            smpl.setXML(smpl.patchXML_OID(XMLSampleData.VOID));
            smpl.process();
        }
        System.out.println("\n**********************************************************");
        System.out.println("An Example of Periodic Billing Transactions");
        System.out.println("**********************************************************");
        System.out.println("\nFirst create new PB (with minimum required fields ):");
        smpl.setXML(XMLSampleData.PB_NEW);
        smpl.process();
        if (smpl.R_Error.length() == 0) {
            System.out.println("\nNow change existing PB using 'oid' recieved as r_ordernum");
            System.out.println("from previous transaction");
            smpl.setXML(smpl.patchXML_OID(XMLSampleData.PB_MODIFY));
            smpl.process();
        }
        if (smpl.R_Error.length() == 0) {
            System.out.println("\nNow we cancel existing PB using its 'oid'");
            smpl.setXML(smpl.patchXML_OID(XMLSampleData.PB_CANCEL));
            smpl.process();
        }
        System.out.println("\n**********************************************************");
        System.out.println("An Example of VirtualCheck Transactions");
        System.out.println("**********************************************************");
        System.out.println("\nFirst, run VirtualCheck SALE transaction (with minimum required fields");
        smpl.setXML(XMLSampleData.VCHECK_SALE);
        smpl.process();
        if (smpl.R_Error.length() == 0) {
            System.out.println("\nNow void the sale using its 'oid' recieved as r_ordernum");
            System.out.println("from previous transaction");
            smpl.setXML(smpl.patchXML_OID(XMLSampleData.VCHECK_VOID));
            smpl.process();
        }
        System.out.println("\n**********************************************************");
        System.out.println("An Example of Retail Keyed Credit Card SALE");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.RETAIL_KEYED);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("An Example of Retail Credit Card SALE with partial AVS");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.RETAIL_PARTIAL_AVS);
        smpl.process();
        System.out.println("\n**********************************************************");
        System.out.println("An Example of Retail Swipe Credit Card SALE");
        System.out.println("**********************************************************");
        smpl.setXML(XMLSampleData.RETAIL_SWIPE);
        smpl.process();
    }

    public void setXML(String xml) {
        this.sXML = this.setXML_Configfile(xml);
    }

    public String setXML_Configfile(String xml) {
        return JLinkPointXMLSamples.replaceTagValue(xml, "<configfile>", this.configfile);
    }

    public String patchXML_OID(String xml) {
        return JLinkPointXMLSamples.replaceTagValue(xml, "<oid>", this.R_OrderNum);
    }

    public static String replaceTagValue(String xml, String tag, String val) {
        StringBuffer sb = new StringBuffer(1024);
        int idx = xml.indexOf(tag);
        if (idx == -1) {
            return xml;
        }
        sb.append(xml.substring(0, idx + tag.length()));
        sb.append(val);
        idx = xml.indexOf(60, idx + 1);
        sb.append(xml.substring(idx));
        return sb.toString();
    }

    protected String getOrderXML() {
        return this.sXML;
    }
}

