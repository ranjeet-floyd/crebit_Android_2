package in.crebit.bitblue.app.WebView.bitblue.IDs;

public class prePaid {
    private static String operatorId;

    public static String getPrePaidOperatorId(int position) {
        switch (position) {
            case 0:
                operatorId = "20";
                break;  //Aircel
            case 1:
                operatorId = "21";
                break;  //Airtel
            case 2:
                operatorId = "22";
                break; //BSNL
            case 3:
                operatorId = "23";
                break; //BSnl validity
            case 4:
                operatorId = "24";
                break;  //Idea
            case 5:
                operatorId = "25";
                break;  //Loop
            case 6:
                operatorId = "26";
                break;  //MTNL(TopUp)
            case 7:
                operatorId = "27";
                break;  //MTNL validity
            case 8:
                operatorId = "28";
                break; //MTS
            case 9:
                operatorId = "29";
                break; //REliance CDMA
            case 10:
                operatorId = "200";
                break; //REliance gsm
            case 11:
                operatorId = "201";
                break; //t24 flex
            case 12:
                operatorId = "202";
                break; //t24 special
            case 13:
                operatorId = "203";
                break; //tata docomo
            case 14:
                operatorId = "204";
                break; //tata docomo special
            case 15:
                operatorId = "205";
                break; //tata indicom
            case 16:
                operatorId = "206";
                break; //uninor
            case 17:
                operatorId = "207";
                break; //videocon
            case 18:
                operatorId = "208";
                break; //virgin cdma
            case 19:
                operatorId = "209";
                break; //virgin gsm flexi
            case 20:
                operatorId = "210";
                break; //virgin gsm special
            case 21:
                operatorId = "211";
                break; //vodafone
            default:
                operatorId = "";
        }

        return operatorId;
    }

}
