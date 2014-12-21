package in.crebit.bitblue.app.WebView.bitblue.requestparam;

public class ElectricityParams {
    private String UserId;
    private String Key;
    private int ServiceId;
    private String CusAcc;
    private int BU;
    private String CyDiv;
    private double Amount;
    private String CusMob;
    private String DueDate;

    public ElectricityParams(String userId, String key, int serviceId, String cusAcc, int BU, String cyDiv, double amount, String cusMob, String dueDate) {
        UserId = userId;
        Key = key;
        ServiceId = serviceId;
        CusAcc = cusAcc;
        this.BU = BU;
        CyDiv = cyDiv;
        Amount = amount;
        CusMob = cusMob;
        DueDate = dueDate;
    }
}
