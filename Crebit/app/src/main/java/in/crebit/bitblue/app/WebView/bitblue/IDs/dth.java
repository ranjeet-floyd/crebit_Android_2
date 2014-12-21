package in.crebit.bitblue.app.WebView.bitblue.IDs;

public class dth {
    private static String operatorId;

    public static String getDTHOperatorId(int position) {
        switch (position) {
            case 0:
                operatorId = "30";
                break; // Airtel Digital TV
            case 1:
                operatorId = "31";
                break;//  Big TV
            case 2:
                operatorId = "32";
                break; // Dish TV
            case 3:
                operatorId = "33";
                break; // Sun Direct
            case 4:
                operatorId = "34";
                break;//  Tata Sky
            case 5:
                operatorId = "35";
                break; // Videocon d2h
            default:
                operatorId = "";
        }
        return operatorId;
    }
}
