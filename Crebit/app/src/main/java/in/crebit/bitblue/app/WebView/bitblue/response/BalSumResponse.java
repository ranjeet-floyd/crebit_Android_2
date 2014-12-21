package in.crebit.bitblue.app.WebView.bitblue.response;

import org.json.JSONArray;

public class BalSumResponse {
    private double TotalBalanceGiven;
    private double TotalBalanceTaken;
    private JSONArray balUse;

    public BalSumResponse(double totalBalanceGiven, double totalBalanceTaken, JSONArray balUse) {
        TotalBalanceGiven = totalBalanceGiven;
        TotalBalanceTaken = totalBalanceTaken;
        this.balUse = balUse;
    }

    public double getTotalBalanceGiven() {
        return TotalBalanceGiven;
    }

    public double getTotalBalanceTaken() {
        return TotalBalanceTaken;
    }

    public JSONArray getBalUse() {
        return balUse;
    }

    public void setTotalBalanceGiven(double totalBalanceGiven) {
        TotalBalanceGiven = totalBalanceGiven;
    }

    public void setTotalBalanceTaken(double totalBalanceTaken) {
        TotalBalanceTaken = totalBalanceTaken;
    }

    public void setBalUse(JSONArray balUse) {
        this.balUse = balUse;
    }
}
