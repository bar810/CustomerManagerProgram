/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package model;

import entities.Customer;
import entities.Purchase;
import entities.Subscription;

import java.util.List;

import static model.GlobalProperties.*;
import static utils.Constants.*;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.*;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.insertPurchaseToDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.*;
import static utils.Utils.getCurrentTimeStamp;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */
public class BasicMethods {
    public static void addCustomer(Customer customer){
        //check if this customer is already exist in DB. if yes- return message. if not - insert this new customer
        if(!thisCustomerExistingInDB(customer))
        {
            insertCustomerToDB(customer);
        }else {
            _logger.warning("Customer is already exist. "+customer.toString());
        }
    }

    public static void deleteCustomer(int customerId){
        //delete his subscriptions before.
        for(Subscription s : getCachedSubscriptions()){
            if(s.getCoustomerID()==customerId){
                //delete this subscription
                removeOneSubscription(s.getSubscriptionID());
            }
        }
        //finally, remove the customer
        removeOneCustomer(customerId);
    }
    public static void buySubscription(Customer customer, String type){
        //check if there is customer.
        if(thisCustomerExistingInDB(customer)){
            //check if there is subscription from this type- if yes update. if not create new
            List<Subscription> list= getAllSubscriptionFromDBWithConditions("",String.valueOf(customer.getCustomerID()),"","",type.toString());
            if(list.size()>1){
                //not valid case
                _logger.error("Customer have more than 1 subscription from on type. not valid case. Customer: "+customer);
            }
            else{
                boolean createNewSubscription=list.size()==0;
                double amount=type.equals(MEALS_SUBSCRIPTION)?Double.parseDouble(getProperty(DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT)):Double.parseDouble(getProperty(DEFAULT_VIP_SUBSCRIPTION_AMOUNT));
                if(createNewSubscription){
                    insertSubscriptionToDB(new Subscription(customer.getCustomerID(),getCurrentTimeStamp(),amount,type));
                }else{
                    //need to update the current subscription
                    updateSubscriptionBalance(list.get(0).getSubscriptionID(),list.get(0).getBalance()+amount);
                    updateSubscriptionPurchaseDate(list.get(0).getSubscriptionID(),getCurrentTimeStamp());

                }
            }

        }
    }
    public static void buyProduct(Customer customer,String type,double price){
        //check if there is customer.
        if(thisCustomerExistingInDB(customer)){
            //check if there is subscription
            List<Subscription> list=getAllSubscriptionFromDBWithConditions("",String.valueOf(customer.getCustomerID()),"","",type.toString());
            if(list.size()>1){
                //not valid case
                _logger.error("Customer have more than 1 subscription. not valid case. Customer: "+customer);
            }
            if(list.size()==1){
                //check if there is enough price
                Subscription subscription=list.get(0);
                if(subscription.getBalance()>=price){
                    //buy the product, update table
                    updateSubscriptionBalance(subscription.getSubscriptionID(),subscription.getBalance()-price);
                    //add this buying to purchase table
                    insertPurchaseToDB(new Purchase(customer.getCustomerID(),price,subscription.getBalance()-price,type,""));
                }else{
                    //do not enough balance
                }
            }
        }
    }
    public static boolean thisCustomerExistingInDB(Customer customer){
        List<Customer> customers=getAllCustomersFromDBWithConditions("",customer.getFirstName(),customer.getLastName(),customer.getMailAddress(),customer.getPhoneNumber());
        return customers.size()==1;
    }

    public static void addValueToCustomerSubscription(int customerId,String type,double amount){
        //TODO
    }
}
