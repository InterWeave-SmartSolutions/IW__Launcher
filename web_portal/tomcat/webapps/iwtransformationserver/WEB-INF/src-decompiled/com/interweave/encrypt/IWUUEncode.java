/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.encrypt;

import com.interweave.encrypt.IWIEncrypt;
import sun.misc.UUDecoder;
import sun.misc.UUEncoder;

public class IWUUEncode
implements IWIEncrypt {
    public String encrypt(byte[] inputData) {
        try {
            UUEncoder encoder = new UUEncoder();
            String outStr = encoder.encode(inputData);
            encoder = null;
            return outStr;
        }
        catch (Exception exception) {
            return "";
        }
    }

    public String decrypt(String inputStr) {
        try {
            UUDecoder decoder = new UUDecoder();
            return "";
        }
        catch (Exception exception) {
            return "";
        }
    }
}

