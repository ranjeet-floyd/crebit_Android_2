package in.crebit.bitblue.app.WebView.bitblue.response;

public class ElectricityResponse {
    private String Status;
    private String AvaiBal;

    public ElectricityResponse(String status, String avaiBal) {
        Status = status;
        AvaiBal = avaiBal;
    }

    public String getStatus() {
        return Status;
    }

    public String getAvaiBal() {
        return AvaiBal;
    }
}
