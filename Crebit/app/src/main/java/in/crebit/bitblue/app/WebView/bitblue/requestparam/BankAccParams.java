package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class BankAccParams {
    private String UserId;
    private String Key;
    private String Mobile;
    private String Name;
    private String Account;
    private String IFSC;
    private double Amount;

    public BankAccParams(String userId, String key, String mobile, String name, String account, String IFSC, double amount) {
        UserId = userId;
        Key = key;
        Mobile = mobile;
        Name = name;
        Account = account;
        this.IFSC = IFSC;
        Amount = amount;
    }

    public String getUserId() {
        return UserId;
    }

    public String getKey() {
        return Key;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getName() {
        return Name;
    }

    public String getAccount() {
        return Account;
    }

    public String getIFSC() {
        return IFSC;
    }

    public double getAmount() {
        return Amount;
    }
}
