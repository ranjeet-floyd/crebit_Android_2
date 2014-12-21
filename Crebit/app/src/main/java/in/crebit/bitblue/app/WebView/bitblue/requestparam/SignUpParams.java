package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class SignUpParams {
    private int UserType;
    private String Name;
    private String Pass;
    private String Mobile;

    public SignUpParams() {
    }

    public SignUpParams(int userType, String name, String pass, String mobile) {
        UserType = userType;
        Name = name;
        Pass = pass;
        Mobile = mobile;
    }

    public int getUserType() {
        return UserType;
    }

    public String getName() {
        return Name;
    }

    public String getPass() {
        return Pass;
    }

    public String getMobile() {
        return Mobile;
    }
}
