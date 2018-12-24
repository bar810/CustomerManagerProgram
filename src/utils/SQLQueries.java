/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import entities.customer.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bbrownsh
 * @since 12/24/2018
 */
public class SQLQueries {

    //Customer
    public static int insertCustomerToDB(Customer customer) {
        SessionFactory factory=new Configuration()
                .configure("utils/hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();
        Session session=factory.getCurrentSession();

        Integer id = -1;

        try {
            session.beginTransaction();
            id=(Integer)session.save(customer);
            session.getTransaction().commit();

        }catch(Exception e){
            //TODO
            System.out.println("Exception while insert customer");
        } finally {
            factory.close();
        }
        System.out.println("Customer inserted!");
        id=customer.getCustomerID();
        return id;
    }

    public static Customer getCustomerByID(int id){
        SessionFactory factory=new Configuration()
                .configure("utils/hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();
        Session session=factory.getCurrentSession();

        Customer customer=null;

        try {
            session.beginTransaction();
            customer=session.get(Customer.class,id);
            session.getTransaction().commit();

        }catch(Exception e){
            //TODO
            System.out.println("Exception while getting customer");
        } finally {
            factory.close();
        }
        System.out.println("Customer getting succeeded !");
        return customer;
    }

    public static List<Customer> getAllCustomersFromDB(){
        SessionFactory factory=new Configuration()
                .configure("utils/hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();
        Session session=factory.getCurrentSession();
        List customers=new ArrayList<Customer>();
        try {
            session.beginTransaction();
            customers=session.createQuery("from customers").getResultList();
            session.getTransaction().commit();

        }catch(Exception e){
            //TODO
            System.out.println("Exception");
        } finally {
            factory.close();
        }
        System.out.println("Customer succeeded !");
        return customers;
    }





}
