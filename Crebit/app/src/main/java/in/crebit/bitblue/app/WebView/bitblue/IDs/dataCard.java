package in.crebit.bitblue.app.WebView.bitblue.IDs;

public class dataCard {
    private static String operatorId;

    public static String getDataCardOperatorId(int position) {
        switch (position) {
            case 0:
                operatorId = "80";
                break;//Aircel
            case 1:
                operatorId = "81";
                break;//Airtel
            case 2:
                operatorId = "82";
                break;//BSNL
            case 3:
                operatorId = "83";
                break;//Idea
            case 4:
                operatorId = "84";
                break;//MTS
            case 5:
                operatorId = "85";
                break;//Reliance
            case 6:
                operatorId = "86";
                break;//Tata Docomo
            case 7:
                operatorId = "87";
                break;//Tata Indicom
            default:
                operatorId = "";
        }
        return operatorId;
    }
}
