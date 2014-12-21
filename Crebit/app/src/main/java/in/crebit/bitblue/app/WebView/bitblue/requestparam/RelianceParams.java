package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class RelianceParams {

    private String Account;
    private String Amount;
    private String key;
    private String number;
    private String userId;
    private String Source;

    public RelianceParams(String account, String amount, String key, String number, String userId, String source) {
        Account = account;
        Amount = amount;
        this.key = key;
        this.number = number;
        this.userId = userId;
        this.Source = source;
    }

    public String getAccount() {
        return Account;
    }

    public String getAmount() {
        return Amount;
    }

    public String getKey() {
        return key;
    }

    public String getNumber() {
        return number;
    }

    public String getUserId() {
        return userId;
    }

    public String getSource() {
        return Source;
    }
}
