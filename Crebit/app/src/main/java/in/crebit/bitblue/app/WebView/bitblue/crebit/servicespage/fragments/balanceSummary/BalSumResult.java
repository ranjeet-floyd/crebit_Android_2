package in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.fragments.balanceSummary;

public class BalSumResult {
    private int count;
    private String Name;
    private String Amount;
    private String Contact;
    private String Date;
    private String TransactionId;

    public BalSumResult() {
    }

    public BalSumResult(String name, String amount, String contact, String date, String transactionId) {
        Name = name;
        Amount = amount;
        Contact = contact;
        Date = date;
        TransactionId = transactionId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getName() {
        return Name;
    }

    public String getAmount() {
        return Amount;
    }

    public String getContact() {
        return Contact;
    }

    public String getDate() {
        return Date;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
