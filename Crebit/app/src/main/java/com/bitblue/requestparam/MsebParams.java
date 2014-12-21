package com.bitblue.requestparam;

public class MsebParams {
    private String UserId;
    private String Key;
    private String CusAcc;
    private String BU;
    private int ServiceId;

    public MsebParams(String userId, String key, String cusAcc, String BU, int serviceId) {
        UserId = userId;
        Key = key;
        CusAcc = cusAcc;
        this.BU = BU;
        ServiceId = serviceId;
    }

    public String getUserId() {
        return UserId;
    }

    public String getKey() {
        return Key;
    }

    public String getCusAcc() {
        return CusAcc;
    }

    public String getBU() {
        return BU;
    }

    public int getServiceId() {
        return ServiceId;
    }
}
