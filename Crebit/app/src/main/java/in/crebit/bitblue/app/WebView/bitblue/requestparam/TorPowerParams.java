package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class TorPowerParams {
    private String Bu;
    private String amount;
    private String cusAcc;
    private String cusMob;
    private String key;
    private String userId;

    public TorPowerParams(String bu, String amount, String cusAcc, String cusMob, String key, String userId) {
        Bu = bu;
        this.amount = amount;
        this.cusAcc = cusAcc;
        this.cusMob = cusMob;
        this.key = key;
        this.userId = userId;
    }

    public String getBu() {
        return Bu;
    }

    public String getAmount() {
        return amount;
    }

    public String getCusAcc() {
        return cusAcc;
    }

    public String getCusMob() {
        return cusMob;
    }

    public String getKey() {
        return key;
    }

    public String getUserId() {
        return userId;
    }
}
