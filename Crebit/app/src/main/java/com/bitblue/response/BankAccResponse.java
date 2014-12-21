package com.bitblue.response;

public class BankAccResponse {
    private String Status;
    private String AvailableBalance;
    private String RefId;

    public BankAccResponse(String status, String availableBalance, String refId) {
        Status = status;
        AvailableBalance = availableBalance;
        RefId = refId;
    }

    public String getStatus() {
        return Status;
    }

    public String getAvailableBalance() {
        return AvailableBalance;
    }

    public String getRefId() {
        return RefId;
    }
}
