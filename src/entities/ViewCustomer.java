/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author bbrownsh
 * @since 12/28/2018
 */
public class ViewCustomer {

    private SimpleIntegerProperty customerID;

    private SimpleStringProperty firstName;

    private SimpleStringProperty lastName;

    private SimpleStringProperty lastPurchase;

    private SimpleDoubleProperty mealsBalance;

    private SimpleDoubleProperty vipBalance;

    public ViewCustomer(int customerID, String firstName, String lastName, String lastPurchase, double mealsBalance, double vipBalance) {
        this.customerID = new SimpleIntegerProperty(customerID);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.lastPurchase = new SimpleStringProperty(lastPurchase);
        this.mealsBalance = new SimpleDoubleProperty(mealsBalance);
        this.vipBalance = new SimpleDoubleProperty(vipBalance);
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public SimpleIntegerProperty customerIDProperty() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getLastPurchase() {
        return lastPurchase.get();
    }

    public SimpleStringProperty lastPurchaseProperty() {
        return lastPurchase;
    }

    public void setLastPurchase(String lastPurchase) {
        this.lastPurchase.set(lastPurchase);
    }

    public double getMealsBalance() {
        return mealsBalance.get();
    }

    public SimpleDoubleProperty mealsBalanceProperty() {
        return mealsBalance;
    }

    public void setMealsBalance(double mealsBalance) {
        this.mealsBalance.set(mealsBalance);
    }

    public double getVipBalance() {
        return vipBalance.get();
    }

    public SimpleDoubleProperty vipBalanceProperty() {
        return vipBalance;
    }

    public void setVipBalance(double vipBalance) {
        this.vipBalance.set(vipBalance);
    }
}
