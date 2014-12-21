package com.bitblue.IDs;

public class broadBand {
    private static String operatorId;

    public static String getBroadBandOperatorId(int position) {
        switch (position) {
            case 0:
                operatorId = "70";
                break;  //Tikona postpaid
            default:
                operatorId = "";
                break;
        }
        return operatorId;
    }
}
