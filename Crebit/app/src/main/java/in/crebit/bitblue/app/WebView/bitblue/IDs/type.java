package in.crebit.bitblue.app.WebView.bitblue.IDs;

public class type {
    private static int typeID;

    public static int getTypeId(int position) {
        switch (position) {
            case 0:
                typeID = -1;  //All
                break;
            case 1:
                typeID = 1;  //postpaid
                break;
            case 2:
                typeID = 2;  //prepaid
                break;
            case 3:
                typeID = 3;  //dth
                break;
            case 4:
                typeID = 4;  //Electricity
                break;
            case 5:
                typeID = 5;   //Gas Bill
                break;
            case 6:
                typeID = 6;  //Insurance
                break;
            case 7:
                typeID = 7;  //BroadBand
                break;
            case 8:
                typeID = 8;  //Data Card
                break;
            case 9:
                typeID = 9;  //Fund Transfer
                break;
            case 10:
                typeID = 10;  //Bank Transfer
                break;
            case 11:
                typeID = 11;  //Crebit Admin
                break;
            case 12:
                typeID = 12;  //Crebit Monthly Charge
                break;
            case 13:
                typeID = 13;  //Money Transfer
                break;
            case 14:
                typeID = 14;  //Crebit Refund
                break;
        }
        return typeID;
    }
}
