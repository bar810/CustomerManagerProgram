/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities.subscription;

import javax.persistence.*;
import java.util.Date;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractSubscription implements Subscription {
    public enum SubscriptionType {
        MEALS(1), VIP(2);

        private final int type;
        SubscriptionType(int type) { this.type = type; }
        public int getValue() { return type; }
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Subscription_ID")
    private int subscriptionID;

    @Column(name="Customer_ID")
    private int coustomerID;

    @Column(name="Balance")
    private Date subscriptionPurchaseDate;

    @Column(name="Type")
    private double balance;

    @Column(name="Type")
    private SubscriptionType type;

    public AbstractSubscription(double balance,SubscriptionType subscriptionType) {
        this.balance = balance;
        this.type=subscriptionType;
        subscriptionPurchaseDate =new Date();
    }

    @Override
    public boolean purchase(double amount) {
        if(thereIsMoreCredit(amount)){
            //purchase
            this.setBalance(this.getBalance()-amount);
            return true;
        }
        //there is not enough amount
        return false;
    }


    @Override
    public boolean thereIsMoreCredit(double amount) {
        return balance>=amount;
    }

    public Date getSubscriptionPurchaseDate() {
        return subscriptionPurchaseDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
