package com.bitblue.response;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    public static final long serialVersionUID = 1L;
    private boolean isSupported;
    private boolean isActive;
    private String userID;
    private String availableBalance;
    private boolean isUpdated;
    private boolean isDataUpdated;
    private String name;
    private String userKey;
    private String uType;
    public LoginResponse() {
    }

    public LoginResponse(boolean isSupported, boolean isActive, String userID, String availableBalance, boolean isUpdated, boolean isDataUpdated, String name, String userKey, String uType) {
        this.isSupported = isSupported;
        this.isActive = isActive;
        this.userID = userID;
        this.availableBalance = availableBalance;
        this.isUpdated = isUpdated;
        this.isDataUpdated = isDataUpdated;
        this.name = name;
        this.userKey = userKey;
        this.uType = uType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isSupported() {
        return isSupported;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getUserID() {
        return userID;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public boolean isDataUpdated() {
        return isDataUpdated;
    }

    public String getName() {
        return name;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getuType() {
        return uType;
    }
}
