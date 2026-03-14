/*
 * Decompiled with CFR 0.152.
 */
package lp.samples;

import lp.samples.AVS_CVM;
import lp.samples.FORCED_TICKET;
import lp.samples.ITEMS_W_ESD;
import lp.samples.JLinkPointSample;
import lp.samples.L2PURCHASING_CARD;
import lp.samples.MULTIPLE;
import lp.samples.PB_CANCEL;
import lp.samples.PB_MODIFY;
import lp.samples.PB_NEW;
import lp.samples.POSTAUTH;
import lp.samples.PREAUTH;
import lp.samples.RETAIL_KEYED;
import lp.samples.RETAIL_PARTIAL_AVS;
import lp.samples.RETAIL_SWIPE;
import lp.samples.SALE_MAXINFO;
import lp.samples.SALE_MININFO;
import lp.samples.SHIPPING;
import lp.samples.TAX;
import lp.samples.VCHECK_SALE;
import lp.samples.VCHECK_VOID;
import lp.samples.VOID;

public class JLinkPointLPOrderPartSamples {
    public static void main(String[] args) {
        System.out.println("\nSAMPLE TRANSACTIONS using LPOrderPart object ");
        JLinkPointLPOrderPartSamples.CreditCardSales();
        JLinkPointLPOrderPartSamples.PreAuth_PostAuth_Void();
        JLinkPointLPOrderPartSamples.PeriodicBilling();
        JLinkPointLPOrderPartSamples.VirtualCheck();
        System.out.println("\n**********************************************************");
        System.out.println("Sample TAX Calculation Transaction ");
        System.out.println("**********************************************************");
        new TAX().process();
        System.out.println("\n**********************************************************");
        System.out.println("Sample SHIPPING Calculation Transaction ");
        System.out.println("**********************************************************");
        new SHIPPING().process();
    }

    public static void PreAuth_PostAuth_Void() {
        System.out.println("\n*****************************************************");
        System.out.println("Sample for PreAuth/PostAuth/Void Credit Card Transactions");
        System.out.println("******************************************************");
        System.out.println("\nFirst run PREAUTH");
        JLinkPointSample smpl = new PREAUTH();
        smpl.process();
        if (smpl.R_Error.length() != 0) {
            return;
        }
        System.out.println("\nNow run POSTAUTH transaction using 'oid' recieved");
        System.out.println("from previous transaction. OID=" + smpl.R_OrderNum);
        smpl = new POSTAUTH(smpl.R_OrderNum);
        smpl.process();
        if (smpl.R_Error.length() != 0) {
            return;
        }
        System.out.println("\nNow VOID  PreAuth/PostAuth using 'oid' ");
        System.out.println("from previous transactions. OID=" + smpl.R_OrderNum);
        new VOID(smpl.R_OrderNum).process();
    }

    public static void PeriodicBilling() {
        System.out.println("\n*****************************************************");
        System.out.println("Sample for Sample Periodic Billing Transactions");
        System.out.println("******************************************************");
        System.out.println("\nFirst create new Periodic Billing");
        JLinkPointSample smpl = new PB_NEW();
        smpl.process();
        if (smpl.R_Error.length() != 0) {
            return;
        }
        System.out.println("\nNow modify existing Periodic Billing using 'oid' recieve");
        System.out.println("from previous transaction. OID=" + smpl.R_OrderNum);
        smpl = new PB_MODIFY(smpl.R_OrderNum);
        smpl.process();
        if (smpl.R_Error.length() != 0) {
            return;
        }
        System.out.println("\nNow cancel existing Periodic Billing using its 'oid' ");
        System.out.println("OID=" + smpl.R_OrderNum);
        new PB_CANCEL(smpl.R_OrderNum).process();
    }

    public static void VirtualCheck() {
        System.out.println("\n*****************************************************");
        System.out.println("Sample for Virtual Check Sale/Void Transactions");
        System.out.println("******************************************************");
        System.out.println("\nFirst run Sale");
        VCHECK_SALE smpl = new VCHECK_SALE();
        smpl.process();
        if (smpl.R_Error.length() != 0) {
            return;
        }
        System.out.println("\nNow VOID  Virtual Check Sale using 'oid' ");
        System.out.println("from previous transactions. OID=" + smpl.R_OrderNum);
        new VCHECK_VOID(smpl.R_OrderNum).process();
    }

    public static void CreditCardSales() {
        System.out.println("\n*****************************************************");
        System.out.println("Credit Card SALE With Minimum Fields required for AVS");
        System.out.println("and Card Code fraud prevention measures ");
        System.out.println("******************************************************");
        new AVS_CVM().process();
        System.out.println("\n*****************************************************");
        System.out.println("Minimum Required Fields for a FORCED TICKET Transaction");
        System.out.println("*****************************************************");
        new FORCED_TICKET().process();
        System.out.println("\n**********************************************************");
        System.out.println("An Example Level 2 Purchasing Card SALE ");
        System.out.println("**********************************************************");
        new L2PURCHASING_CARD().process();
        System.out.println("\n**********************************************************");
        System.out.println("Minimum Required Fields for a Credit Card SALE");
        System.out.println("**********************************************************");
        new SALE_MININFO().process();
        System.out.println("\n**********************************************************");
        System.out.println("An Example Credit Card SALE with all commonly used fields included");
        System.out.println("**********************************************************");
        new SALE_MAXINFO().process();
        System.out.println("\n**********************************************************");
        System.out.println("Required Fields for Credit Card SALE with ITEMS Transaction");
        System.out.println("**********************************************************");
        new ITEMS_W_ESD().process();
        System.out.println("\n**********************************************************");
        System.out.println("Sample of multiple Credit Card PreAuth Transactions");
        System.out.println("**********************************************************");
        new MULTIPLE().process();
        System.out.println("\n**********************************************************");
        System.out.println("Sample Retail Keyed Credit Card Transaction ");
        System.out.println("**********************************************************");
        new RETAIL_KEYED().process();
        System.out.println("\n**********************************************************");
        System.out.println("Sample Retail Partial AVS Credit Card Transaction ");
        System.out.println("**********************************************************");
        new RETAIL_PARTIAL_AVS().process();
        System.out.println("\n**********************************************************");
        System.out.println("Sample Retail Swipe Credit Card Transaction ");
        System.out.println("**********************************************************");
        new RETAIL_SWIPE().process();
    }
}

