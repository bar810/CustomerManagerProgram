/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import entities.customer.Customer;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import static utils.GlobalProperties._customerFactory;
import static utils.Utils.isNotNullOrEmpty;

/**
 * @author bbrownsh
 * @since 12/24/2018
 */
public class SQLQueriesAgainstCustomer {

    public static int insertCustomerToDB(Customer customer) {
        Session customerSession= _customerFactory.getCurrentSession();
        Integer id = -1;
        try {
            customerSession.beginTransaction();
            id=(Integer)customerSession.save(customer);
            customerSession.getTransaction().commit();

        }catch(Exception e){
            //TODO
            System.out.println("Exception while insert customer");
        }
        System.out.println("Customer inserted!");
        id=customer.getCustomerID();
        return id;
    }

    public static Customer getCustomerByID(int id){
        Session customerSession= _customerFactory.getCurrentSession();
        Customer customer=null;
        try {
            customerSession.beginTransaction();
            customer=customerSession.get(Customer.class,id);
            customerSession.getTransaction().commit();

        }catch(Exception e){
            //TODO
            System.out.println("Exception while getting customer");
        }
        System.out.println("Customer getting succeeded !");
        return customer;
    }

    public static List<Customer> getAllCustomersFromDB(){
        Session customerSession= _customerFactory.getCurrentSession();
        List customers=new ArrayList<Customer>();
        try {
            customerSession.beginTransaction();
            customers=customerSession.createQuery("from Customer").getResultList();
            customerSession.getTransaction().commit();

        }catch(Exception e){
            //TODO
            System.out.println("Exception");
        }
        System.out.println("Customer succeeded !");
        return customers;
    }

    public static List<Customer> getAllCustomersFromDBWithConditions(String ID,String firstName,String lastName,String mail,String phone){
        Session customerSession= _customerFactory.getCurrentSession();
        boolean putFirstPrefix=false;
        String query="from Customer";
        if(isNotNullOrEmpty(ID)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.customerID='"+ID+"'";
        }
        if(isNotNullOrEmpty(firstName)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.firstName='"+firstName+"'";
        }
        if(isNotNullOrEmpty(lastName)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.lastName='"+lastName+"'";
        }
        if(isNotNullOrEmpty(mail)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.mailAddress='"+mail+"'";
        }
        if(isNotNullOrEmpty(phone)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.phoneNumber='"+phone+"'";
        }
        List customers=new ArrayList<Customer>();
        try {
            customerSession.beginTransaction();
            customers=customerSession.createQuery(query).getResultList();
            customerSession.getTransaction().commit();

        }catch(Exception e){
            //TODO
            System.out.println("Exception");
        }
        System.out.println("Customer succeeded !");
        return customers;
    }

    public static void updateCustomerFirstName(int customerID,String newFirstName){
        Session customerSession= _customerFactory.getCurrentSession();
        customerSession.beginTransaction();
        Customer customer=customerSession.get(Customer.class,customerID);
        customer.setFirstName(newFirstName);
        customerSession.getTransaction().commit();
    }
    public static void removeOneCustomer(int customerID){
        Session customerSession= _customerFactory.getCurrentSession();
        customerSession.beginTransaction();
        Customer customer=customerSession.get(Customer.class,customerID);
        customerSession.delete(customer);
        customerSession.getTransaction().commit();
    }
    public static void removeAllCustomerTable(){
        List<Customer> list=getAllCustomersFromDB();
        for(Customer c: list){
            removeOneCustomer(c.getCustomerID());
        }
    }
}
