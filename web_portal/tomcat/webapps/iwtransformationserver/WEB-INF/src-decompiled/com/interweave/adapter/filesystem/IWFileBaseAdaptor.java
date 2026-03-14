/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.filesystem;

import com.interweave.adapter.http.IWXMLHierarchicalAdaptor;
import com.interweave.bindings.Datamap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class IWFileBaseAdaptor
extends IWXMLHierarchicalAdaptor {
    protected FileType fileType;
    protected FileCommand fileCommand;
    protected int startPosition;
    protected int endPosition;
    protected int startFilePosition;
    protected int endFilePosition;
    protected int headPosition;
    protected int headPosition2;
    protected boolean stepThrough = false;
    protected boolean escape = false;
    protected boolean firstLineKey = false;
    protected String dynamicFileName = null;
    protected String csvFilter1 = null;
    protected String csvFilter2 = null;
    protected String separator = "'";

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setup(Datamap map, IWRequest request) throws Exception {
        request.lnkIWServices.logConsole("IWFileBaseAdaptor - setup started!", IWServices.LOG_TRACE, request);
        this.setupInitConnect(map, request);
        this.headPosition = 0;
        this.headPosition2 = -1;
        this.startPosition = -1;
        this.startFilePosition = -1;
        this.endPosition = -1;
        this.endFilePosition = -1;
        this.firstLineKey = false;
        this.stepThrough = false;
        this.dynamicFileName = null;
        this.csvFilter1 = null;
        this.csvFilter2 = null;
        String ct = this.contentType.trim();
        if (ct.length() <= 0) throw new Exception("File type/command is missing.");
        String[] tpc = ct.split(";");
        if (tpc.length != 2) {
            throw new Exception("Incorrect File type/command format");
        }
        if (tpc[0].equalsIgnoreCase(FileType.TSV.getLabel())) {
            this.separator = "\t";
            this.fileType = FileType.CSV;
        } else if (tpc[0].equalsIgnoreCase(FileType.TSVH.getLabel())) {
            this.separator = "\t";
            this.fileType = FileType.CSVH;
        } else if (tpc[0].equalsIgnoreCase(FileType.CSV.getLabel())) {
            this.separator = ",";
            this.fileType = FileType.CSV;
        } else if (tpc[0].equalsIgnoreCase(FileType.CSVH.getLabel())) {
            this.separator = ",";
            this.fileType = FileType.CSVH;
        } else if (tpc[0].equalsIgnoreCase(FileType.NVP.getLabel())) {
            this.fileType = FileType.NVP;
        } else if (tpc[0].equalsIgnoreCase(FileType.TXT.getLabel())) {
            this.fileType = FileType.TXT;
        } else if (tpc[0].equalsIgnoreCase(FileType.TXTL.getLabel())) {
            this.fileType = FileType.TXTL;
        } else {
            if (!tpc[0].equalsIgnoreCase(FileType.XML.getLabel())) throw new Exception("Unknown File type " + tpc[0]);
            this.fileType = FileType.XML;
        }
        String[] tpcc = tpc[1].split(":");
        if (tpcc.length < 1 || tpcc.length > 3) {
            throw new Exception("Invalid File command format: " + tpc[1]);
        }
        if (tpcc[0].equalsIgnoreCase(FileCommand.APPEND.getLabel())) {
            if (tpcc.length != 1) {
                throw new Exception("Invalid File command format: " + tpc[1]);
            }
            this.fileCommand = FileCommand.APPEND;
        } else if (tpcc[0].equalsIgnoreCase(FileCommand.DELETE.getLabel())) {
            if (tpcc.length != 1) {
                throw new Exception("Invalid File command format: " + tpc[1]);
            }
            this.fileCommand = FileCommand.DELETE;
        } else if (tpcc[0].equalsIgnoreCase(FileCommand.INSERT.getLabel())) {
            if (tpcc.length != 2) {
                throw new Exception("Invalid File command format: " + tpc[1]);
            }
            this.fileCommand = FileCommand.INSERT;
            this.startPosition = Integer.valueOf(tpcc[1]);
        } else if (tpcc[0].equalsIgnoreCase(FileCommand.MOVE.getLabel())) {
            if (tpcc.length != 1) {
                throw new Exception("Invalid File command format: " + tpc[1]);
            }
            this.fileCommand = FileCommand.MOVE;
        } else if (tpcc[0].equalsIgnoreCase(FileCommand.READ.getLabel())) {
            this.fileCommand = FileCommand.READ;
            int batchStep = -1;
            switch (tpcc.length) {
                case 3: {
                    if (tpcc[1].startsWith("*")) {
                        this.csvFilter1 = tpcc[1].substring(1);
                        this.csvFilter2 = tpcc[2];
                        request.lnkIWServices.logConsole("IWFileBaseAdaptor - filters set: Filter1=" + this.csvFilter1 + " Filter2=" + this.csvFilter2, IWServices.LOG_HTTP, request);
                    } else if (Character.isDigit(tpcc[2].charAt(0))) {
                        batchStep = Integer.valueOf(tpcc[2]);
                    } else {
                        String bstp = request.getParameter(tpcc[2]);
                        if (tpcc[1].equalsIgnoreCase("N")) {
                            this.dynamicFileName = bstp.trim().length() > 0 ? bstp : tpcc[2];
                        } else if (bstp.trim().length() > 0) {
                            batchStep = Integer.valueOf(bstp);
                        }
                    }
                    this.endPosition = batchStep;
                    this.endFilePosition = batchStep;
                }
                case 2: {
                    if (tpcc[1].startsWith("*")) {
                        this.csvFilter1 = tpcc[1].substring(1);
                        break;
                    }
                    if (tpcc[1].equalsIgnoreCase("S")) {
                        this.startFilePosition = -1;
                        this.endFilePosition = -1;
                        this.startPosition = Integer.valueOf(request.getParameter("TransactionFlowCounter")) * this.endPosition;
                        this.endPosition += this.startPosition;
                        this.stepThrough = true;
                        break;
                    }
                    if (tpcc[1].equalsIgnoreCase("B")) {
                        this.startPosition = -1;
                        this.endPosition = -1;
                        this.startFilePosition = Integer.valueOf(request.getParameter("TransactionFlowCounter")) * this.endFilePosition;
                        this.endFilePosition += this.startFilePosition;
                        this.stepThrough = true;
                        break;
                    }
                    if (tpcc[1].equalsIgnoreCase("K")) {
                        this.startFilePosition = -1;
                        this.endFilePosition = -1;
                        this.startPosition = -1;
                        this.endPosition = -1;
                        this.firstLineKey = true;
                        break;
                    }
                    if (tpcc[1].equalsIgnoreCase("N")) {
                        this.startFilePosition = -1;
                        this.endFilePosition = -1;
                        this.startPosition = -1;
                        this.endPosition = -1;
                        break;
                    }
                    this.startPosition = Integer.valueOf(tpcc[1]);
                }
            }
        } else if (tpcc[0].equalsIgnoreCase(FileCommand.RENAME.getLabel())) {
            if (tpcc.length != 1) {
                throw new Exception("Invalid File command format: " + tpc[1]);
            }
            this.fileCommand = FileCommand.RENAME;
        } else if (tpcc[0].equalsIgnoreCase(FileCommand.UPDATE.getLabel())) {
            this.fileCommand = FileCommand.UPDATE;
            switch (tpcc.length) {
                case 3: {
                    this.endPosition = Integer.valueOf(tpcc[2]);
                }
                case 2: {
                    this.startPosition = Integer.valueOf(tpcc[1]);
                }
            }
            if (this.startPosition >= 0 && this.endPosition >= 0 && this.startPosition >= this.endPosition) {
                throw new Exception("Incorrect index order.");
            }
        } else if (tpcc[0].equalsIgnoreCase(FileCommand.WRITE.getLabel())) {
            if (tpcc.length > 2) {
                throw new Exception("Invalid File command format: " + tpc[1]);
            }
            this.fileCommand = FileCommand.WRITE;
            if (tpcc.length == 2) {
                this.escape = tpcc[1].equalsIgnoreCase("E");
            }
        } else {
            if (!tpcc[0].equalsIgnoreCase(FileCommand.OVERWRITE.getLabel())) throw new Exception("Unknown File command " + tpcc[0]);
            if (tpcc.length > 2) {
                throw new Exception("Invalid File command format: " + tpc[1]);
            }
            this.fileCommand = FileCommand.OVERWRITE;
            if (tpcc.length == 2) {
                this.escape = tpcc[1].equalsIgnoreCase("E");
            }
        }
        request.lnkIWServices.logConsole("IWFileBaseAdaptor - setup ended!", IWServices.LOG_TRACE, request);
    }

    protected StringBuffer processRequest(String stpre, File file, File[] files, boolean temporaryFile) throws Exception {
        this.iwRequest.lnkIWServices.logConsole("processRequest started: " + (stpre.length() <= 20 ? stpre : stpre.substring(0, 20)) + "...", IWServices.LOG_TRACE, this.iwRequest);
        switch (this.fileCommand) {
            case DELETE: {
                if (files == null) {
                    if (!file.exists()) {
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + file.getPath() + " does not exist."))));
                    }
                    if (!file.delete()) {
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to delete file " + file.getPath()))));
                    }
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", file.getPath()))));
                }
                StringBuffer res = new StringBuffer();
                File[] fileArray = files;
                int n = 0;
                int n2 = fileArray.length;
                while (n < n2) {
                    File cf = fileArray[n];
                    if (!cf.exists()) {
                        res.append(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + cf.getPath() + " does not exist."))));
                    } else if (!cf.delete()) {
                        res.append(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to delete file " + cf.getPath()))));
                    } else {
                        res.append(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", cf.getPath()))));
                    }
                    ++n;
                }
                return this.addDataMapXML(res);
            }
            case RENAME: {
                if (!file.exists()) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + file.getPath() + " does not exist."))));
                }
                if (stpre == null || stpre.length() == 0) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to rename file " + file.getPath() + ". MIssing new name."))));
                }
                File df = new File(stpre);
                if (df.exists()) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + stpre + " already exists."))));
                }
                if (!file.renameTo(df)) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to rename file " + file.getPath() + "."))));
                }
                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", ""))));
            }
            case MOVE: {
                File dfn;
                if (!file.exists()) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + file.getPath() + " does not exist."))));
                }
                if (stpre == null || stpre.length() == 0) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to move file " + file.getPath() + ". MIssing destination directory."))));
                }
                File df = new File(stpre);
                if (!df.exists()) {
                    if (!df.mkdir()) {
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to create directory " + stpre))));
                    }
                } else if (!df.isDirectory()) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Directory " + stpre + " does not exists."))));
                }
                if (!file.renameTo(dfn = new File(String.valueOf(stpre) + File.pathSeparator + file.getName()))) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to move file " + file.getPath() + "."))));
                }
                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", ""))));
            }
            case WRITE: 
            case OVERWRITE: {
                if (!temporaryFile && !file.isDirectory() && file.exists()) {
                    if (this.fileCommand == FileCommand.WRITE) {
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + file.getPath() + " already exists."))));
                    }
                    if (!file.delete()) {
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to overwrite file " + file.getPath() + "."))));
                    }
                }
                if (stpre == null || stpre.trim().length() == 0) {
                    stpre = "";
                }
                if (this.escape) {
                    stpre = IWServices.unEscape(stpre);
                }
                if (!this.writeFile(file, stpre, false)) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to write file " + file.getPath() + "."))));
                }
                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", ""))));
            }
            case UPDATE: {
                if (!file.exists()) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + file.getPath() + " does not exist."))));
                }
                if (stpre == null || stpre.trim().length() == 0) {
                    stpre = "";
                }
                if (this.startPosition < 0 ? !this.writeFile(file, stpre, false) : !this.updateTypedFile(file, stpre, true)) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to update file " + file.getPath() + "."))));
                }
                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", ""))));
            }
            case INSERT: {
                if (!file.exists()) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + file.getPath() + " does not exist."))));
                }
                if (stpre == null) {
                    stpre = "";
                }
                if (!this.updateTypedFile(file, stpre, false)) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to update file " + file.getPath() + "."))));
                }
                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", ""))));
            }
            case READ: {
                if (files == null) {
                    if (!file.exists()) {
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + file.getPath() + " does not exist."))));
                    }
                    return this.addDataMapXML(this.addDataXML(this.readTypedFile(file), file.getName()));
                }
                StringBuffer res = new StringBuffer();
                File[] fileArray = files;
                int n = 0;
                int n3 = fileArray.length;
                while (n < n3) {
                    File cf = fileArray[n];
                    if (!cf.exists()) {
                        res.append(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "File " + cf.getPath() + " does not exist."))));
                    } else {
                        res.append(this.addDataXML(this.readTypedFile(cf), cf.getName()));
                    }
                    ++n;
                }
                return this.addDataMapXML(res);
            }
            case APPEND: {
                if (!file.exists()) {
                    if (file.createNewFile()) break;
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to create file " + file.getPath() + " to append."))));
                }
                if (stpre == null || stpre.trim().length() == 0) {
                    stpre = "";
                }
                if (!this.writeFile(file, stpre, true)) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to append to file " + file.getPath() + "."))));
                }
                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("SUCCESS", ""))));
            }
        }
        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to recognize operation for file " + file.getPath() + "."))));
    }

    public StringBuffer go(IWRequest request) throws Exception {
        this.iwRequest = request;
        this.iwRequest.lnkIWServices.logConsole("IWFileBaseAdaptor.go started: ", IWServices.LOG_TRACE, this.iwRequest);
        File file = null;
        File[] files = null;
        if (this.fileCommand == FileCommand.READ || this.fileCommand == FileCommand.DELETE) {
            int pi = this.httpURL.indexOf("*.");
            if (pi >= 0) {
                File pd = null;
                int ls = this.httpURL.lastIndexOf(File.separator);
                pd = ls < 0 ? File.listRoots()[0] : new File(this.httpURL.substring(0, ls));
                if (!pd.isDirectory()) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FAILURE", "Unable to find parent directory " + this.httpURL))));
                }
                File[] fls = pd.listFiles();
                ArrayList<File> ff = new ArrayList<File>();
                String ext = this.httpURL.substring(pi + 2);
                File[] fileArray = fls;
                int n = 0;
                int n2 = fileArray.length;
                while (n < n2) {
                    File cf = fileArray[n];
                    if (cf.getName().endsWith(ext)) {
                        ff.add(cf);
                    }
                    ++n;
                }
                files = ff.toArray(new File[0]);
            } else {
                file = new File(this.httpURL);
            }
        } else {
            file = new File(this.httpURL);
        }
        this.iwRequest.lnkIWServices.logConsole("IWFileBaseAdaptor.go before return: ", IWServices.LOG_TRACE, this.iwRequest);
        return this.processRequest(this.applyParaneters(this.fileType == FileType.XML, this.fileCommand == FileCommand.READ), file, files, false);
    }

    /*
     * Unable to fully structure code
     */
    private boolean writeFile(File inf, String fileContext, boolean append) {
        switch (IWFileBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileType()[this.fileType.ordinal()]) {
            case 1: 
            case 3: 
            case 4: {
                try {
                    fc = IWServices.readLineContext(fileContext);
                    if (append) {
                        of = this.readLineFile(inf);
                        fnc = new String[of.length + fc.length];
                        System.arraycopy(of, 0, fnc, 0, of.length);
                        System.arraycopy(fc, 0, fnc, of.length, fc.length);
                    } else {
                        fnc = fc;
                    }
                    bw = null;
                    if (!inf.isDirectory()) {
                        bw = new BufferedWriter(new FileWriter(inf));
                    }
                    var10_12 = fnc;
                    var8_13 = 0;
                    var9_14 = var10_12.length;
                    while (var8_13 < var9_14) {
                        fcc = var10_12[var8_13];
                        if (!inf.isDirectory()) ** GOTO lbl-1000
                        if (fcc.startsWith("%%__") && fcc.endsWith("__%%")) {
                            fp = new StringBuffer(inf.getPath());
                            if (!inf.getPath().endsWith(File.separator)) {
                                fp.append(File.separator);
                            }
                            if (bw != null) {
                                bw.close();
                            }
                            bw = new BufferedWriter(new FileWriter(new File(fp + fcc.substring(4, fcc.length() - 4))));
                        } else if (bw == null) {
                            if (fcc.trim().length() != 0) {
                                return false;
                            }
                        } else lbl-1000:
                        // 2 sources

                        {
                            bw.write(fcc);
                            bw.newLine();
                        }
                        ++var8_13;
                    }
                    bw.close();
                    break;
                }
                catch (IOException e) {
                    this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to update file", IWServices.LOG_TRACE, e, null);
                    return false;
                }
            }
            default: {
                outs = fileContext.getBytes();
                try {
                    fr = new FileOutputStream(inf, append);
                    fr.write(outs);
                    fr.close();
                    break;
                }
                catch (FileNotFoundException e) {
                    this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to write file", IWServices.LOG_TRACE, e, null);
                    return false;
                }
                catch (IOException e) {
                    this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to write file", IWServices.LOG_TRACE, e, null);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean updateTypedFile(File inf, String fileContext, boolean update) {
        String[] fc = null;
        String[] nc = null;
        String fct = null;
        String inpFilePath = inf.getPath();
        switch (this.fileType) {
            case TXTL: 
            case CSV: 
            case CSVH: {
                fc = this.readLineFile(inf);
                try {
                    nc = IWServices.readLineContext(fileContext);
                    break;
                }
                catch (IOException e) {
                    this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to read file context", IWServices.LOG_ERRORS, e, null);
                    return false;
                }
            }
            case TXT: {
                fct = this.readFile(inf);
                break;
            }
            case NVP: 
            case XML: {
                this.iwRequest.lnkIWServices.logConsole("IWXMLHierarchicalAdaptor: File type is not applicable", IWServices.LOG_ERRORS, this.iwRequest);
                return false;
            }
        }
        int dp = inpFilePath.lastIndexOf(".");
        String bfn = String.valueOf(dp < 0 ? inpFilePath : inpFilePath.substring(0, dp)) + ".bak";
        File bf = new File(bfn);
        if (bf.exists() && !bf.delete()) {
            this.iwRequest.lnkIWServices.logConsole("IWXMLHierarchicalAdaptor: Unable to delete previous back-up.", IWServices.LOG_ERRORS, this.iwRequest);
            return false;
        }
        if (inf.renameTo(bf)) {
            this.iwRequest.lnkIWServices.logConsole("IWXMLHierarchicalAdaptor: Unable to create back-up.", IWServices.LOG_ERRORS, this.iwRequest);
            return false;
        }
        File nf = new File(inpFilePath);
        if (nf.exists()) {
            this.iwRequest.lnkIWServices.logConsole("IWXMLHierarchicalAdaptor: Unable to create a new copy.", IWServices.LOG_ERRORS, this.iwRequest);
            return false;
        }
        if (this.fileType == FileType.TXT) {
            int fctl = fct.length();
            int fb = fctl > this.startPosition ? this.startPosition : fctl;
            StringBuffer nfc = new StringBuffer(fct.substring(0, fb));
            if (fctl < this.startPosition) {
                nfc.setLength(this.startPosition);
                int i = fctl;
                while (i < this.startPosition) {
                    nfc.setCharAt(i, ' ');
                    ++i;
                }
            }
            nfc.append(fileContext);
            if (update) {
                if (this.endPosition < fctl) {
                    nfc.append(fct.substring(this.endPosition, fctl));
                }
            } else if (fctl > this.startPosition) {
                nfc.append(fct.substring(this.startPosition));
            }
            return this.writeFile(nf, nfc.toString(), false);
        }
        if (update) {
            return this.modifyUpdate(nf, fc, nc);
        }
        return this.modifyInsert(nf, fc, nc);
    }

    private boolean modifyUpdate(File nf, String[] fc, String[] nc) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(nf));
            int i = 0;
            while (i < this.startPosition) {
                if (i < fc.length) {
                    bw.write(fc[i]);
                }
                bw.newLine();
                ++i;
            }
            String[] stringArray = nc;
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cnl = stringArray[n];
                bw.write(cnl);
                bw.newLine();
                ++n;
            }
            if (this.endPosition > 0) {
                i = this.endPosition;
                while (i < fc.length) {
                    bw.write(fc[i]);
                    bw.newLine();
                    ++i;
                }
            }
            bw.close();
        }
        catch (IOException e) {
            this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to update file", IWServices.LOG_TRACE, e, null);
            return false;
        }
        return true;
    }

    private boolean modifyInsert(File nf, String[] fc, String[] nc) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(nf));
            int i = 0;
            while (i < this.startPosition) {
                if (i < fc.length) {
                    bw.write(fc[i]);
                }
                bw.newLine();
                ++i;
            }
            String[] stringArray = nc;
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                String cnl = stringArray[n];
                bw.write(cnl);
                bw.newLine();
                ++n;
            }
            i = this.startPosition;
            while (i < fc.length) {
                bw.write(fc[i]);
                bw.newLine();
                ++i;
            }
            bw.close();
        }
        catch (IOException e) {
            this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to update file", IWServices.LOG_TRACE, e, null);
            return false;
        }
        return true;
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private StringBuffer readTypedFile(File inf) {
        this.iwRequest.lnkIWServices.logConsole("readTypedFile started: " + inf.getName(), IWServices.LOG_TRACE, this.iwRequest);
        fc = null;
        res = new StringBuffer("");
        ep = -1;
        switch (IWFileBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileType()[this.fileType.ordinal()]) {
            case 1: 
            case 3: 
            case 4: {
                fc = this.readLineFile(inf);
                v0 = ep = this.endPosition < 0 ? fc.length : this.endPosition;
                if (this.endPosition <= 0 || ep < fc.length || !this.stepThrough) break;
                this.iwRequest.setStopSchedule(1);
                ep = fc.length;
            }
        }
        bp = this.startPosition < 0 ? 0 : this.startPosition;
        switch (IWFileBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileType()[this.fileType.ordinal()]) {
            case 0: {
                txt = this.readFile(inf);
                if (txt.startsWith("%_00_%")) {
                    txt = txt.substring(6);
                    ots = txt.getBytes();
                    i0 = 0;
                    while (i0 < ots.length) {
                        ots[i0] = (byte)(255 - (ots[i0] & 255));
                        ++i0;
                    }
                    txt = new String(ots, 0, ots.length);
                }
                v1 = ep = this.endPosition < 0 ? txt.length() : this.endPosition;
                if (this.endPosition > 0 && ep >= txt.length() && this.stepThrough) {
                    this.iwRequest.setStopSchedule(1);
                    ep = txt.length();
                }
                strdata = txt.substring(bp, ep);
                try {
                    strdata = IWServices.processParameters(this.iwRequest, strdata, inf.getName(), this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                }
                catch (Exception e1) {
                    this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to process parameters", IWServices.LOG_TRACE, e1, null);
                    return res;
                }
                return new StringBuffer(this.oneColOneRow(inf.getName(), strdata));
            }
            case 1: {
                i = bp;
                while (i < ep) {
                    resPre = fc[i];
                    if (resPre.startsWith("%_00_%")) {
                        resPre = resPre.substring(6);
                        ots = resPre.getBytes();
                        i0 = 0;
                        while (i0 < ots.length) {
                            ots[i0] = (byte)(255 - (ots[i0] & 255));
                            ++i0;
                        }
                        resPre = new String(ots, 0, ots.length);
                    }
                    try {
                        strdata = IWServices.processParameters(this.iwRequest, resPre, inf.getName(), this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                        res.append(this.oneColOneRow(inf.getName(), strdata));
                    }
                    catch (Exception e1) {
                        this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to process parameters", IWServices.LOG_TRACE, e1, null);
                        return res;
                    }
                    ++i;
                }
                break;
            }
            case 2: {
                nvp = new Properties();
                try {
                    nvp.load(new FileInputStream(inf));
                }
                catch (FileNotFoundException e) {
                    this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to load name-valuue pairs", IWServices.LOG_TRACE, e, null);
                    return res;
                }
                catch (IOException e) {
                    this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to load name-valuue pairs", IWServices.LOG_TRACE, e, null);
                    return res;
                }
                for (String kv : nvp.keySet()) {
                    kvn = nvp.getProperty(kv);
                    try {
                        kvn = IWServices.processParameters(this.iwRequest, kvn, kv, this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                    }
                    catch (Exception e1) {
                        this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to process parameters", IWServices.LOG_TRACE, e1, null);
                        return res;
                    }
                    res.append(this.oneColOneRow(kv, kvn));
                }
                break;
            }
            case 3: {
                i = bp;
                while (i < ep) {
                    if (!(fc[i].trim().length() == 0 || this.csvFilter1 != null && fc[i].indexOf(this.csvFilter1) >= 0 || this.csvFilter2 != null && fc[i].indexOf(this.csvFilter2) >= 0)) {
                        rv = fc[i].split(this.separator);
                        rb = new StringBuffer();
                        i1 = 0;
                        while (i1 < rv.length) {
                            try {
                                strdata = IWServices.processParameters(this.iwRequest, rv[i1], inf.getName(), this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                            }
                            catch (Exception e1) {
                                this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to process parameters", IWServices.LOG_TRACE, e1, null);
                                return res;
                            }
                            rb.append(this.oneColBase(inf.getName(), strdata, i1));
                            ++i1;
                        }
                        res.append(this.addRowXML(rb));
                    }
                    ++i;
                }
                break;
            }
            case 4: {
                hd = fc[this.headPosition].split(this.separator);
                if (this.headPosition2 > 0) {
                    hd2 = fc[this.headPosition2].split(this.separator);
                    ih = 0;
                    while (ih < hd.length) {
                        v2 = ih;
                        hd[v2] = String.valueOf(hd[v2]) + hd2[ih];
                        ++ih;
                    }
                }
                this.iwRequest.lnkIWServices.logConsole("readTypedFile bp: " + bp + " ep: " + ep, IWServices.LOG_TRACE, this.iwRequest);
                j = bp;
                while (j < ep) {
                    if (fc[j].trim().length() == 0 || j == this.headPosition || this.headPosition2 > 0 && j == this.headPosition2 || this.csvFilter1 != null && fc[j].indexOf(this.csvFilter1) < 0 || this.csvFilter2 != null && fc[j].indexOf(this.csvFilter2) < 0) ** GOTO lbl176
                    rv0 = fc[j].split(this.separator);
                    rv = new String[hd.length];
                    i = 0;
                    rt = new StringBuffer("");
                    while (true) {
                        k = 0;
                        while (i < hd.length) {
                            if (k >= rv0.length) ** GOTO lbl146
                            if (!rv0[k].startsWith("\"") && rt.length() <= 0) ** GOTO lbl144
                            if (!rv0[k].endsWith("\"")) ** GOTO lbl139
                            if (rt.length() > 0) {
                                rv[i] = String.valueOf(rt.toString()) + rv0[k++];
                                rt = new StringBuffer("");
                            } else {
                                rv[i] = rv0[k++];
                            }
                            ** GOTO lbl147
lbl-1000:
                            // 1 sources

                            {
                                rt.append(String.valueOf(rv0[k++]) + (k < rv0.length - 1 || fc[j].endsWith(this.separator) != false ? this.separator : ""));
lbl139:
                                // 2 sources

                                ** while (k < rv0.length && !rv0[k].endsWith((String)"\""))
                            }
lbl140:
                            // 1 sources

                            if (k >= rv0.length) break;
                            rv[i] = String.valueOf(rt.toString()) + rv0[k++];
                            rt = new StringBuffer("");
                            ** GOTO lbl147
lbl144:
                            // 1 sources

                            rv[i] = rv0[k++];
                            ** GOTO lbl147
lbl146:
                            // 1 sources

                            rv[i] = "";
lbl147:
                            // 5 sources

                            ++i;
                        }
                        if (i >= hd.length) break;
                        rt.append("\n");
                        if (++j >= ep) {
                            rv[i] = rt.toString();
                            break;
                        }
                        rv0 = fc[j].split(this.separator);
                    }
                    rb = new StringBuffer();
                    i = 0;
                    while (i < hd.length) {
                        if (i < rv.length && rv[i] != null) {
                            try {
                                strdata = IWServices.processParameters(this.iwRequest, rv[i], hd[i], this.returnName, this.replaceQuoteName, this.replaceQuote, this.replace2QuoteName, this.replace2Quote, this.retFormat);
                            }
                            catch (Exception e1) {
                                this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to process parameters", IWServices.LOG_TRACE, e1, null);
                                return res;
                            }
                            rb.append(this.oneColBase(hd[i], strdata, i));
                        } else {
                            rb.append(this.oneColBase(hd[i], "", i));
                        }
                        ++i;
                    }
                    res.append(this.addRowXML(rb));
lbl176:
                    // 2 sources

                    ++j;
                }
                break;
            }
            case 7: {
                try {
                    res.append(this.xml2DataMap(this.readFile(inf), this.iwRequest, true));
                    break;
                }
                catch (Exception e) {
                    this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to read XML", IWServices.LOG_TRACE, e, null);
                    return res;
                }
            }
        }
        return res;
    }

    public void closeConnection() {
    }

    protected String[] readLineFile(File inf) {
        FileReader fr = null;
        try {
            fr = new FileReader(inf);
        }
        catch (FileNotFoundException e) {
            this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: File does not exist", IWServices.LOG_TRACE, e, null);
            return new String[]{""};
        }
        this.iwRequest.lnkIWServices.logConsole("readLineFile started: " + inf.getName(), IWServices.LOG_TRACE, this.iwRequest);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> sb = new ArrayList<String>();
        String is = "";
        try {
            while ((is = br.readLine()) != null) {
                this.iwRequest.lnkIWServices.logConsole("readLineFile line: " + is, IWServices.LOG_TRACE, this.iwRequest);
                sb.add(is);
            }
        }
        catch (IOException e) {
            this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to read File", IWServices.LOG_TRACE, e, null);
            return new String[]{""};
        }
        return sb.toArray(new String[0]);
    }

    private String readFile(File inf) {
        if (!inf.exists()) {
            this.iwRequest.lnkIWServices.logConsole("IWXMLHierarchicalAdaptor: File does not exist", IWServices.LOG_ERRORS, this.iwRequest);
            return "";
        }
        int fl = (int)inf.length();
        this.iwRequest.lnkIWServices.logConsole("Read binary file. Length= " + fl, IWServices.LOG_TRACE, this.iwRequest);
        if (fl == 0) {
            return "";
        }
        byte[] outs = new byte[fl];
        int osz = 0;
        try {
            FileInputStream fr = new FileInputStream(inf);
            osz = fr.read(outs);
            this.iwRequest.lnkIWServices.logConsole("Read binary file. Bytes= " + osz, IWServices.LOG_TRACE, this.iwRequest);
        }
        catch (FileNotFoundException e) {
            this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to read file", IWServices.LOG_TRACE, e, null);
            return "";
        }
        catch (IOException e) {
            this.iwRequest.lnkIWServices.logError("IWXMLHierarchicalAdaptor: Unable to read file", IWServices.LOG_TRACE, e, null);
            return "";
        }
        return new String(outs);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum FileType {
        TXT("TXT"),
        TXTL("TXTL"),
        NVP("NVP"),
        CSV("CSV"),
        CSVH("CSVH"),
        TSV("TSV"),
        TSVH("TSVH"),
        XML("XML");

        private final String label;

        private FileType(String value) {
            this.label = value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum FileCommand {
        READ("READ"),
        WRITE("WRITE"),
        OVERWRITE("OVERWRITE"),
        UPDATE("UPDATE"),
        INSERT("INSERT"),
        APPEND("APPEND"),
        RENAME("RENAME"),
        MOVE("MOVE"),
        DELETE("DELETE");

        private final String label;

        private FileCommand(String value) {
            this.label = value;
        }

        public String getLabel() {
            return this.label;
        }
    }
}

