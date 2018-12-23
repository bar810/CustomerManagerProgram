/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package entities.customer;

import entities.purchase.Purchase;
import entities.subscription.Subscription;

import java.util.ArrayList;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String mailAddress;
    private String phoneNumber;
    private ArrayList<Subscription> subscription;
    private ArrayList<Purchase> purchasesHistory;

}
