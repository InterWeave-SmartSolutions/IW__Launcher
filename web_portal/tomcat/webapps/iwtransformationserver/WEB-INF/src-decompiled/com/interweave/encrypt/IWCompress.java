/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.encrypt;

import com.interweave.encrypt.IWIEncrypt;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class IWCompress
implements IWIEncrypt {
    public String encrypt(byte[] inputData) {
        try {
            String inputStr = new String(inputData);
            byte[] input = inputStr.getBytes();
            int len = inputStr.length();
            byte[] output = new byte[len];
            Deflater compress = new Deflater();
            compress.setInput(input);
            compress.finish();
            int compressedDataLength = compress.deflate(output);
            String outStr = new String(output, 0, compressedDataLength);
            return outStr;
        }
        catch (Exception exception) {
            return "";
        }
    }

    public String decrypt(String inputStr) {
        try {
            Inflater decompress = new Inflater();
            decompress.setInput(inputStr.getBytes(), 0, inputStr.length());
            int len = inputStr.length();
            byte[] result = new byte[len];
            int resultLength = decompress.inflate(result);
            decompress.end();
            String outStr = new String(result, 0, resultLength);
            return outStr;
        }
        catch (Exception exception) {
            return "";
        }
    }
}

