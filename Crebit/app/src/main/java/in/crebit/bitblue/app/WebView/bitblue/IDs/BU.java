package in.crebit.bitblue.app.WebView.bitblue.IDs;

public class BU {

    public static String BuCode;

    public static String getBuCode(String[] item, int position) {
        BuCode =item[position].substring(0, 4);
        return BuCode;
    }
}
