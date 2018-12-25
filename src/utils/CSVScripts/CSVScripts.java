/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils.CSVScripts;

import entities.Customer;
import entities.Purchase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.CSV_SEPERATOR;
import static utils.GlobalProperties._logger;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */
public class CSVScripts {

    public static void exportPurchasesToCsv(ArrayList<Purchase> list) {
        try
        {
            _logger.debug("Exporting to csv");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Purchases.csv"), "UTF-8"));
            //write the header
            StringBuffer oneLine = new StringBuffer();
            oneLine.append("ID");
            oneLine.append(CSV_SEPERATOR);
            oneLine.append("Customer ID");
            oneLine.append(CSV_SEPERATOR);
            oneLine.append("Date");
            oneLine.append(CSV_SEPERATOR);
            oneLine.append("amount");
            oneLine.append(CSV_SEPERATOR);
            oneLine.append("new balance");
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
    public static ArrayList<Purchase> importPurchasesFromCsv(String location) {
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
                purchases.add(new Purchase(customerID,amount,newBalance));
            } catch (Exception ex) {
                _logger.debug("Purchase reading failed. probably cannot cast string to int");
            }
        }
        _logger.debug("Purchases imported successfully. size:  "+purchases.size());
        return purchases;
    }



    public static void exportCustomersToCsv(ArrayList<Customer> list) {
        try
        {
            _logger.debug("Exporting to csv");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Customers.csv"), "UTF-8"));
            //write the header
            StringBuffer oneLine = new StringBuffer();
            oneLine.append("ID");
            oneLine.append(CSV_SEPERATOR);
            oneLine.append("First Name");
            oneLine.append(CSV_SEPERATOR);
            oneLine.append("Last Name");
            oneLine.append(CSV_SEPERATOR);
            oneLine.append("Mail Adress");
            oneLine.append(CSV_SEPERATOR);
            oneLine.append("Phone Number");
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
    public static ArrayList<Customer> importCustomersFromCsv(String location) {
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
}
