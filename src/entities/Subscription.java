/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities;

import javax.persistence.*;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
@Entity
@Table(name="Subscriptions")
public class Subscription{


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Subscription_ID")
    private int subscriptionID;

    @Column(name="Customer_ID")
    private int coustomerID;

    @Column(name="Subscription_purchase_date")
    private String subscriptionPurchaseDate;

    @Column(name="Balance")
    private double balance;

    @Column(name="Type")
    private String type;

    public Subscription() {
    }

    public Subscription(int coustomerID, String subscriptionPurchaseDate, double balance,String type) {
        this.coustomerID = coustomerID;
        this.subscriptionPurchaseDate = subscriptionPurchaseDate;
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
