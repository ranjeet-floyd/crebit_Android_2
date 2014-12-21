package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class MsebPayBillparams {
    private int amount;
    private String bU;
    private String cusAcc;
    private String cusMob;
    private String dueDate;
    private String key;
    private int serviceId;
    private String userId;

    public MsebPayBillparams(int amount, String bU, String cusAcc, String cusMob, String dueDate, String key, int serviceId, String userId) {
        this.amount = amount;
        this.bU = bU;
        this.cusAcc = cusAcc;
        this.cusMob = cusMob;
        this.dueDate = dueDate;
        this.key = key;
        this.serviceId = serviceId;
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public String getbU() {
        return bU;
    }

    public String getCusAcc() {
        return cusAcc;
    }

    public String getCusMob() {
        return cusMob;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getKey() {
        return key;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getUserId() {
        return userId;
    }
}
