package utils.SQLQueries;

import static utils.CSVScripts.CSVScripts.*;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.*;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.*;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.*;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */
public class GeneralScripts {
    public static void removeAllDataFromAllDBTables(){
        removeAllCustomerTable();
        removeAllPurchaseTable();
        removeAllSubscriptionsTable();
    }
    public static void csvToDB(String customerLocation,String purchasaeLocation,String subscriptionLocation){
        insertRangeOfCustomersToDB(importCustomersFromCsv(customerLocation));
        insertRangeOfPurchasesToDB(importPurchasesFromCsv(purchasaeLocation));
        insertRangeOfSubscriptionsToDB(importSubscriptionsFromCsv(subscriptionLocation));
    }
    public static void DBToCsv(String customerLocation,String purchasaeLocation,String subscriptionLocation){
        exportCustomersToCsv(getAllCustomersFromDB(),customerLocation);
        exportPurchasesToCsv(getAllPurchasesFromDB(),purchasaeLocation);
        exportSubscriptionsToCsv(getAllSubscriptionsFromDB(),subscriptionLocation);
    }
}
