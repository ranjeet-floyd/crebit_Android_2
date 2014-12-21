package in.crebit.bitblue.app.WebView.bitblue.IDs;

public class status {
    private static int statusID;

    public static int getStatusId(int position) {
        switch (position) {
            case 0:
                statusID = -1;   //All
            case 1:
                statusID = 0;   //Failed
                break;
            case 2:
                statusID = 1;  //Success
                break;
            case 3:
                statusID = 2;  //Pending
                break;
            case 4:
                statusID = 3;  //In Progress
                break;
            case 5:
                statusID = 4;  //Reject
                break;
            case 6:
                statusID = 5;  //Received
                break;
            case 7:
                statusID = 6;  //Others
                break;
            case 8:
                statusID = 7;  //Not Known
                break;
            case 9:
                statusID = 8;  //Awaiting
                break;
            case 10:
                statusID = 9;  //Refunded
                break;
        }
        return statusID;
    }
}
