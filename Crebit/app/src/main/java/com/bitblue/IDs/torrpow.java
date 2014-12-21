package com.bitblue.IDs;

public class torrpow {
    private static String cityId;

    public static String getCityID(int position) {
        switch (position) {
            case 0:
                cityId = "1";
                break;
            case 1:
                cityId = "2";
                break;
            case 2:
                cityId = "3";
                break;
            case 3:
                cityId = "4";
                break;
            default:
                cityId = "";
        }
        return cityId;
    }
}
