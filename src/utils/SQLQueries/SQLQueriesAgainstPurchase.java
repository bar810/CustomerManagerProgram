/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils.SQLQueries;

import entities.Purchase;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import static model.GlobalCommands._logger;
import static utils.Utils.*;

/**
 * @author bbrownsh
 * @since 12/24/2018
 */
public class SQLQueriesAgainstPurchase {
    public static int insertPurchaseToDB(Purchase purchase) {
        _logger.debug("Inserting purchase to DB: "+purchase.toString());
        Session purchaseSession= getPurchaseSession();
        Integer id = -1;
        try {
            purchaseSession.beginTransaction();
            id=(Integer)purchaseSession.save(purchase);
            purchaseSession.getTransaction().commit();

        }catch(Exception e){
            _logger.error("Exception thrown while trying to insert purchase: "+e.toString());
            return -1;
        }
        System.out.println("purchase inserted!");
        _logger.debug("purchase inserted successfully to DB. ID: "+purchase.getPurchaseID());
        return purchase.getPurchaseID();
    }
    public static int insertRangeOfPurchasesToDB(List<Purchase> purchases) {
        _logger.debug("Inserting range of purchases to DB. size: "+purchases.size());
        int counter=0;
        for(Purchase p: purchases){
            int retVal=insertPurchaseToDB(p);
            if(retVal!=-1){
                counter++;
            }
        }
        if(counter==purchases.size()){
            _logger.debug("All the purchases are inserted to DB successfully");
        }else{
            _logger.warning("not all the purchases are inserted to DB successfully");
        }
        return counter;
    }
    public static Purchase getPurchaseByID(int id){
        _logger.debug("getting Purchase from DB by ID: "+id);
        Session purchaseSession= getPurchaseSession();
        Purchase purchase=null;
        try {
            purchaseSession.beginTransaction();
            purchase=purchaseSession.get(Purchase.class,id);
            purchaseSession.getTransaction().commit();

        }catch(Exception e){
            _logger.error("Exception while getting purchase by ID: "+e.toString());
            return null;
        }
        if(purchase==null){
            _logger.warning("purchase not found ! ID: "+id);
        }
        else{
            _logger.debug("purchase getting succeeded !");
        }
        return purchase;
    }
    public static List<Purchase> getAllPurchasesFromDB(){
        _logger.debug("Getting all Purchases from DB");
        Session purchaseSession= getPurchaseSession();
        List purchases=new ArrayList<Purchase>();
        try {
            purchaseSession.beginTransaction();
            purchases=purchaseSession.createQuery("from Purchase").getResultList();
            purchaseSession.getTransaction().commit();
        }catch(Exception e){
            _logger.error("Exception while trying to get all Purchases from DB");
            return null;
        }
        _logger.debug("Getting all Purchases query succeeded. Purchases founds: "+purchases.size());
        return purchases;
    }
    public static List<Purchase> getAllPurchasesFromDBWithConditions(String ID,String customerID,String date,String amount,String newBalance){
        Session purchaseSession= getPurchaseSession();
        boolean putFirstPrefix=false;
        String query="from Purchase";
        if(isNotNullOrEmpty(ID)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.purchaseID='"+ID+"'";
        }
        if(isNotNullOrEmpty(customerID)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.customerID='"+customerID+"'";
        }
        if(isNotNullOrEmpty(date)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.date='"+date+"'";
        }
        if(isNotNullOrEmpty(amount)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.amount='"+amount+"'";
        }
        if(isNotNullOrEmpty(newBalance)){
            if(!putFirstPrefix){
                query+= " s where";
                putFirstPrefix=true;
            }
            else{
                query+=" AND";
            }
            query+=" s.newBalance='"+newBalance+"'";
        }
        _logger.debug("Getting all Purchases from DB with condition. query: "+query);
        List purchases=new ArrayList<Purchase>();
        try {
            purchaseSession.beginTransaction();
            purchases=purchaseSession.createQuery(query).getResultList();
            purchaseSession.getTransaction().commit();

        }catch(Exception e){
            _logger.error("Exception while trying to get all purchases from DB with conditions");
            return null;
        }
        _logger.debug("Getting all purchases with condition query succeeded. purchases founds: "+purchases.size());
        return purchases;
    }
    public static void updatePurchaseAmount(int purchaseID,int newAmount){
        _logger.debug("update Purchase amount. Purchase ID: "+purchaseID+". new name: "+newAmount);
        Session purchaseSession= getPurchaseSession();
        purchaseSession.beginTransaction();
        Purchase purchase=purchaseSession.get(Purchase.class,purchaseID);
        if(purchase==null){
            _logger.warning("purchase not found");
        }else{
            purchase.setAmount(newAmount);
            purchaseSession.getTransaction().commit();
            _logger.debug("purchase updated successfully. purchase: "+purchase.toString());
        }
    }
    public static void removeOnePurchase(int purchaseID){
        _logger.debug("removing Purchase. ID: "+purchaseID);
        Session purchaseSession= getPurchaseSession();
        purchaseSession.beginTransaction();
        Purchase purchase=purchaseSession.get(Purchase.class,purchaseID);
        if(purchase==null){
            _logger.warning("Purchase not found");
        }else{
            purchaseSession.delete(purchase);
            purchaseSession.getTransaction().commit();
            _logger.debug("Purchase removed successfully");
        }
    }
    public static void removeAllPurchaseTable(){
        _logger.debug("Removing all Purchases from DB");
        List<Purchase> list=getAllPurchasesFromDB();
        for(Purchase c: list){
            removeOnePurchase(c.getPurchaseID());
        }
        _logger.debug("Removing all Purchases succeeded");
    }
}
