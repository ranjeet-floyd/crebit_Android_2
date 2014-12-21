package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class BalSumParams {
    private String UserId;
    private String Key;
    private String FromDate;
    private String ToDate;
    private String TypeId;
    private String Value;

    public BalSumParams(String userId, String key, String fromDate, String toDate, String typeId, String value) {
        UserId = userId;
        Key = key;
        FromDate = fromDate;
        ToDate = toDate;
        TypeId = typeId;
        Value = value;
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

    public String getTypeId() {
        return TypeId;
    }

    public String getValue() {
        return Value;
    }
}
