package com.bitblue.response;

import org.json.JSONArray;

public class TranSumResponse {
    private double TotalAmount;
    private double TotalProfit;
    private JSONArray TranSumResults;

    public TranSumResponse(double totalAmount, double totalProfit, JSONArray tranSumResults) {
        TotalAmount = totalAmount;
        TotalProfit = totalProfit;
        TranSumResults = tranSumResults;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public double getTotalProfit() {
        return TotalProfit;
    }

    public JSONArray getTranSumResults() {
        return TranSumResults;
    }
    }
