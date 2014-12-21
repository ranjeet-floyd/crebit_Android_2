package com.bitblue.IDs;

public class insurance {
    private static String operatorId;

    public static String getInsuranceOperatorId(int position) {
        switch (position) {
            case 0:
                operatorId = "60";
                break;  //ICICI Pru life
            case 1:
                operatorId = "61";
                break;  //TATA AIG Life
            default:
                operatorId="";
        }
        return operatorId;
    }
}
