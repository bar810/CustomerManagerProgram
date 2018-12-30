/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

import entities.Customer;
import entities.Purchase;
import entities.Subscription;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.GlobalCommands;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.SQLQueries.GeneralScripts.removeAllDataFromAllDBTables;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.getAllCustomersFromDB;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.insertCustomerToDB;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.getAllPurchasesFromDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.getAllSubscriptionsFromDB;

/**
 * @author bbrownsh
 * @since 12/30/2018
 */
public class DBTest {

    @Before
    public void init() {
        GlobalCommands.init();
    }

    @Test
    public void CleanDB() {
        removeAllDataFromAllDBTables();
        List<Customer> customers=getAllCustomersFromDB();
        List<Purchase> purchases=getAllPurchasesFromDB();
        List<Subscription> subscriptions=getAllSubscriptionsFromDB();
        assertEquals(customers.size(), 0);
        assertEquals(purchases.size(), 0);
        assertEquals(subscriptions.size(), 0);
    }

    @Test
    public void addOneCustomerToDB(){
        String name="n";
        String ln="ln";
        String mail="mail";
        String phone="phone";
        this.CleanDB();
        int id= insertCustomerToDB(new Customer(name,ln,mail,phone));
        List<Customer> list=getAllCustomersFromDB();
        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getFirstName(), name);
        assertEquals(list.get(0).getLastName(), ln);
        assertEquals(list.get(0).getMailAddress(), mail);
        assertEquals(list.get(0).getPhoneNumber(), phone);
    }

    @After
    public void finish() {
        GlobalCommands.closeConnections();
    }
}
