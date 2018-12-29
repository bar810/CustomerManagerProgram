/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import entities.Customer;
import entities.Purchase;
import entities.Subscription;
import entities.ViewCustomer;

import java.util.List;

/**
 * @author bbrownsh
 * @since 12/29/2018
 */
public class GlobalProperties {
    private static ViewCustomer cachedViewCustomer;
    private static List<Subscription> cachedSubscriptions;
    private static List<Purchase> cachedPurchases;
    private static List<Customer> cachedCustomers;


    public static ViewCustomer getCachedViewCustomer() {
        return cachedViewCustomer;
    }

    public static void setCachedViewCustomer(ViewCustomer cachedViewCustomer) {
        GlobalProperties.cachedViewCustomer = cachedViewCustomer;
    }

    public static List<Subscription> getCachedSubscriptions() {
        return cachedSubscriptions;
    }

    public static void setCachedSubscriptions(List<Subscription> cachedSubscriptions) {
        GlobalProperties.cachedSubscriptions = cachedSubscriptions;
    }

    public static List<Purchase> getCachedPurchases() {
        return cachedPurchases;
    }

    public static void setCachedPurchases(List<Purchase> cachedPurchases) {
        GlobalProperties.cachedPurchases = cachedPurchases;
    }

    public static List<Customer> getCachedCustomers() {
        return cachedCustomers;
    }

    public static void setCachedCustomers(List<Customer> cachedCustomers) {
        GlobalProperties.cachedCustomers = cachedCustomers;
    }

    public static Subscription getSubscriptionByCustoemrID(int id){
        for(Subscription s: cachedSubscriptions){
            if(s.getCoustomerID()==id){
                return s;
            }
        }
        //TODO error
        return null;
    }
}
