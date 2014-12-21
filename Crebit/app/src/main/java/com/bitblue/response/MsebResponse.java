package com.bitblue.response;

public class MsebResponse {
    private int billAmount;
    private String dueDate;
    private int consumptionUnits;
    private String BillMonth;

    public MsebResponse(int billAmount, String dueDate, int consumptionUnits, String billMonth) {
        this.billAmount = billAmount;
        this.dueDate = dueDate;
        this.consumptionUnits = consumptionUnits;
        BillMonth = billMonth;
    }

    public String getBillMonth() {
        return BillMonth;
    }

    public int getBillAmount() {
        return billAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getConsumptionUnits() {
        return consumptionUnits;
    }
}
