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
    public static SessionFactory customerFactory;

    public static void init() {
         customerFactory=new Configuration()
                .configure("utils/hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();
         System.out.println("Global properties initialized");
    }

    public static void closeConnections(){
        customerFactory.close();
        System.out.println("Connection closed successfully");
    }

}
