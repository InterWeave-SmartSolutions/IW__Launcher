/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.core;

import java.io.IOException;

public class IWExecute {
    public static void ExecuteCommand(String externCommand) throws IOException {
        String runCommand = new String("start command /c ");
        Process proc = Runtime.getRuntime().exec(String.valueOf(runCommand) + externCommand);
        try {
            proc.waitFor();
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException raised: " + e.getMessage());
        }
    }
}

