package model;

import entities.Customer;
import entities.Purchase;
import entities.Subscription;
import entities.ViewCustomer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import utils.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static model.GeneralViewFunctions.exit;
import static utils.Constants.*;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */
public class GlobalProperties {

    public static Logger _logger;
    private static SessionFactory _customerFactory;
    private static SessionFactory _purchaseFactory;
    private static SessionFactory _SubscriptionFactory;
    private static Properties _properties;
    private static ViewCustomer cachedViewCustomer;
    private static List<Subscription> cachedSubscriptions;
    private static List<Purchase> cachedPurchases;
    private static List<Customer> cachedCustomers;
    private static Scene scene;
    private static boolean _isManager;

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
            _logger.error("error with properties");
            _logger.CleanAndSaveLogIfNeeded(true);
            exit();
        }
        if(!validateDoubles(new String[]{DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT,
                DEFAULT_VIP_SUBSCRIPTION_AMOUNT,
                MEAL_PRICE,
                HOT_MEAL_PRICE,
                DRINK_PRICE,
                LOG_MAX_SIZE,
                TIME_TILL_CANCEL_PURCHASE})){
            _logger.debug("error with properties");
            _logger.CleanAndSaveLogIfNeeded(true);
            exit();
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
    private static void storeProperties(Properties p){
        try {
            OutputStream os=new FileOutputStream(CONFIGURE_FILE_PATH);
            p.store(os,null);
        } catch (Exception ex) {
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
    public static SessionFactory get_customerFactory() {
        return _customerFactory;
    }
    public static void set_customerFactory(SessionFactory _customerFactory) {
        GlobalProperties._customerFactory = _customerFactory;
    }
    public static SessionFactory get_purchaseFactory() {
        return _purchaseFactory;
    }
    public static void set_purchaseFactory(SessionFactory _purchaseFactory) {
        GlobalProperties._purchaseFactory = _purchaseFactory;
    }
    public static SessionFactory get_SubscriptionFactory() {
        return _SubscriptionFactory;
    }
    public static void set_SubscriptionFactory(SessionFactory _SubscriptionFactory) {
        GlobalProperties._SubscriptionFactory = _SubscriptionFactory;
    }
    public static ViewCustomer getCachedViewCustomer() {
        return cachedViewCustomer;
    }
    public static void setCachedViewCustomer(ViewCustomer cvc) {
        cachedViewCustomer = cvc;
    }
    public static List<Subscription> getCachedSubscriptions() {
        return cachedSubscriptions;
    }
    public static void setCachedSubscriptions(List<Subscription> cs) {
        cachedSubscriptions = cs;
    }
    public static List<Purchase> getCachedPurchases() {
        return cachedPurchases;
    }
    public static void setCachedPurchases(List<Purchase> cp) {
        cachedPurchases = cp;
    }
    public static List<Customer> getCachedCustomers() {
        return cachedCustomers;
    }
    public static void setCachedCustomers(List<Customer> cs) {
        cachedCustomers = cs;
    }
    public static Subscription getSubscriptionByCustomerID(int id, String type){
        for(Subscription s: cachedSubscriptions){
            if(s.getCoustomerID()==id && s.getType().equals(type)){
                return s;
            }
        }
        _logger.error("Cannot find Subscription for customer. Customer id: "+id);
        return null;
    }
    public static Scene getScene() {
        return scene;
    }
    public static void setScene(Parent value) {
        scene.setRoot(value);
    }
    public static void initScene(Parent root ){
        scene=new Scene(root);
    }
    public static boolean getIsManager() {
        return _isManager;
    }
    public static void setIsManager(boolean _isManager) {
        GlobalProperties._isManager = _isManager;
    }
}
