/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities.subscription;

import java.util.Date;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */


public abstract class AbstractSubscription implements Subscription {
    public enum SubscriptionType {
        MEALS(1), VIP(2);

        private final int type;
        SubscriptionType(int type) { this.type = type; }
        public int getValue() { return type; }
    }

    private int subscriptionID;
    private int coustomerID;
    private Date subscriptionBuyingDate;
    private double balance;
    private SubscriptionType type;

    public AbstractSubscription(double balance,SubscriptionType subscriptionType) {
        this.balance = balance;
        this.type=subscriptionType;
        subscriptionBuyingDate =new Date();
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

    public Date getSubscriptionBuyingDate() {
        return subscriptionBuyingDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
