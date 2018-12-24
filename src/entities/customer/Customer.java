/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities.customer;

import entities.purchase.Purchase;
import entities.subscription.MealsSubscription;
import entities.subscription.Subscription;

import java.util.ArrayList;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String mailAddress;
    private String phoneNumber;
    private ArrayList<Subscription> subscription;
    private ArrayList<Purchase> purchasesHistory;


    public Customer(int ID, String firstName, String lastName, String mailAddress, String phoneNumber) {
        this.customerID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
        subscription=new ArrayList<Subscription>();
        purchasesHistory=new ArrayList<Purchase>();
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

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Subscription> getSubscription() {
        return subscription;
    }

    public void setSubscription(ArrayList<Subscription> subscription) {
        this.subscription = subscription;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public ArrayList<Purchase> getPurchasesHistory() {
        return purchasesHistory;
    }

    public boolean makeMealPurchase(double amount){
        for (Subscription s: subscription) {
            if(s.getClass().equals(MealsSubscription.class) && s.thereIsMoreCredit(amount)){
                s.purchase(amount);
                purchasesHistory.add(new Purchase(customerID,1,s.getBalance()));
                return true;
            }
        }
        //TODO there is not enough credit
        return false;
    }

    public void buyMealSubscription(){
        subscription.add(new MealsSubscription());
    }

}
