package com.bitblue.crebit.servicespage.listAdapter;

public class TranSumResult {
    private String CBalance;
    private String profit;
    private String Amount;
    private String Source;
    private String TDate;
    private String Status;
    private String OperaterName;
    private String Id;
    private int OperaterId;
    private int OpType;
    private String Charge;

    public TranSumResult(String CBalance, String profit, String amount, String source, String TDate, String status, String operaterName, String id, int operaterId, int opType, String charge) {
        this.CBalance = CBalance;
        this.profit = profit;
        Amount = amount;
        Source = source;
        this.TDate = TDate;
        Status = status;
        OperaterName = operaterName;
        Id = id;
        OperaterId = operaterId;
        OpType = opType;
        Charge = charge;
    }

    public String getCBalance() {
        return CBalance;
    }

    public String getProfit() {
        return profit;
    }

    public String getAmount() {
        return Amount;
    }

    public String getSource() {
        return Source;
    }

    public String getTDate() {
        return TDate;
    }

    public String getStatus() {
        return Status;
    }

    public String getOperaterName() {
        return OperaterName;
    }

    public String getId() {
        return Id;
    }

    public int getOperaterId() {
        return OperaterId;
    }

    public int getOpType() {
        return OpType;
    }

    public String getCharge() {
        return Charge;
    }
}
