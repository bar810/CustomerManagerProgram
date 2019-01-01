package entities;

import javax.persistence.*;

import static utils.Constants.*;
import static utils.Utils.getCurrentTimeStamp;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
@Entity
@Table(name=SUBSCRIPTION_TABLE_NAME)
public class Subscription{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name= SUBSCRIPTION_ID_TABLE_NAME)
    private int subscriptionID;
    @Column(name= SUBSCRIPTION_CUSTOMER_ID_TABLE_NAME)
    private int coustomerID;
    @Column(name= SUBSCRIPTION_DATE_TABLE_NAME)
    private String subscriptionPurchaseDate;
    @Column(name= SUBSCRIPTION_BALANCE_TABLE_NAME)
    private double balance;
    @Column(name= SUBSCRIPTION_TYPE_TABLE_NAME)
    private String type;

    public Subscription() {
    }
    public Subscription(int coustomerID, double balance,String type) {
        this.coustomerID = coustomerID;
        this.subscriptionPurchaseDate = getCurrentTimeStamp();
        this.balance = balance;
        this.type=type;
    }
    public Subscription(int coustomerID,String date, double balance,String type) {
        this.coustomerID = coustomerID;
        this.subscriptionPurchaseDate = date;
        this.balance = balance;
        this.type=type;
    }
    public int getSubscriptionID() {
        return subscriptionID;
    }
    public void setSubscriptionID(int subscriptionID) {
        this.subscriptionID = subscriptionID;
    }
    public int getCoustomerID() {
        return coustomerID;
    }
    public void setCoustomerID(int coustomerID) {
        this.coustomerID = coustomerID;
    }
    public String getSubscriptionPurchaseDate() {
        return subscriptionPurchaseDate;
    }
    public void setSubscriptionPurchaseDate(String subscriptionPurchaseDate) {
        this.subscriptionPurchaseDate = subscriptionPurchaseDate;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String toString(){
        return "Meals subscription: subscriptionID: "+subscriptionID+",customerID: "+coustomerID+",subscriptionPurchaseDate: "+subscriptionPurchaseDate+",Balance: "+balance+",Type: "+type;
    }
}
