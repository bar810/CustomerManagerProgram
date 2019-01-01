import entities.Customer;
import entities.Purchase;
import entities.Subscription;
import model.GlobalProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.CSVScripts.CSVScripts.*;
import static utils.Constants.*;
import static utils.SQLQueries.GeneralScripts.*;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.getAllCustomersFromDB;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.insertCustomerToDB;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.getAllPurchasesFromDB;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.insertPurchaseToDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.getAllSubscriptionsFromDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.insertSubscriptionToDB;

/**
 * @author bbrownsh
 * @since 12/30/2018
 */
public class DBTest {
    @Before
    public void init() {
        GlobalProperties.init();
    }
    @Test
    public void CleanDB() {
        removeAllDataFromAllDBTables();
        List<Customer> customers=getAllCustomersFromDB();
        List<Purchase> purchases=getAllPurchasesFromDB();
        List<Subscription> subscriptions=getAllSubscriptionsFromDB();
        assertEquals(customers.size(), 0);
        assertEquals(purchases.size(), 0);
        assertEquals(subscriptions.size(), 0);
    }
    @Test
    public void addOneCustomerToDB(){
        String name="n";
        String ln="ln";
        String mail="mail";
        String phone="phone";
        this.CleanDB();
        int id= insertCustomerToDB(new Customer(name,ln,mail,phone));
        List<Customer> list=getAllCustomersFromDB();
        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getFirstName(), name);
        assertEquals(list.get(0).getLastName(), ln);
        assertEquals(list.get(0).getMailAddress(), mail);
        assertEquals(list.get(0).getPhoneNumber(), phone);
    }
    @Test
    public void CSVAndDB() {
        this.CleanDB();
        int customerid1 = insertCustomerToDB(new Customer("1", "1", "1", "1"));
        int customerid2 = insertCustomerToDB(new Customer("2", "2", "2", "2"));
        insertPurchaseToDB(new Purchase(customerid1, 10, 10, MEALS_SUBSCRIPTION, ""));
        insertPurchaseToDB(new Purchase(customerid2, 10, 10, VIP_SUBSCRIPTION, ""));
        insertSubscriptionToDB(new Subscription(customerid1, 10, MEALS_SUBSCRIPTION));
        insertSubscriptionToDB(new Subscription(customerid2, 10, VIP_SUBSCRIPTION));
        DBToCsv(CUSTOMER_TABLE_LOCATION, PURCHASE_TABLE_LOCATION, SUBSCRIPTION_TABLE_LOCATION);
        List<Customer> customers = (importCustomersFromCsv(CUSTOMER_TABLE_LOCATION));
        List<Purchase> purchases = (importPurchasesFromCsv(PURCHASE_TABLE_LOCATION));
        List<Subscription> subscriptions = (importSubscriptionsFromCsv(SUBSCRIPTION_TABLE_LOCATION));

        assertEquals(customers.size(), 2);
        assertEquals(customers.get(0).getFirstName(), "1");
        assertEquals(customers.get(0).getLastName(), "1");
        assertEquals(customers.get(0).getMailAddress(), "1");
        assertEquals(customers.get(0).getPhoneNumber(), "1");
        assertEquals(customers.get(1).getFirstName(), "2");
        assertEquals(customers.get(1).getLastName(), "2");
        assertEquals(customers.get(1).getMailAddress(), "2");
        assertEquals(customers.get(1).getPhoneNumber(), "2");

        assertEquals(purchases.size(), 2);
        assertEquals(purchases.get(0).getCustomerID(), customerid1);
        assertEquals(String.valueOf(purchases.get(0).getAmount()), "10.0");
        assertEquals(purchases.get(0).getComments(), "");
        assertEquals(String.valueOf(purchases.get(0).getNewBalance()), "10.0");
        assertEquals(purchases.get(0).getType(), MEALS_SUBSCRIPTION);
        assertEquals(purchases.get(1).getCustomerID(), customerid2);
        assertEquals(String.valueOf(purchases.get(1).getAmount()), "10.0");
        assertEquals(purchases.get(1).getComments(), "");
        assertEquals(String.valueOf(purchases.get(1).getNewBalance()), "10.0");
        assertEquals(purchases.get(1).getType(), VIP_SUBSCRIPTION);

        assertEquals(subscriptions.size(), 2);
        assertEquals(subscriptions.get(0).getCoustomerID(), customerid1);
        assertEquals(String.valueOf(subscriptions.get(0).getBalance()), "10.0");
        assertEquals(subscriptions.get(0).getType(), MEALS_SUBSCRIPTION);
        assertEquals(subscriptions.get(1).getCoustomerID(), customerid2);
        assertEquals(String.valueOf(subscriptions.get(1).getBalance()), "10.0");
        assertEquals(subscriptions.get(1).getType(), VIP_SUBSCRIPTION);

        this.CleanDB();
        csvToDB(CUSTOMER_TABLE_LOCATION, PURCHASE_TABLE_LOCATION, SUBSCRIPTION_TABLE_LOCATION);
        List<Customer> customersFromDB = getAllCustomersFromDB();
        List<Purchase> purchasesFromDB = getAllPurchasesFromDB();
        List<Subscription> subscriptionsFromDB = getAllSubscriptionsFromDB();

        assertEquals(customers.size(), customersFromDB.size());
        assertEquals(customers.get(0).getFirstName(), customersFromDB.get(0).getFirstName());
        assertEquals(customers.get(0).getLastName(), customersFromDB.get(0).getLastName());
        assertEquals(customers.get(0).getMailAddress(), customersFromDB.get(0).getMailAddress());
        assertEquals(customers.get(0).getPhoneNumber(), customersFromDB.get(0).getPhoneNumber());
        assertEquals(customers.get(1).getFirstName(), customersFromDB.get(1).getFirstName());
        assertEquals(customers.get(1).getLastName(), customersFromDB.get(1).getLastName());
        assertEquals(customers.get(1).getMailAddress(), customersFromDB.get(1).getMailAddress());
        assertEquals(customers.get(1).getPhoneNumber(), customersFromDB.get(1).getPhoneNumber());

        assertEquals(purchases.size(), purchasesFromDB.size());
        assertEquals(purchases.get(0).getCustomerID(), purchasesFromDB.get(0).getCustomerID());
        assertEquals(String.valueOf(purchases.get(0).getAmount()), String.valueOf(purchasesFromDB.get(0).getAmount()));
        assertEquals(purchases.get(0).getComments(), purchasesFromDB.get(0).getComments());
        assertEquals(String.valueOf(purchases.get(0).getNewBalance()), String.valueOf(purchasesFromDB.get(0).getNewBalance()));
        assertEquals(purchases.get(0).getType(), purchasesFromDB.get(0).getType());
        assertEquals(purchases.get(1).getCustomerID(), purchasesFromDB.get(1).getCustomerID());
        assertEquals(String.valueOf(purchases.get(1).getAmount()), String.valueOf(purchasesFromDB.get(1).getAmount()));
        assertEquals(purchases.get(1).getComments(), purchasesFromDB.get(1).getComments());
        assertEquals(String.valueOf(purchases.get(1).getNewBalance()), String.valueOf(purchasesFromDB.get(1).getNewBalance()));
        assertEquals(purchases.get(1).getType(), purchasesFromDB.get(1).getType());

        assertEquals(subscriptions.size(), subscriptionsFromDB.size());
        assertEquals(subscriptions.get(0).getCoustomerID(), subscriptionsFromDB.get(0).getCoustomerID());
        assertEquals(String.valueOf(subscriptions.get(0).getBalance()), String.valueOf(subscriptionsFromDB.get(0).getBalance()));
        assertEquals(subscriptions.get(0).getType(), subscriptionsFromDB.get(0).getType());
        assertEquals(subscriptions.get(1).getCoustomerID(), subscriptionsFromDB.get(1).getCoustomerID());
        assertEquals(String.valueOf(subscriptions.get(1).getBalance()), String.valueOf(subscriptionsFromDB.get(1).getBalance()));
        assertEquals(subscriptions.get(1).getType(), subscriptionsFromDB.get(1).getType());

        File file1 = new File(CUSTOMER_TABLE_LOCATION);
        File file2 = new File(PURCHASE_TABLE_LOCATION);
        File file3 = new File(SUBSCRIPTION_TABLE_LOCATION);
        file1.delete();
        file2.delete();
        file3.delete();
        this.CleanDB();

    }
    @After
    public void finish() {
        GlobalProperties.closeConnections();
    }
}
