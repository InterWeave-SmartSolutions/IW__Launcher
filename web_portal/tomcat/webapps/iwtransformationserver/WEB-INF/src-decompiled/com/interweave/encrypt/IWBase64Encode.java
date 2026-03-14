/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.encrypt;

import com.interweave.encrypt.IWIEncrypt;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class IWBase64Encode
implements IWIEncrypt {
    public String encrypt(byte[] inputData) {
        return IWBase64Encode.base64Encrypt(inputData);
    }

    public String decrypt(String inputStr) {
        return IWBase64Encode.base64Decrypt(inputStr);
    }

    public static String base64Decrypt(String inputStr) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] out = decoder.decodeBuffer(inputStr);
            String outStr = new String(out);
            decoder = null;
            return outStr;
        }
        catch (Exception exception) {
            return "";
        }
    }

    public static String base64Encrypt(byte[] inputData) {
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            String outStr = encoder.encode(inputData);
            encoder = null;
            return outStr;
        }
        catch (Exception exception) {
            return "";
        }
    }

    public static String base64Encrypt(String inputString) {
        return IWBase64Encode.base64Encrypt(inputString.getBytes());
    }
}

