/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.filesystem;

import com.interweave.adapter.filesystem.IWFileBaseAdaptor;
import com.interweave.bindings.Datamap;
import com.interweave.core.IWRequest;
import com.interweave.core.IWServices;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

public class IWFtpBaseAdaptor
extends IWFileBaseAdaptor {
    protected FTPClient ftpClient;
    protected FTPSClient ftpsClient;
    protected Session sftpSession;
    protected String filePath;
    protected String pattern;
    protected String filter;
    protected boolean writeEmptyFile;
    protected boolean loadUpperNameOnly;
    protected int ftpActivePassiveMode;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand;

    public void closeConnection() {
        super.closeConnection();
        if (this.ftpClient != null) {
            try {
                if (!this.ftpClient.logout()) {
                    this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: FTP Unable to logout", IWServices.LOG_ERRORS, this.iwRequest);
                }
                this.ftpClient.disconnect();
            }
            catch (IOException e) {
                this.iwRequest.lnkIWServices.logError("IWFtpBaseAdaptor: FTP Unable to disconnect", IWServices.LOG_TRACE, e, this.iwRequest);
            }
        } else if (this.ftpsClient != null) {
            try {
                if (!this.ftpsClient.logout()) {
                    this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: FTPS Unable to logout", IWServices.LOG_ERRORS, this.iwRequest);
                }
                this.ftpsClient.disconnect();
            }
            catch (IOException e) {
                this.iwRequest.lnkIWServices.logError("IWFtpBaseAdaptor: FTPS Unable to disconnect", IWServices.LOG_TRACE, e, this.iwRequest);
            }
        } else if (this.sftpSession != null) {
            this.sftpSession.disconnect();
        }
    }

    /*
     * Unable to fully structure code
     */
    public StringBuffer go(IWRequest request) throws Exception {
        block111: {
            block112: {
                block110: {
                    this.iwRequest = request;
                    file = null;
                    remoteFile = new File(this.filePath);
                    remoteFileName = remoteFile.getName();
                    remoteDir = remoteFile.getParent().replace('\\', '/');
                    this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Processing started with File Command " + (Object)this.fileCommand + " and File Type " + (Object)this.fileType + " and File Path " + this.filePath + " and File " + remoteFileName + " and Path " + remoteDir, IWServices.LOG_TRACE, this.iwRequest);
                    files = null;
                    stpre = null;
                    pi = this.filePath.endsWith("/");
                    if (pi && this.fileCommand != IWFileBaseAdaptor.FileCommand.READ && this.fileCommand != IWFileBaseAdaptor.FileCommand.DELETE && this.fileCommand != IWFileBaseAdaptor.FileCommand.RENAME && this.fileCommand != IWFileBaseAdaptor.FileCommand.MOVE) {
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Group operations are not supported for this command: " + this.filePath))));
                    }
                    ret = new StringBuffer("");
                    if (this.ftpClient == null) break block110;
                    if (remoteDir != null && !pi && !this.ftpClient.changeWorkingDirectory(remoteDir)) {
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to change remote working directory to: " + remoteDir + " for filepath " + this.filePath))));
                    }
                    switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                        case 8: {
                            if (pi) {
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: List Files for " + this.filePath + this.pattern, IWServices.LOG_TRACE, this.iwRequest);
                                ftpFiles = this.ftpClient.listFiles(String.valueOf(this.filePath) + this.pattern);
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Files are Listed: " + this.filePath + this.pattern + "; Found " + (ftpFiles == null ? "null" : Integer.valueOf(ftpFiles.length)) + " RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_TRACE, this.iwRequest);
                                var14_17 = ftpFiles;
                                var12_24 = 0;
                                var13_32 = var14_17.length;
                                while (var12_24 < var13_32) {
                                    cftf = var14_17[var12_24];
                                    if (cftf != null && cftf.isFile()) {
                                        fn = cftf.getName();
                                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File found: " + fn, IWServices.LOG_TRACE, this.iwRequest);
                                        if (!this.ftpClient.deleteFile(String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn)) {
                                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during deletion " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn + " RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to delete file " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn))));
                                        }
                                    }
                                    ++var12_24;
                                }
                                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP SUCCESS", this.filePath))));
                            }
                            if (!this.ftpClient.deleteFile(remoteFileName)) {
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during deletion RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to delete file " + this.filePath))));
                            }
                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP SUCCESS", this.filePath))));
                        }
                        case 0: 
                        case 1: 
                        case 2: 
                        case 3: 
                        case 4: 
                        case 5: 
                        case 6: 
                        case 7: {
                            stpre = this.applyParaneters(this.fileType == IWFileBaseAdaptor.FileType.XML, this.fileCommand == IWFileBaseAdaptor.FileCommand.READ);
                            break;
                        }
                        default: {
                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to recognize operation for file " + this.filePath + "."))));
                        }
                    }
                    switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                        case 6: 
                        case 7: {
                            if (pi) {
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: List Files " + this.filePath + this.pattern, IWServices.LOG_TRACE, this.iwRequest);
                                ftpFiles = this.ftpClient.listFiles(String.valueOf(this.filePath) + this.pattern);
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Files are Listed: " + this.filePath + this.pattern + "; Found " + (ftpFiles == null ? "null" : Integer.valueOf(ftpFiles.length)) + " RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_TRACE, this.iwRequest);
                                var14_18 = ftpFiles;
                                var12_25 = 0;
                                var13_33 = var14_18.length;
                                while (var12_25 < var13_33) {
                                    cftf = var14_18[var12_25];
                                    if (cftf != null && cftf.isFile()) {
                                        fn = cftf.getName();
                                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File found: " + fn, IWServices.LOG_TRACE, this.iwRequest);
                                        fp = (String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn).split("/");
                                        if (!this.ftpClient.rename(String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn, String.valueOf(stpre) + fp[fp.length - 1])) {
                                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during moving/renaming " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn + " to " + stpre + fp[fp.length - 1] + " RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to move/rename file " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn))));
                                        }
                                    }
                                    ++var12_25;
                                }
                                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP SUCCESS", this.filePath))));
                            }
                            if (!this.ftpClient.rename(remoteFileName, stpre)) {
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during move/rename RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to move/rename file " + this.filePath))));
                            }
                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP SUCCESS", this.filePath))));
                        }
                    }
                    file = File.createTempFile("IWFTP", null);
                    file.deleteOnExit();
                    switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                        case 3: {
                            if (this.startPosition < 0) break;
                        }
                        case 0: 
                        case 4: {
                            if (!pi) ** GOTO lbl146
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: List Files", IWServices.LOG_TRACE, this.iwRequest);
                            ftpFiles = this.ftpClient.listFiles(String.valueOf(this.filePath) + this.pattern);
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Files are Listed: " + this.filePath + this.pattern + "; Found " + (ftpFiles == null ? "null" : Integer.valueOf(ftpFiles.length)) + " RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_TRACE, this.iwRequest);
                            ff = new ArrayList<File>();
                            if (this.startFilePosition >= ftpFiles.length) {
                                if (this.stepThrough) {
                                    this.iwRequest.setStopSchedule(1);
                                }
                                return new StringBuffer("");
                            }
                            filterArray = null;
                            if (this.filter != null) {
                                i = 0;
                                while (i < ftpFiles.length) {
                                    cftf = ftpFiles[i];
                                    if (cftf != null && cftf.isFile() && (fn = cftf.getName()).equals(this.filter)) {
                                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Filter is found : " + fn, IWServices.LOG_REQUEST, this.iwRequest);
                                        fc = File.createTempFile("IWFTP", null);
                                        fc.deleteOnExit();
                                        fo = new FileOutputStream(fc);
                                        res = this.ftpClient.retrieveFile(String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn, (OutputStream)fo);
                                        fo.close();
                                        if (!res) {
                                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during reading filter " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn + " RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                            break;
                                        }
                                        filterArray = this.readLineFile(fc);
                                        break;
                                    }
                                    ++i;
                                }
                            }
                            i = 0;
                            while (i < ftpFiles.length) {
                                if (this.startFilePosition < 0 || this.endFilePosition <= 0) ** GOTO lbl110
                                if (i >= this.startFilePosition) {
                                    if (i >= this.endFilePosition) break;
                                }
                                ** GOTO lbl141
lbl110:
                                // 2 sources

                                if ((cftf = ftpFiles[i]) == null || !cftf.isFile()) ** GOTO lbl141
                                fn = cftf.getName();
                                if (this.filter == null) ** GOTO lbl125
                                if (fn.equals(this.filter) || filterArray == null || filterArray.length == 0) ** GOTO lbl141
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File to filter: " + fn, IWServices.LOG_REQUEST, this.iwRequest);
                                rfn = fn;
                                ptrfn = fn.indexOf(".");
                                if (ptrfn > 0) {
                                    rfn = fn.substring(0, ptrfn);
                                }
                                fi = 0;
                                while (fi < filterArray.length) {
                                    if (filterArray[fi].trim().equals(fn) || filterArray[fi].trim().equals(rfn)) break;
                                    ++fi;
                                }
                                if (fi >= filterArray.length) ** GOTO lbl141
lbl125:
                                // 2 sources

                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File found: " + fn, this.filter != null ? IWServices.LOG_REQUEST : IWServices.LOG_TRACE, this.iwRequest);
                                if (this.dynamicFileName != null && !fn.startsWith(this.dynamicFileName)) ** GOTO lbl141
                                if (!this.loadUpperNameOnly || this.dynamicFileName != null) ** GOTO lbl131
                                pps = fn.indexOf(".");
                                v0 = fnn = pps > 0 ? fn.substring(0, pps) : fn;
                                if (!fnn.toUpperCase().equals(fnn)) ** GOTO lbl141
lbl131:
                                // 2 sources

                                fc = File.createTempFile("IWFTP", null);
                                fc.deleteOnExit();
                                fo = new FileOutputStream(fc);
                                res = this.ftpClient.retrieveFile(String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn, (OutputStream)fo);
                                fo.close();
                                if (!res) {
                                    this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during reading " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn + " RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to retrieve file " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn))));
                                }
                                ff.add(fc);
lbl141:
                                // 7 sources

                                ++i;
                            }
                            files = ff.toArray(new File[0]);
                            file = null;
                            break;
lbl146:
                            // 1 sources

                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File Reading started with File Path " + this.filePath + " to " + file.getName(), IWServices.LOG_TRACE, this.iwRequest);
                            files = null;
                            fo = new FileOutputStream(file);
                            res = this.ftpClient.retrieveFile(remoteFileName, (OutputStream)fo);
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File Reading ended with result " + res, IWServices.LOG_TRACE, this.iwRequest);
                            fo.close();
                            if (res) break;
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during reading RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to retrieve file " + this.filePath))));
                        }
                    }
                    ret = this.processRequest(stpre, file, files, true);
                    switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                        case 1: 
                        case 2: 
                        case 3: 
                        case 4: 
                        case 5: {
                            if (ret.indexOf("FAILURE") < 0) break;
                            return ret;
                        }
                    }
                    if (this.fileCommand != IWFileBaseAdaptor.FileCommand.READ && this.fileCommand != IWFileBaseAdaptor.FileCommand.DELETE) {
                        fi = new FileInputStream(file);
                        switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                            case 3: 
                            case 4: {
                                if (!this.ftpClient.deleteFile(remoteFileName)) {
                                    this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during deleting RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                    ret = this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to delete file " + this.filePath + " to update."))));
                                    break;
                                }
                            }
                            case 2: {
                                this.ftpClient.deleteFile(remoteFileName);
                            }
                            case 1: {
                                if (this.fileCommand != IWFileBaseAdaptor.FileCommand.OVERWRITE && file.length() <= 0L && !this.writeEmptyFile || this.ftpClient.storeFile(remoteFileName, (InputStream)fi)) break;
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during storing RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                ret = this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to store file " + this.filePath))));
                                break;
                            }
                            case 5: {
                                if (this.ftpClient.appendFile(remoteFileName, (InputStream)fi)) break;
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during appending RC= " + this.ftpClient.getReplyCode() + " R= " + this.ftpClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                ret = this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to append file " + this.filePath))));
                            }
                        }
                        fi.close();
                    }
                    break block111;
                }
                if (this.ftpsClient == null) break block112;
                if (remoteDir != null && !pi && !this.ftpsClient.changeWorkingDirectory(remoteDir)) {
                    return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to change remote working directory to: " + remoteDir + " for filepath " + this.filePath))));
                }
                switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                    case 8: {
                        if (pi) {
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: List Files for " + this.filePath + this.pattern, IWServices.LOG_TRACE, this.iwRequest);
                            ftpFiles = this.ftpsClient.listFiles(String.valueOf(this.filePath) + this.pattern);
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Files are Listed: " + this.filePath + this.pattern + "; Found " + (ftpFiles == null ? "null" : Integer.valueOf(ftpFiles.length)) + " RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_TRACE, this.iwRequest);
                            cftf = ftpFiles;
                            filterArray = 0;
                            i = cftf.length;
                            while (filterArray < i) {
                                cftf = cftf[filterArray];
                                if (cftf != null && cftf.isFile()) {
                                    fn = cftf.getName();
                                    this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File found: " + fn, IWServices.LOG_TRACE, this.iwRequest);
                                    if (!this.ftpsClient.deleteFile(String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn)) {
                                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during deletion " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn + " RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to delete file " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn))));
                                    }
                                }
                                ++filterArray;
                            }
                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP SUCCESS", this.filePath))));
                        }
                        if (!this.ftpsClient.deleteFile(remoteFileName)) {
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during deletion RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to delete file " + this.filePath))));
                        }
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP SUCCESS", this.filePath))));
                    }
                    case 0: 
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: 
                    case 7: {
                        stpre = this.applyParaneters(this.fileType == IWFileBaseAdaptor.FileType.XML, this.fileCommand == IWFileBaseAdaptor.FileCommand.READ);
                        break;
                    }
                    default: {
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to recognize operation for file " + this.filePath + "."))));
                    }
                }
                switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                    case 6: 
                    case 7: {
                        if (pi) {
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: List Files " + this.filePath + this.pattern, IWServices.LOG_TRACE, this.iwRequest);
                            ftpFiles = this.ftpsClient.listFiles(String.valueOf(this.filePath) + this.pattern);
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Files are Listed: " + this.filePath + this.pattern + "; Found " + (ftpFiles == null ? "null" : Integer.valueOf(ftpFiles.length)) + " RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_TRACE, this.iwRequest);
                            cftf = ftpFiles;
                            filterArray = 0;
                            i = cftf.length;
                            while (filterArray < i) {
                                cftf = cftf[filterArray];
                                if (cftf != null && cftf.isFile()) {
                                    fn = cftf.getName();
                                    this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File found: " + fn, IWServices.LOG_TRACE, this.iwRequest);
                                    fp = (String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn).split("/");
                                    if (!this.ftpsClient.rename(String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn, String.valueOf(stpre) + fp[fp.length - 1])) {
                                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during moving/renaming " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn + " to " + stpre + fp[fp.length - 1] + " RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to move/rename file " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn))));
                                    }
                                }
                                ++filterArray;
                            }
                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP SUCCESS", this.filePath))));
                        }
                        if (!this.ftpsClient.rename(remoteFileName, stpre)) {
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during move/rename RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                            return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to move/rename file " + this.filePath))));
                        }
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP SUCCESS", this.filePath))));
                    }
                }
                file = File.createTempFile("IWFTP", null);
                file.deleteOnExit();
                switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                    case 3: {
                        if (this.startPosition < 0) break;
                    }
                    case 0: 
                    case 4: {
                        if (!pi) ** GOTO lbl316
                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: List Files", IWServices.LOG_TRACE, this.iwRequest);
                        ftpFiles = this.ftpsClient.listFiles(String.valueOf(this.filePath) + this.pattern);
                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Files are Listed: " + this.filePath + this.pattern + "; Found " + (ftpFiles == null ? "null" : Integer.valueOf(ftpFiles.length)) + " RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_TRACE, this.iwRequest);
                        ff = new ArrayList<File>();
                        if (this.startFilePosition >= ftpFiles.length) {
                            if (this.stepThrough) {
                                this.iwRequest.setStopSchedule(1);
                            }
                            return new StringBuffer("");
                        }
                        filterArray = null;
                        if (this.filter != null) {
                            i = 0;
                            while (i < ftpFiles.length) {
                                cftf = ftpFiles[i];
                                if (cftf != null && cftf.isFile() && (fn = cftf.getName()).equals(this.filter)) {
                                    this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Filter is found : " + fn, IWServices.LOG_REQUEST, this.iwRequest);
                                    fc = File.createTempFile("IWFTP", null);
                                    fc.deleteOnExit();
                                    fo = new FileOutputStream(fc);
                                    res = this.ftpsClient.retrieveFile(String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn, (OutputStream)fo);
                                    fo.close();
                                    if (!res) {
                                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during reading filter " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn + " RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                        break;
                                    }
                                    filterArray = this.readLineFile(fc);
                                    break;
                                }
                                ++i;
                            }
                        }
                        i = 0;
                        while (i < ftpFiles.length) {
                            if (this.startFilePosition < 0 || this.endFilePosition <= 0) ** GOTO lbl280
                            if (i >= this.startFilePosition) {
                                if (i >= this.endFilePosition) break;
                            }
                            ** GOTO lbl311
lbl280:
                            // 2 sources

                            if ((cftf = ftpFiles[i]) == null || !cftf.isFile()) ** GOTO lbl311
                            fn = cftf.getName();
                            if (this.filter == null) ** GOTO lbl295
                            if (fn.equals(this.filter) || filterArray == null || filterArray.length == 0) ** GOTO lbl311
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File to filter: " + fn, IWServices.LOG_REQUEST, this.iwRequest);
                            rfn = fn;
                            ptrfn = fn.indexOf(".");
                            if (ptrfn > 0) {
                                rfn = fn.substring(0, ptrfn);
                            }
                            fi = 0;
                            while (fi < filterArray.length) {
                                if (filterArray[fi].trim().equals(fn) || filterArray[fi].trim().equals(rfn)) break;
                                ++fi;
                            }
                            if (fi >= filterArray.length) ** GOTO lbl311
lbl295:
                            // 2 sources

                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File found: " + fn, this.filter != null ? IWServices.LOG_REQUEST : IWServices.LOG_TRACE, this.iwRequest);
                            if (this.dynamicFileName != null && !fn.startsWith(this.dynamicFileName)) ** GOTO lbl311
                            if (!this.loadUpperNameOnly || this.dynamicFileName != null) ** GOTO lbl301
                            pps = fn.indexOf(".");
                            v1 = fnn = pps > 0 ? fn.substring(0, pps) : fn;
                            if (!fnn.toUpperCase().equals(fnn)) ** GOTO lbl311
lbl301:
                            // 2 sources

                            fc = File.createTempFile("IWFTP", null);
                            fc.deleteOnExit();
                            fo = new FileOutputStream(fc);
                            res = this.ftpsClient.retrieveFile(String.valueOf(fn.indexOf("/") >= 0 ? "" : this.filePath) + fn, (OutputStream)fo);
                            fo.close();
                            if (!res) {
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during reading " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn + " RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to retrieve file " + (fn.indexOf("/") >= 0 ? "" : this.filePath) + fn))));
                            }
                            ff.add(fc);
lbl311:
                            // 7 sources

                            ++i;
                        }
                        files = ff.toArray(new File[0]);
                        file = null;
                        break;
