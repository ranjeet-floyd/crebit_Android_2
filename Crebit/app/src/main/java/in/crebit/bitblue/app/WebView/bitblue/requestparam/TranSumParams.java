package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class TranSumParams {
    private String UserId;
    private String Key;
    private String FromDate;
    private String ToDate;
    private int StatusId;
    private int TypeId;
    private String Value;

    public TranSumParams(String userId, String key, String value) {
        UserId = userId;
        Key = key;
        Value = value;
    }

    public TranSumParams(String userId, String key, String fromDate, String toDate, int statusId, int typeId) {
        UserId = userId;
        Key = key;
        FromDate = fromDate;
        ToDate = toDate;
        StatusId = statusId;
        TypeId = typeId;
    }

    public String getUserId() {
        return UserId;
    }

    public String getKey() {
        return Key;
    }

    public String getFromDate() {
        return FromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public int getTypeId() {
        return TypeId;
    }

    public int getStatusId() {
        return StatusId;
    }

    public String getValue() {
        return Value;
    }
}
