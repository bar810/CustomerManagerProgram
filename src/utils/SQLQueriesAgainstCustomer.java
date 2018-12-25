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

import static utils.GlobalProperties.customerFactory;
import static utils.Utils.isNotNullOrEmpty;

/**
 * @author bbrownsh
 * @since 12/24/2018
 */
public class SQLQueriesAgainstCustomer {

    public static int insertCustomerToDB(Customer customer) {
        Session customerSession=customerFactory.getCurrentSession();
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
        Session customerSession=customerFactory.getCurrentSession();
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
        Session customerSession=customerFactory.getCurrentSession();
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
        Session customerSession=customerFactory.getCurrentSession();
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

    public static void updateCustomerFirstName(Customer customer,String newFirstName){
        Session customerSession=customerFactory.getCurrentSession();
        Customer customer1=customerSession.get(Customer.class,1);
        customer1.setFirstName(newFirstName);
        customerSession.getTransaction().commit();
        // customer.setFirstName("newFirstName");
        // customerSession.getTransaction().commit();
    }//TODO}
    public static void removeOneCustomer(Customer customer){}//TODO}
    public static void removeAllCustomerTable(){}//TODO}
}