lbl316:
                        // 1 sources

                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File Reading started with File Path " + this.filePath + " to " + file.getName(), IWServices.LOG_TRACE, this.iwRequest);
                        files = null;
                        fo = new FileOutputStream(file);
                        res = this.ftpsClient.retrieveFile(remoteFileName, (OutputStream)fo);
                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: File Reading ended with result " + res, IWServices.LOG_TRACE, this.iwRequest);
                        fo.close();
                        if (res) break;
                        this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during reading RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                        return this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to retrieve file " + this.filePath))));
                    }
                }
                ret = this.processRequest(stpre, file, files, true);
                this.iwRequest.lnkIWServices.logConsole("processRequest ended ", IWServices.LOG_TRACE, this.iwRequest);
                switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: {
                        if (ret.indexOf("FAILURE") < 0) break;
                        return ret;
                    }
                }
                if (this.fileCommand != IWFileBaseAdaptor.FileCommand.READ && this.fileCommand != IWFileBaseAdaptor.FileCommand.DELETE) {
                    fi = new FileInputStream(file);
                    switch (IWFtpBaseAdaptor.$SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand()[this.fileCommand.ordinal()]) {
                        case 3: 
                        case 4: {
                            if (!this.ftpsClient.deleteFile(remoteFileName)) {
                                this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during deleting RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                                ret = this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to delete file " + this.filePath + " to update."))));
                                break;
                            }
                        }
                        case 2: {
                            this.ftpsClient.deleteFile(remoteFileName);
                        }
                        case 1: {
                            if (this.fileCommand != IWFileBaseAdaptor.FileCommand.OVERWRITE && file.length() <= 0L && !this.writeEmptyFile) break;
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Storing File " + remoteFileName, IWServices.LOG_ERRORS, this.iwRequest);
                            if (this.ftpsClient.storeFile(remoteFileName, (InputStream)fi)) break;
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during storing RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                            ret = this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to store file " + this.filePath))));
                            break;
                        }
                        case 5: {
                            if (this.ftpsClient.appendFile(remoteFileName, (InputStream)fi)) break;
                            this.iwRequest.lnkIWServices.logConsole("IWFtpBaseAdaptor: Error during appending RC= " + this.ftpsClient.getReplyCode() + " R= " + this.ftpsClient.getReplyString(), IWServices.LOG_ERRORS, this.iwRequest);
                            ret = this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("FTP FAILURE", "Unable to append file " + this.filePath))));
                        }
                    }
                    fi.close();
                }
                break block111;
            }
            if (this.sftpSession != null) {
                channel = null;
                try {
                    channel = this.sftpSession.openChannel("sftp");
                    request.lnkIWServices.logConsole("IWFtpBaseAdaptor: JSCH Channel opened", IWServices.LOG_REQUEST, request);
                }
                catch (JSchException e) {
                    request.lnkIWServices.logError("IWFtpBaseAdaptor: JSCH Opening channel failed", IWServices.LOG_ERRORS, (Exception)e, request);
                    throw e;
                }
                sftpChannel = (ChannelSftp)channel;
                try {
                    sftpChannel.connect();
                    request.lnkIWServices.logConsole("IWFtpBaseAdaptor: JSCH SFTPChannel Connected", IWServices.LOG_REQUEST, request);
                }
                catch (JSchException e) {
                    request.lnkIWServices.logError("IWFtpBaseAdaptor: JSCH Channel Connection failed", IWServices.LOG_ERRORS, (Exception)e, request);
                    throw e;
                }
                in = null;
                try {
                    in = sftpChannel.get(this.filePath);
                    request.lnkIWServices.logConsole("IWFtpBaseAdaptor: JSCH InputStream obtained for " + this.filePath, IWServices.LOG_REQUEST, request);
                }
                catch (SftpException e) {
                    request.lnkIWServices.logError("IWFtpBaseAdaptor: JSCH Get from sftpChannel failed", IWServices.LOG_ERRORS, (Exception)e, request);
                    throw e;
                }
                b = new byte[300];
                ri = in.read(b);
                request.lnkIWServices.logConsole("IWFtpBaseAdaptor: JSCH " + ri + " bytes read", IWServices.LOG_REQUEST, request);
                if (ri >= 0) {
                    res = new String(b, 0, ri);
                    if (res.startsWith("%_00_%")) {
                        res = res.substring(6);
                        ots = res.getBytes();
                        i = 0;
                        while (i < ots.length) {
                            ots[i] = (byte)(255 - (ots[i] & 255));
                            ++i;
                        }
                        res = new String(ots, 0, ots.length);
                    }
                    ret = this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow(this.filePath, res))));
                } else {
                    ret = this.addDataMapXML(this.addDataXML(new StringBuffer(this.oneColOneRow("SFTP FAILURE", "Unable to read result " + this.filePath))));
                }
            }
        }
        return ret;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        URL ftpURL;
        super.setup(map, request);
        this.ftpClient = null;
        this.ftpsClient = null;
        this.sftpSession = null;
        String ftpEncryption = "n";
        String fURL = this.httpURL;
        boolean implicit = false;
        if (fURL.indexOf("ftpsi") >= 0) {
            ftpEncryption = "ftps";
            implicit = true;
            fURL = fURL.replace("ftpsi", "ftp");
        } else if (fURL.indexOf("ftps") >= 0) {
            ftpEncryption = "ftps";
            fURL = fURL.replace("ftps", "ftp");
        } else if (fURL.indexOf("sftp") >= 0) {
            ftpEncryption = "sftp";
            fURL = fURL.replace("sftp", "ftp");
        }
        try {
            ftpURL = new URL(fURL);
        }
        catch (MalformedURLException e1) {
            request.setConnectionError(true);
            request.setConnectionFailures(request.getConnectionFailures() + 1);
            request.setQueryStartTime(request.getInitialQueryStartTime());
            throw e1;
        }
        InetAddress ipaddr = InetAddress.getByName(ftpURL.getHost());
        int port = ftpURL.getPort();
        this.filePath = ftpURL.getPath();
        this.pattern = "*.*";
        this.writeEmptyFile = false;
        this.loadUpperNameOnly = false;
        this.filter = null;
        request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Trying to connect to FTP Server " + ipaddr + (port > 0 ? " Port=" + port : ""), IWServices.LOG_TRACE, request);
        if (ftpEncryption.equals("n")) {
            this.ftpClient = new FTPClient();
            try {
                if (port > 0) {
                    this.ftpClient.connect(ipaddr, port);
                } else {
                    this.ftpClient.connect(ipaddr);
                }
            }
            catch (SocketException e) {
                request.setConnectionError(true);
                request.setConnectionFailures(request.getConnectionFailures() + 1);
                request.setQueryStartTime(request.getInitialQueryStartTime());
                throw e;
            }
            catch (IOException e) {
                request.setConnectionError(true);
                request.setConnectionFailures(request.getConnectionFailures() + 1);
                request.setQueryStartTime(request.getInitialQueryStartTime());
                throw e;
            }
            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: FTP Server connected", IWServices.LOG_TRACE, request);
            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Trying to Log into FTP Server with UID=" + this.user + " PWD=" + this.password, IWServices.LOG_TRACE, request);
            if (!this.ftpClient.login(this.user, this.password)) {
                request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Unable to login", IWServices.LOG_ERRORS, request);
                request.setConnectionError(true);
                request.setConnectionFailures(request.getConnectionFailures() + 1);
                request.setQueryStartTime(request.getInitialQueryStartTime());
            }
            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Logged into FTP Server", IWServices.LOG_TRACE, request);
            String query = ftpURL.getQuery();
            if (query != null) {
                String[] qp;
                request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Query " + query + " has been received", IWServices.LOG_REQUEST, request);
                String[] stringArray = qp = query.split(query.indexOf("&") > 0 ? "&" : "\\|");
                int n = 0;
                int n2 = stringArray.length;
                while (n < n2) {
                    String cqp = stringArray[n];
                    int cp = cqp.indexOf("=");
                    if (cp > 0) {
                        String pn = cqp.substring(0, cp).trim();
                        String pv = cqp.substring(cp + 1).trim();
                        request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Parameter " + pn + "=" + pv + " has been applied", IWServices.LOG_REQUEST, request);
                        if (pn.equalsIgnoreCase("type")) {
                            if (pv.equalsIgnoreCase("ASCII")) {
                                this.ftpClient.setFileType(0);
                            } else if (pv.equalsIgnoreCase("BINARY")) {
                                this.ftpClient.setFileType(2);
                            } else if (pv.equalsIgnoreCase("IMAGE")) {
                                this.ftpClient.setFileType(2);
                            }
                        } else if (pn.equalsIgnoreCase("mode")) {
                            if (pv.equalsIgnoreCase("ACTIVE")) {
                                request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Entering Local Active Mode", IWServices.LOG_TRACE, request);
                                this.ftpClient.enterLocalActiveMode();
                            } else if (pv.equalsIgnoreCase("PASSIVE")) {
                                request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Entering Local Passive Mode", IWServices.LOG_TRACE, request);
                                this.ftpClient.enterLocalPassiveMode();
                            } else if (pv.equalsIgnoreCase("STPASSIVE")) {
                                if (!this.ftpClient.sendSiteCommand("epsv4 off")) {
                                    request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Unable to disable EPSV", IWServices.LOG_ERRORS, request);
                                }
                                this.ftpClient.enterLocalPassiveMode();
                            }
                        } else if (pn.equalsIgnoreCase("pattern")) {
                            this.pattern = pv;
                        } else if (pn.equalsIgnoreCase("empty")) {
                            this.writeEmptyFile = pv.equalsIgnoreCase("true");
                        } else if (pn.equalsIgnoreCase("upper")) {
                            this.loadUpperNameOnly = pv.equalsIgnoreCase("true");
                        } else if (pn.equalsIgnoreCase("filter")) {
                            this.filter = pv;
                            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Filer " + this.filter + " has been applied", IWServices.LOG_REQUEST, request);
                        } else if (pn.equalsIgnoreCase("csvheader")) {
                            this.headPosition = Integer.valueOf(pv);
                            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: header position " + this.headPosition + " has been applied", IWServices.LOG_TRACE, request);
                        } else if (pn.equalsIgnoreCase("csvheader2")) {
                            this.headPosition2 = Integer.valueOf(pv);
                            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: header position 2 " + this.headPosition2 + " has been applied", IWServices.LOG_TRACE, request);
                        }
                    }
                    ++n;
                }
            }
        } else if (ftpEncryption.equals("ftps")) {
            this.ftpsClient = new FTPSClient(implicit);
            try {
                if (port > 0) {
                    this.ftpsClient.connect(ipaddr, port);
                } else {
                    this.ftpsClient.connect(ipaddr);
                }
            }
            catch (SocketException e) {
                request.setConnectionError(true);
                request.setConnectionFailures(request.getConnectionFailures() + 1);
                request.setQueryStartTime(request.getInitialQueryStartTime());
                throw e;
            }
            catch (IOException e) {
                request.setConnectionError(true);
                request.setConnectionFailures(request.getConnectionFailures() + 1);
                request.setQueryStartTime(request.getInitialQueryStartTime());
                throw e;
            }
            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: FTP Server connected", IWServices.LOG_TRACE, request);
            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Trying to Log into FTP Server with UID=" + this.user + " PWD=" + this.password, IWServices.LOG_TRACE, request);
            if (!this.ftpsClient.login(this.user, this.password)) {
                request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Unable to login", IWServices.LOG_ERRORS, request);
                request.setConnectionError(true);
                request.setConnectionFailures(request.getConnectionFailures() + 1);
                request.setQueryStartTime(request.getInitialQueryStartTime());
            }
            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Logged into FTP Server", IWServices.LOG_TRACE, request);
            String query = ftpURL.getQuery();
            if (query != null) {
                String[] qp;
                request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Query " + query + " has been received", IWServices.LOG_REQUEST, request);
                String[] stringArray = qp = query.split(query.indexOf("&") > 0 ? "&" : "\\|");
                int n = 0;
                int n3 = stringArray.length;
                while (n < n3) {
                    String cqp = stringArray[n];
                    int cp = cqp.indexOf("=");
                    if (cp > 0) {
                        String pn = cqp.substring(0, cp).trim();
                        String pv = cqp.substring(cp + 1).trim();
                        request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Parameter " + pn + "=" + pv + " has been applied", IWServices.LOG_REQUEST, request);
                        if (pn.equalsIgnoreCase("type")) {
                            if (pv.equalsIgnoreCase("ASCII")) {
                                this.ftpsClient.setFileType(0);
                            } else if (pv.equalsIgnoreCase("BINARY")) {
                                this.ftpsClient.setFileType(2);
                            } else if (pv.equalsIgnoreCase("IMAGE")) {
                                this.ftpsClient.setFileType(2);
                            }
                        } else if (pn.equalsIgnoreCase("mode")) {
                            if (pv.equalsIgnoreCase("ACTIVE")) {
                                request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Entering Local Active Mode", IWServices.LOG_TRACE, request);
                                this.ftpsClient.enterLocalActiveMode();
                            } else if (pv.equalsIgnoreCase("PASSIVE")) {
                                request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Entering Local Passive Mode", IWServices.LOG_TRACE, request);
                                this.ftpsClient.enterLocalPassiveMode();
                            } else if (pv.equalsIgnoreCase("STPASSIVE")) {
                                if (!this.ftpsClient.sendSiteCommand("epsv4 off")) {
                                    request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Unable to disable EPSV", IWServices.LOG_ERRORS, request);
                                }
                                this.ftpsClient.enterLocalPassiveMode();
                            }
                        } else if (pn.equalsIgnoreCase("pattern")) {
                            this.pattern = pv;
                        } else if (pn.equalsIgnoreCase("empty")) {
                            this.writeEmptyFile = pv.equalsIgnoreCase("true");
                        } else if (pn.equalsIgnoreCase("upper")) {
                            this.loadUpperNameOnly = pv.equalsIgnoreCase("true");
                        } else if (pn.equalsIgnoreCase("filter")) {
                            this.filter = pv;
                            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: Filer " + this.filter + " has been applied", IWServices.LOG_REQUEST, request);
                        } else if (pn.equalsIgnoreCase("csvheader")) {
                            this.headPosition = Integer.valueOf(pv);
                            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: header position " + this.headPosition + " has been applied", IWServices.LOG_TRACE, request);
                        } else if (pn.equalsIgnoreCase("csvheader2")) {
                            this.headPosition2 = Integer.valueOf(pv);
                            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: header position 2 " + this.headPosition2 + " has been applied", IWServices.LOG_TRACE, request);
                        } else if (pn.equalsIgnoreCase("PROT")) {
                            this.ftpsClient.execPBSZ(0L);
                            this.ftpsClient.execPROT(pv);
                            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: PROT level is set to " + pv, IWServices.LOG_TRACE, request);
                        }
                    }
                    ++n;
                }
            }
        } else if (ftpEncryption.equals("sftp")) {
            JSch jsch = new JSch();
            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: JSCH Session will use " + ftpURL.getHost() + ":" + ftpURL.getPort(), IWServices.LOG_REQUEST, request);
            try {
                this.sftpSession = jsch.getSession(this.user, ftpURL.getHost(), ftpURL.getPort());
            }
            catch (JSchException e1) {
                request.lnkIWServices.logError("IWFtpBaseAdaptor: Session creation failed", IWServices.LOG_ERRORS, (Exception)((Object)e1), request);
                throw e1;
            }
            this.sftpSession.setPassword(this.password);
            this.sftpSession.setConfig("StrictHostKeyChecking", "no");
            this.sftpSession.setTimeout(15000);
            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: JSCH Session created for " + this.user + " " + this.password + (this.sftpSession.getClientVersion() == null ? "" : "\nCVersion " + this.sftpSession.getClientVersion()) + "\nTO " + this.sftpSession.getTimeout(), IWServices.LOG_REQUEST, request);
            try {
                this.sftpSession.connect();
            }
            catch (JSchException e) {
                request.setConnectionError(true);
                request.setConnectionFailures(request.getConnectionFailures() + 1);
                request.setQueryStartTime(request.getInitialQueryStartTime());
                throw e;
            }
            request.lnkIWServices.logConsole("IWFtpBaseAdaptor: JSCH Session Connected", IWServices.LOG_REQUEST, request);
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand() {
        if ($SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand != null) {
            return $SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand;
        }
        int[] nArray = new int[IWFileBaseAdaptor.FileCommand.values().length];
        try {
            nArray[IWFileBaseAdaptor.FileCommand.READ.ordinal()] = 0;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[IWFileBaseAdaptor.FileCommand.WRITE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[IWFileBaseAdaptor.FileCommand.OVERWRITE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[IWFileBaseAdaptor.FileCommand.UPDATE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[IWFileBaseAdaptor.FileCommand.INSERT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[IWFileBaseAdaptor.FileCommand.APPEND.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[IWFileBaseAdaptor.FileCommand.RENAME.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[IWFileBaseAdaptor.FileCommand.MOVE.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[IWFileBaseAdaptor.FileCommand.DELETE.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$com$interweave$adapter$filesystem$IWFileBaseAdaptor$FileCommand = nArray;
        return nArray;
    }
}

