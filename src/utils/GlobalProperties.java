/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import entities.customer.Customer;
import entities.purchase.Purchase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */
public class GlobalProperties {

    public static Logger _logger;

    public static SessionFactory _customerFactory;
    public static SessionFactory _purchaseFactory;
    public static SessionFactory _subscriptionFactory;

    public static void init() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        _logger=new Logger();
        openConnections();
        _logger.debug("Global properties initialized");
    }

    public static void openConnections(){
        _customerFactory =new Configuration()
                .configure("utils/hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        _purchaseFactory =new Configuration()
                .configure("utils/hibernate.cfg.xml")
                .addAnnotatedClass(Purchase.class)
                .buildSessionFactory();

        // _subscriptionFactory =new Configuration()
        //         .configure("utils/hibernate.cfg.xml")
        //         .addAnnotatedClass(AbstractSubscription.class)//TODO :: check if this is good
        //         .buildSessionFactory();
    }
    public static void closeConnections(){
        _customerFactory.close();
        _purchaseFactory.close();
        // _subscriptionFactory.close();

        _logger.debug("Connection closed successfully");
    }

}
