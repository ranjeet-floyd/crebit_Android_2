package in.crebit.bitblue.app.WebView.bitblue.response;

public class SignUpResponse {
    private String Status;

    public SignUpResponse() {
    }

    public SignUpResponse(String status) {
        Status = status;
    }

    public String getStatus() {
        return Status;
    }
}
