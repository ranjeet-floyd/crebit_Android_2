package com.bitblue.requestparam;

public class RefundOrTransParams {
    private String Comments;
    private String Key;
    private String TransId;
    private String TypeId;
    private String UserId;

    public RefundOrTransParams(String comments, String key, String transId, String typeId, String userId) {
        Comments = comments;
        Key = key;
        TransId = transId;
        TypeId = typeId;
        UserId = userId;
    }

    public String getComments() {
        return Comments;
    }

    public String getKey() {
        return Key;
    }

    public String getTransId() {
        return TransId;
    }

    public String getTypeId() {
        return TypeId;
    }

    public String getUserId() {
        return UserId;
    }
}
