/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk;

import com.iwtransactions.datamapType;

public class IwConnection {
    private String driver = null;
    private String url = null;
    private String userName = null;
    private String password = null;

    public IwConnection(datamapType dataMap) {
        try {
            this.driver = dataMap.getdriver().getValue().trim();
            this.url = dataMap.geturl().getValue().trim();
            this.userName = dataMap.getuser().getValue().trim();
            this.password = dataMap.getpassword().getValue().trim();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final String getDriver() {
        return this.driver;
    }

    public final void setDriver(String driver) {
        this.driver = driver;
    }

    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(String url) {
        this.url = url;
    }

    public final String getUserName() {
        return this.userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean equals(Object arg0) {
        if (arg0 instanceof IwConnection) {
            return ((IwConnection)arg0).getDriver().equals(this.driver) && ((IwConnection)arg0).getUrl().equals(this.url) && ((IwConnection)arg0).getUserName().equals(this.userName) && ((IwConnection)arg0).getPassword().equals(this.password);
        }
        if (arg0 instanceof datamapType) {
            try {
                boolean ret = ((datamapType)arg0).getdriver().getValue().trim().equals(this.driver) && ((datamapType)arg0).geturl().getValue().trim().equals(this.url) && ((datamapType)arg0).getuser().getValue().trim().equals(this.userName) && ((datamapType)arg0).getpassword().getValue().trim().equals(this.password);
                return ret;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return super.equals(arg0);
    }

    public boolean isEmpty() {
        return this.userName.trim().length() == 0 && this.password.trim().length() == 0 && this.driver.trim().length() == 0 && this.url.trim().length() == 0;
    }

    public boolean isNull() {
        return this.userName == null || this.password == null || this.driver == null || this.url == null;
    }
}

