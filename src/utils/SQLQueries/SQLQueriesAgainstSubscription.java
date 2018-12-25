/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils.SQLQueries;

import entities.Subscription;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import static utils.GlobalProperties._logger;
import static utils.Utils.getSubscriptionSession;
import static utils.Utils.isNotNullOrEmpty;

/**
 * @author bbrownsh
 * @since 12/24/2018
 */
public class SQLQueriesAgainstSubscription {
        //TODO :: more update functions
    public static int insertSubscriptionToDB(Subscription subscription) {
        _logger.debug("Inserting subscription to DB: "+subscription.toString());
        Session subscriptionSession= getSubscriptionSession();
        Integer id = -1;
        try {
            subscriptionSession.beginTransaction();
            id=(Integer)subscriptionSession.save(subscription);
            subscriptionSession.getTransaction().commit();

        }catch(Exception e){
            _logger.error("Exception thrown while trying to insert subscription: "+e.toString());
            return -1;
        }
        System.out.println("subscription inserted!");
        _logger.debug("subscription inserted successfully to DB. ID: "+subscription.getSubscriptionID());
        return subscription.getSubscriptionID();
    }
    public static int insertRangeOfSubscriptionsToDB(List<Subscription> subscription) {
        _logger.debug("Inserting range of Subscription to DB. size: "+subscription.size());
        int counter=0;
        for(Subscription c: subscription){
            int retVal=insertSubscriptionToDB(c);
            if(retVal!=-1){
                counter++;
            }
        }
        if(counter==subscription.size()){
            _logger.debug("All the subscriptions are inserted to DB successfully");
        }else{
            _logger.warning("not all the subscriptions are inserted to DB successfully");
        }
        return counter;
    }

    public static Subscription getSubscriptionByID(int id){
        _logger.debug("getting Subscription from DB by ID: "+id);
        Session subscriptionSession;
        subscriptionSession= getSubscriptionSession();
        Subscription subscription=null;
        try {
            subscriptionSession.beginTransaction();
            subscription=subscriptionSession.get(Subscription.class,id);
            subscriptionSession.getTransaction().commit();
        }catch(Exception e){
            _logger.error("Exception while getting subscription by ID: "+e.toString());
            return null;
        }
        if(subscription==null){
            _logger.warning("subscription not found ! ID: "+id);
        }
        else{
            _logger.debug("subscription getting succeeded !");
        }
        return subscription;
    }

    public static List<Subscription> getAllSubscriptionsFromDB(){
        _logger.debug("Getting all Subscriptions from DB");
        List<Subscription> Subscriptions=new ArrayList<>();
        try {
                Session SubscriptionSession= getSubscriptionSession();
                List mealsSbscription=new ArrayList<Subscription>();
                SubscriptionSession.beginTransaction();
                Subscriptions=SubscriptionSession.createQuery("from Subscription").getResultList();
                SubscriptionSession.getTransaction().commit();
        }catch(Exception e){
            _logger.error("Exception while trying to get all Subscriptions from DB");
            return null;
        }
        _logger.debug("Getting all Subscriptions query succeeded. Subscriptions founds: "+Subscriptions.size());
        return Subscriptions;
    }

    public static List<Subscription> getAllSubscriptionFromDBWithConditions(String ID, String customerID, String date, String balance, String type){
        boolean putFirstPrefix=false;
        String query="from Subscription";
        if(isNotNullOrEmpty(ID)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.subscriptionID='"+ID+"'";
        }
        if(isNotNullOrEmpty(customerID)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.coustomerID='"+customerID+"'";
        }
        if(isNotNullOrEmpty(date)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.subscriptionPurchaseDate='"+date+"'";
        }
        if(isNotNullOrEmpty(balance)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.balance='"+balance+"'";
        }
        if(isNotNullOrEmpty(type)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.type='"+type+"'";
        }

        _logger.debug("Getting all subscriptions from DB with condition. query: "+query);
        List subscriptions=new ArrayList<Subscription>();
        try {
            Session subscriptionSession= getSubscriptionSession();

            subscriptionSession.beginTransaction();
            subscriptions=subscriptionSession.createQuery(query).getResultList();
            subscriptionSession.getTransaction().commit();

        }catch(Exception e){
            _logger.error("Exception while trying to get all subscriptions from DB with conditions");
            return null;
        }
        _logger.debug("Getting all subscriptions with condition query succeeded. subscriptions founds: "+subscriptions.size());
        return subscriptions;
    }
    public static void updateSubscriptionBalance(int subscriptionID,double newBalance){
        _logger.debug("update Subscription Balance. Subscription ID: "+subscriptionID+". new Balance: "+newBalance);
        Session subscriptionSession= getSubscriptionSession();
        subscriptionSession.beginTransaction();
        Subscription subscription=subscriptionSession.get(Subscription.class,subscriptionID);
        if(subscription==null){
            _logger.warning("subscription not found");
        }else{
            subscription.setBalance(newBalance);
            subscriptionSession.getTransaction().commit();
            _logger.debug("subscription updated successfully. subscription: "+subscription.toString());
        }
    }
    public static void removeOneSubscription(int subscriptionID){
        _logger.debug("removing Subscription. ID: "+subscriptionID);
        Session subscriptionSession;
        subscriptionSession=getSubscriptionSession();
        subscriptionSession.beginTransaction();
        Subscription subscription=subscriptionSession.get(Subscription.class,subscriptionID);
        if(subscription==null){
            _logger.warning("Subscription not found");
        }else{
            subscriptionSession.delete(subscription);
            subscriptionSession.getTransaction().commit();
            _logger.debug("subscription removed successfully");
        }
    }
    public static void removeAllSubscriptionsTable(){
        _logger.debug("Removing all Subscriptions from DB");
        List<Subscription> list=getAllSubscriptionsFromDB();
        for(Subscription c: list){
                removeOneSubscription(c.getSubscriptionID());
        }
        _logger.debug("Removing all Subscriptions succeeded");
    }
}
