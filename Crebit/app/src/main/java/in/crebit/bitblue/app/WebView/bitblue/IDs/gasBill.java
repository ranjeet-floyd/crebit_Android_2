package in.crebit.bitblue.app.WebView.bitblue.IDs;

public class gasBill {
    private static String operatorId;

    public static String getGasBillOperatorId(int position) {
        switch (position) {
            case 0:
                operatorId = "50";
                break;//Mahanagar Gas Limited
            default:
                operatorId = "";
        }
        return operatorId;
    }
}
