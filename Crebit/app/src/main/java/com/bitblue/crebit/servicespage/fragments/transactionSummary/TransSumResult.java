package com.bitblue.crebit.servicespage.fragments.transactionSummary;

public class TransSumResult {
    private int count;
    private String id;
    private String cBalance;
    private String profit;
    private String amount;
    private String source;
    private String tDate;
    private String status;
    private String operaterName;
    private int operaterId;
    private int OpType;
    private String charge;

    public TransSumResult() {
    }

    public TransSumResult(String id, String cBalance, String profit, String amount, String source, String tDate, String status, String operaterName, int operaterId, int opType, String charge) {
        this.id = id;
        this.cBalance = cBalance;
        this.profit = profit;
        this.amount = amount;
        this.source = source;
        this.tDate = tDate;
        this.status = status;
        this.operaterName = operaterName;
        this.operaterId = operaterId;
        OpType = opType;
        this.charge = charge;
    }

    public TransSumResult(int count, String id, String cBalance, String profit, String amount, String source, String tDate, String status, String operaterName, int operaterId, int opType, String charge) {
        this.count = count;
        this.id = id;
        this.cBalance = cBalance;
        this.profit = profit;
        this.amount = amount;
        this.source = source;
        this.tDate = tDate;
        this.status = status;
        this.operaterName = operaterName;
        this.operaterId = operaterId;
        OpType = opType;
        this.charge = charge;
    }

    public String getId() {
        return id;
    }

    public String getcBalance() {
        return cBalance;
    }

    public String getProfit() {
        return profit;
    }

    public String getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

    public String gettDate() {
        return tDate;
    }

    public String getStatus() {
        return status;
    }

    public String getOperaterName() {
        return operaterName;
    }

    public int getOperaterId() {
        return operaterId;
    }

    public int getOpType() {
        return OpType;
    }

    public String getCharge() {
        return charge;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setcBalance(String cBalance) {
        this.cBalance = cBalance;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOperaterName(String operaterName) {
        this.operaterName = operaterName;
    }

    public void setOperaterId(int operaterId) {
        this.operaterId = operaterId;
    }

    public void setOpType(int opType) {
        OpType = opType;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
