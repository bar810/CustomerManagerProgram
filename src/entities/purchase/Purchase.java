/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities.purchase;

import javax.persistence.*;

import static utils.Utils.getCurrentTimeStamp;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
@Entity
@Table(name="Purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Purchase_ID")
    private int purchaseID;

    @Column(name="Customer_ID")
    private int customerID;

    @Column(name="Purchase_date")
    private String date;

    @Column(name="Amount")
    private double amount;

    @Column(name="New_balance")
    private double newBalance;

    public Purchase(int customerID, double amount, double newBalance) {
        this.customerID = customerID;
        this.amount = amount;
        this.newBalance = newBalance;
        this.date=getCurrentTimeStamp();
    }
}

