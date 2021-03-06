package utils.CSVScripts;

import entities.Customer;
import entities.Purchase;
import entities.Subscription;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.*;
import static model.GlobalProperties._logger;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */
public class CSVScripts {
    public static void exportPurchasesToCsv(List<Purchase> list,String location) {
        try
        {
            _logger.debug("Exporting to csv");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(location), "UTF-8"));
            //write the header
            StringBuffer oneLine = new StringBuffer();
            oneLine.append(PURCHASE_ID_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(PURCHASE_CUSTOMER_ID_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(PURCHASE_DATE_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(PURCHASE_AMOUNT_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(PURCHASE_NEW_BALANCE_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(PURCHASE_TYPE_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(PURCHASE_COMMENTS_TABLE_NAME);
            bw.write(oneLine.toString());
            bw.newLine();

            for (Purchase purchase : list)
            {
                oneLine = new StringBuffer();
                oneLine.append(purchase.getPurchaseID() <=0 ? "" : purchase.getPurchaseID());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(purchase.getCustomerID()< 0? "" : purchase.getCustomerID());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(purchase.getDate().trim().length() == 0? "" : purchase.getDate());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(purchase.getAmount()< 0? "" : purchase.getAmount());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(purchase.getNewBalance()<0? "" : purchase.getNewBalance());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(purchase.getType().trim().length() == 0? "" : purchase.getType());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(purchase.getComments().trim().length() == 0? "" : purchase.getComments());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception e) {
            _logger.error("Error when trying to export to CSV. "+e.toString());
        }
        _logger.debug("Exporting to csv finished successfully");
    }
    public static List<Purchase> importPurchasesFromCsv(String location) {
        _logger.debug("Import Purchases from CSV. location: "+location);
        List<String[]> content = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(location))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
            }
        } catch (Exception e) {
            _logger.error("Error while trying to import from csv. "+e.toString());
        }
        ArrayList<Purchase> purchases=new ArrayList<>();
        //return content;
        for(int i=1;i<content.size();i++){
            String[] con=content.get(i);
            try {
                int customerID= Integer.parseInt(con[1]);
                double amount= Double.parseDouble(con[3]);
                double newBalance= Double.parseDouble(con[4]);
                String comments= con.length==7 ?con[6] : "";
                purchases.add(new Purchase(customerID,amount,newBalance,con[5],comments));
            } catch (Exception ex) {
                _logger.debug("Purchase reading failed. probably cannot cast string to int");
            }
        }
        _logger.debug("Purchases imported successfully. size:  "+purchases.size());
        return purchases;
    }
    public static void exportCustomersToCsv(List<Customer> list,String location) {
        try
        {
            _logger.debug("Exporting to csv");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(location), "UTF-8"));
            //write the header
            StringBuffer oneLine = new StringBuffer();
            oneLine.append(CUSTOMER_ID_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(CUSTOMER_FIRST_NAME_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(CUSTOMER_LAST_NAME_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(CUSTOMER_MAIL_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(CUSTOMER_PHONE_TABLE_NAME);
            bw.write(oneLine.toString());
            bw.newLine();

            for (Customer product : list)
            {
                oneLine = new StringBuffer();
                oneLine.append(product.getCustomerID() <=0 ? "" : product.getCustomerID());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(product.getFirstName().trim().length() == 0? "" : product.getFirstName());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(product.getLastName().trim().length() == 0? "" : product.getLastName());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(product.getMailAddress().trim().length() == 0? "" : product.getMailAddress());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(product.getPhoneNumber().trim().length() == 0? "" : product.getPhoneNumber());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception e) {
            _logger.error("Error when trying to export to CSV. "+e.toString());
        }
        _logger.debug("Exporting to csv finished successfully");
    }
    public static List<Customer> importCustomersFromCsv(String location) {
        _logger.debug("Import Customers from CSV. location: "+location);
        List<String[]> content = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(location))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
            }
        } catch (Exception e) {
            _logger.error("Error while trying to import from csv. "+e.toString());
        }
        ArrayList<Customer> customers=new ArrayList<>();
        //return content;
        for(int i=1;i<content.size();i++){
            String[] con=content.get(i);
            customers.add(new Customer(con[1],con[2],con[3],con[4]));
        }
        _logger.debug("Customers imported successfully. size:  "+customers.size());
        return customers;
    }
    public static void exportSubscriptionsToCsv(List<Subscription> list,String location) {
        try
        {
            _logger.debug("Exporting to csv");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(location), "UTF-8"));
            //write the header
            StringBuffer oneLine = new StringBuffer();
            oneLine.append(SUBSCRIPTION_ID_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(SUBSCRIPTION_CUSTOMER_ID_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(SUBSCRIPTION_DATE_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(SUBSCRIPTION_BALANCE_TABLE_NAME);
            oneLine.append(CSV_SEPERATOR);
            oneLine.append(SUBSCRIPTION_TYPE_TABLE_NAME);
            bw.write(oneLine.toString());
            bw.newLine();

            for (Subscription subscription : list)
            {
                oneLine = new StringBuffer();
                oneLine.append(subscription.getSubscriptionID() <=0 ? "" : subscription.getSubscriptionID());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(subscription.getCoustomerID()< 0? "" : subscription.getCoustomerID());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(subscription.getSubscriptionPurchaseDate().trim().length() == 0? "" : subscription.getSubscriptionPurchaseDate());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(subscription.getBalance()< 0? "" : subscription.getBalance());
                oneLine.append(CSV_SEPERATOR);
                oneLine.append(subscription.getType().trim().length() <0? "" : subscription.getType());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception e) {
            _logger.error("Error when trying to export to CSV. "+e.toString());
        }
        _logger.debug("Exporting to csv finished successfully");
    }
    public static List<Subscription> importSubscriptionsFromCsv(String location) {
        _logger.debug("Import Subscriptions from CSV. location: "+location);
        List<String[]> content = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(location))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
            }
        } catch (Exception e) {
            _logger.error("Error while trying to import from csv. "+e.toString());
        }
        ArrayList<Subscription> subscriptions=new ArrayList<>();
        //return content;
        for(int i=1;i<content.size();i++){
            String[] con=content.get(i);
            try {
                int customerID= Integer.parseInt(con[1]);
                String date=con[2];
                double balance= Double.parseDouble(con[3]);
                String type= con[4];
                subscriptions.add(new Subscription(customerID,date,balance,type));
            } catch (Exception ex) {
                _logger.debug("Subscriptions reading failed. probably cannot cast string to int");
            }
        }
        _logger.debug("Subscriptions imported successfully. size:  "+subscriptions.size());
        return subscriptions;
    }
}
