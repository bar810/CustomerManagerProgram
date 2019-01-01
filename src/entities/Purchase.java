package entities;

import javax.persistence.*;

import static utils.Constants.*;
import static utils.Utils.getCurrentTimeStamp;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
@Entity
@Table(name=PURCHASE_TABLE_NAME)
public class Purchase {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=PURCHASE_ID_TABLE_NAME)
    private int purchaseID;
    @Column(name=PURCHASE_CUSTOMER_ID_TABLE_NAME)
    private int customerID;
    @Column(name=PURCHASE_DATE_TABLE_NAME)
    private String date;
    @Column(name=PURCHASE_AMOUNT_TABLE_NAME)
    private double amount;
    @Column(name=PURCHASE_NEW_BALANCE_TABLE_NAME)
    private double newBalance;
    @Column(name=PURCHASE_TYPE_TABLE_NAME)
    private String type;
    @Column(name=PURCHASE_COMMENTS_TABLE_NAME)
    private String comments;

    public Purchase() {
    }
    public Purchase(int customerID, double amount, double newBalance,String type,String comments) {
        this.customerID = customerID;
        this.amount = amount;
        this.newBalance = newBalance;
        this.type=type;
        this.comments=comments;
        this.date=getCurrentTimeStamp();
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
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
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
    public String toString(){
        return "Purchase: ID: "+purchaseID+", customer ID: "+customerID+", date: "+date+", amount: "+amount+", new Balance: "+newBalance;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}

