package in.crebit.bitblue.app.WebView.bitblue.response;

public class ChangePassResponse {
    private String Status;

    public ChangePassResponse(String status) {
        Status = status;
    }

    public String getStatus() {
        return Status;
    }
}
