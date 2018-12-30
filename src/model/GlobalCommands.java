/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package model;

import entities.Customer;
import entities.Purchase;
import entities.Subscription;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import utils.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;

import static utils.Constants.*;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */
public class GlobalCommands {

    public static Logger _logger;

    public static SessionFactory _customerFactory;
    public static SessionFactory _purchaseFactory;
    public static SessionFactory _SubscriptionFactory;
    public static Properties _properties;

    public static void init() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        _logger=new Logger();
        initProperties();
        openConnections();
        _logger.debug("Global properties initialized");
    }

    private static void initProperties(){
        _properties = new Properties();

        try {
            InputStream is=new FileInputStream(CONFIGURE_FILE_PATH);
            _properties.load(is);
        } catch (Exception ex) {
            _logger.debug("error with properties");
            _logger.CleanAndSaveLogIfNeeded(true);
            System.exit(0);
            //TODO :: close the program
        }
        if(!validateDoubles(new String[]{DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT,
                DEFAULT_VIP_SUBSCRIPTION_AMOUNT,
                MEAL_PRICE,
                HOT_MEAL_PRICE,
                DRINK_PRICE,
                LOG_MAX_SIZE,})){
            _logger.debug("error with properties");
            _logger.CleanAndSaveLogIfNeeded(true);
            System.exit(0);
            //TODO :: close the program.
        }
    }



    private static boolean validateDoubles(String[] str){
        for(String s:str){
            try {

                double d= Double.parseDouble(getProperty(s.replaceAll("\"","")));
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    public static String formatDouble(double d){
        return String.format("%.1f", d);
    }


    private static void set_properties(Properties p){
        try {
            OutputStream os=new FileOutputStream(CONFIGURE_FILE_PATH);
            p.store(os,null);
        } catch (Exception ex) {
            //TODO
        }
    }
    public static String getProperty(String name){
        String retVal= _properties.getProperty(name);
        return retVal;

    }


    private static void openConnections(){
        try {
            _customerFactory =new Configuration()
                    .configure(SQL_CONNECTION_CONFIGURE_FILE_PATH)
                    .addAnnotatedClass(Customer.class)
                    .buildSessionFactory();

            _purchaseFactory =new Configuration()
                    .configure(SQL_CONNECTION_CONFIGURE_FILE_PATH)
                    .addAnnotatedClass(Purchase.class)
                    .buildSessionFactory();

            _SubscriptionFactory =new Configuration()
                    .configure(SQL_CONNECTION_CONFIGURE_FILE_PATH)
                    .addAnnotatedClass(Subscription.class)
                    .buildSessionFactory();
        } catch (Exception ex) {
            _logger.error("Cannot open connection to DB");
            _logger.CleanAndSaveLogIfNeeded(true);
        }




    }
    public static void closeConnections(){
        _customerFactory.close();
        _purchaseFactory.close();
        _SubscriptionFactory.close();

        _logger.debug("Connection closed successfully");
        _logger.CleanAndSaveLogIfNeeded(true);
    }
}
