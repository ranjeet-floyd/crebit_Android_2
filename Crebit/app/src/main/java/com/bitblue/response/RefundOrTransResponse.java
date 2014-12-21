package com.bitblue.response;

public class RefundOrTransResponse {
    private String typeId;
    private String status;
    private String message;
    private String cybertransId;
    private String operatorId;

    public RefundOrTransResponse(String typeId, String status, String message, String cybertransId, String operatorId) {
        this.typeId = typeId;
        this.status = status;
        this.message = message;
        this.cybertransId = cybertransId;
        this.operatorId = operatorId;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCybertransId() {
        return cybertransId;
    }

    public String getOperatorId() {
        return operatorId;
    }
}
