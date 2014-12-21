package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class FundTransferParams {

    private String UserId;
    private String Key;
    private String MobileTo;
    private String Amount;

    public FundTransferParams(String userId, String key, String mobileTo, String amount) {
        UserId = userId;
        Key = key;
        MobileTo = mobileTo;
        Amount = amount;
    }

    public String getUserId() {
        return UserId;
    }

    public String getKey() {
        return Key;
    }

    public String getMobileTo() {
        return MobileTo;
    }

    public String getAmount() {
        return Amount;
    }
}
