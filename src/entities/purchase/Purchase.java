/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities.purchase;

import static utils.Utils.getCurrentTimeStamp;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
public class Purchase {

    private int purchaseID;
    private int customerID;
    private String date;
    private double amount;
    private double newBalance;

    public Purchase(int customerID, double amount, double newBalance) {
        this.customerID = customerID;
        this.amount = amount;
        this.newBalance = newBalance;
        this.date=getCurrentTimeStamp();
    }
}

