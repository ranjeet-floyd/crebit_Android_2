package in.crebit.bitblue.app.WebView.bitblue.response;

public class MsebPayBllResponse {
    private int avaiBal;
    private int status;

    public MsebPayBllResponse(int avaiBal, int status) {
        this.avaiBal = avaiBal;
        this.status = status;
    }

    public int getAvaiBal() {
        return avaiBal;
    }

    public int getStatus() {
        return status;
    }
}
