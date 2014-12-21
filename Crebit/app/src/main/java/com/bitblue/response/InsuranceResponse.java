package com.bitblue.response;

public class InsuranceResponse {
    private String TransId;
    private String Message;
    private int StatusCode;
    private String AvailableBalance;

    public InsuranceResponse(String transId, String message, int statusCode, String availableBalance) {
        TransId = transId;
        Message = message;
        StatusCode = statusCode;
        AvailableBalance = availableBalance;
    }

    public String getTransId() {
        return TransId;
    }

    public String getMessage() {
        return Message;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public String getAvailableBalance() {
        return AvailableBalance;
    }
}
