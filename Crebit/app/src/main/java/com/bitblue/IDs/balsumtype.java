package com.bitblue.IDs;

public class balsumtype {

    private static String TypeId;

    public static String getTypeId(int position) {

        switch (position) {
            case 0:
                TypeId = "2";// Sender Number
            case 1:
                TypeId = "3";  //Transaction ID
        }
        return TypeId;
    }
}
