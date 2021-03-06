package entities;

/**
 * @author bbrownsh
 * @since 12/29/2018
 */
public class ViewLastPurchase {

    private int purchaseID;
    private int customerID;
    private String firstName;
    private String lastName;
    private String date;
    private String type;
    private double amount;
    private double newBalance;
    private String comments;

    public ViewLastPurchase() { }
    public ViewLastPurchase(int purchaseID, int customerID, String firstName, String lastName, String date, String type, double amount, double newBalance,String comments) {
        this.purchaseID = purchaseID;
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.newBalance = newBalance;
        this.comments=comments;
    }
    public int getPurchaseID() {
        return purchaseID;
    }
    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getNewBalance() {
        return newBalance;
    }
    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
