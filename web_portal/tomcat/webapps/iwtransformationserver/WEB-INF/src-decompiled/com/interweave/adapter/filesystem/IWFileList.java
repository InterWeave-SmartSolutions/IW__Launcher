/*
 * Decompiled with CFR 0.152.
 */
package com.interweave.adapter.filesystem;

import com.interweave.bindings.Access;
import com.interweave.bindings.Datamap;
import com.interweave.bindings.IWIDataMap;
import com.interweave.bindings.Mapping;
import com.interweave.bindings.Parameter;
import com.interweave.bindings.Values;
import com.interweave.core.IWRequest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

public class IWFileList
implements IWIDataMap {
    private int indentLevel = 1;
    public File file = null;
    public String rootDirectory = null;
    public String currentDirectory = "";
    public int messageNum = 0;
    private String mapName = null;
    private Datamap dataMap = null;
    public Hashtable accessList = new Hashtable();
    private Access curAccess = null;

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

    public String MakeFileName(String fileName) {
        return String.valueOf(this.currentDirectory) + File.separator + fileName;
    }

    public void setCurrentDirectory(String fileName) {
        File tmpFile;
        if (this.isDirectory(fileName) && (tmpFile = this.OpenFile(fileName)) != null) {
            this.currentDirectory = tmpFile.getAbsolutePath();
            Object var2_2 = null;
        }
    }

    public void closeConnection() {
    }

    public File OpenFile(String fileName) {
        String fullFileName = this.MakeFileName(fileName);
        File tmpFile = new File(fullFileName);
        fullFileName = null;
        return tmpFile;
    }

    public File CreateFile(String fileName) {
        File tmpFile = new File(fileName);
        tmpFile.delete();
        try {
            tmpFile.createNewFile();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return tmpFile;
    }

    public boolean isDirectory(String fileName) {
        boolean bRet = false;
        File tmpFile = this.OpenFile(fileName);
        if (tmpFile != null) {
            bRet = tmpFile.isDirectory();
            tmpFile = null;
        }
        return bRet;
    }

    public boolean isFile(String fileName) {
        boolean bRet = false;
        File tmpFile = this.OpenFile(fileName);
        if (tmpFile != null) {
            bRet = tmpFile.isFile();
            tmpFile = null;
        }
        return bRet;
    }

    public synchronized String[] getFiles(String path, String strFilter) {
        if (path == null) {
            path = this.currentDirectory;
        } else {
            this.currentDirectory = path = this.MakeFileName(path);
        }
        File file = new File(path);
        String[] files = file.list();
        file = null;
        return files;
    }

    public synchronized String FileToString(String fileName) {
        File file = null;
        file = this.OpenFile(fileName);
        if (file == null) {
            return null;
        }
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(file));
        }
        catch (FileNotFoundException e2) {
            Object e2 = null;
            return null;
        }
        StringBuffer outString = new StringBuffer();
        String line = null;
        try {
            while ((line = input.readLine()) != null) {
                outString.append(line);
            }
            input.close();
        }
        catch (IOException e3) {
            Object e3 = null;
        }
        line = outString.toString();
        outString = null;
        file = null;
        input = null;
        return line;
    }

    public synchronized void StringToFile(String fileName, String strOutput) {
        File file = null;
        file = this.OpenFile(fileName);
        if (file == null) {
            return;
        }
        try {
            file.createNewFile();
        }
        catch (IOException e2) {
            Object e2 = null;
        }
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
        }
        catch (IOException e3) {
            Object e3 = null;
            return;
        }
        try {
            output.write(strOutput);
            output.close();
        }
        catch (IOException e4) {
            Object e4 = null;
        }
        file = null;
        output = null;
    }

    public synchronized void CreateStringToFile(String fileName, String strOutput) {
        File file = null;
        file = this.CreateFile(fileName);
        if (file == null) {
            return;
        }
        try {
            file.createNewFile();
        }
        catch (IOException e2) {
            Object e2 = null;
        }
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
        }
        catch (IOException e3) {
            Object e3 = null;
            return;
        }
        try {
            output.write(strOutput);
            output.close();
        }
        catch (IOException e4) {
            Object e4 = null;
        }
        file = null;
        output = null;
    }

    public void Process() {
        String[] files = this.getFiles(null, null);
        this.ProcessDirectory(files, null);
        files = null;
    }

    public void ProcessDirectory(String[] files, String directory) {
        String[] nextFiles = null;
        int nFile = 0;
        String currentDirectory = this.currentDirectory;
        if (directory != null) {
            this.setCurrentDirectory(directory);
            currentDirectory = this.currentDirectory;
        }
        while (nFile < files.length) {
            if (this.isDirectory(files[nFile])) {
                nextFiles = this.getFiles(files[nFile], null);
                if (nextFiles == null) {
                    ++nFile;
                    continue;
                }
                ++this.indentLevel;
                this.ProcessDirectory(nextFiles, files[nFile]);
                nextFiles = null;
                this.currentDirectory = currentDirectory;
            } else if (this.isFile(files[nFile]) && files[nFile].indexOf(".ofx") > -1) {
                this.ProcessFile(files[nFile]);
            }
            ++nFile;
        }
        --this.indentLevel;
        currentDirectory = null;
    }

    public void ProcessFile(String file) {
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
                    strData = request.getParameter(param.getInput());
                    if (strData == null) {
                        strData = "";
                    }
                    responseBuffer.append("            <col number=\"" + count + "\" name=\"" + param.getMapping().getContent() + "\">" + strData + "</col>\n");
                    ++count;
                }
                responseBuffer.append("        </row>\n      </data>\n    </datamap>\n");
            }
        }
        return responseBuffer;
    }

    public void setup(Datamap map, IWRequest request) throws Exception {
        String directory = "./";
        this.file = new File(directory);
        this.currentDirectory = this.rootDirectory = this.file.getAbsolutePath();
        this.mapName = map.getName();
        this.dataMap = map;
        for (Access access : map.getAccess()) {
            this.accessList.put(access.getType(), access);
        }
    }

    public Hashtable getAccessList() {
        return this.accessList;
    }

    public Access getCurAccess() {
        return this.curAccess;
    }
}

