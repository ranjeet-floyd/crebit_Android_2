package in.crebit.bitblue.app.WebView.bitblue.response;

public class RelianceResponse {

    private String TransId;
    private String Message;
    private int StatusCode;
    private String AvailableBalance;

    public RelianceResponse(String transId, String message,
                            int statusCode, String availableBalance) {
        TransId = transId;
        Message = message;
        StatusCode = statusCode;
        AvailableBalance = availableBalance;
    }

    public String getTransId() {
        return TransId;
    }

    public String getMessage() {
        return Message;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public String getAvailableBalance() {
        return AvailableBalance;
    }
}
