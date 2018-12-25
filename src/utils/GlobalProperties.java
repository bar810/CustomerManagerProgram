/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import entities.customer.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */
public class GlobalProperties {
    public static SessionFactory _customerFactory;
    public static SessionFactory _purchaseFactory;
    public static SessionFactory _subscriptionFactory;

    public static void init() {
         _customerFactory =new Configuration()
                .configure("utils/hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        _purchaseFactory =new Configuration()
                .configure("utils/hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        _subscriptionFactory =new Configuration()
                .configure("utils/hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

         System.out.println("Global properties initialized");
    }

    public static void closeConnections(){
        _customerFactory.close();
        _purchaseFactory.close();
        _subscriptionFactory.close();

        System.out.println("Connection closed successfully");
    }

}
