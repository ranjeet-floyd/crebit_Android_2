package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class ChangePassParams {
    private String UserId;
    private String Key;
    private String OPass;
    private String NPass;

    public ChangePassParams(String userId, String key, String OPass, String NPass) {
        UserId = userId;
        Key = key;
        this.OPass = OPass;
        this.NPass = NPass;
    }

    public String getUserId() {
        return UserId;
    }

    public String getKey() {
        return Key;
    }

    public String getOPass() {
        return OPass;
    }

    public String getNPass() {
        return NPass;
    }
}
