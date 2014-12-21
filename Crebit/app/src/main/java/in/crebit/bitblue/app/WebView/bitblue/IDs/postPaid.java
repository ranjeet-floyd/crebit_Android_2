package in.crebit.bitblue.app.WebView.bitblue.IDs;

public class postPaid {
    private static String operatorId;

    public static String getPostPaidOperatorId(int position) {
        switch (position) {
            case 0:
                operatorId = "10";
                break;  //Airtel LandLine
            case 1:
                operatorId = "11";
                break;  //Airtel
            case 2:
                operatorId = "12";
                break; //CellOne
            case 3:
                operatorId = "13";
                break; //Idea
            case 4:
                operatorId = "14";
                break;  //Loop Mobile
            case 5:
                operatorId = "15";
                break;  //Reliance
            case 6:
                operatorId = "16";
                break;  //Tata Docomo
            case 7:
                operatorId = "17";
                break;  //Tata Teleservices
            case 8:
                operatorId = "18";
                break; //Vodafone
            default:
                operatorId = "";
        }

        return operatorId;
    }

}
