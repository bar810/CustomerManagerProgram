/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import entities.customer.Customer;
import org.hibernate.Session;
import sun.reflect.annotation.ExceptionProxy;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static utils.Constants.CSV_SEPERATOR;
import static utils.Constants.DATE_FORMAT;
import static utils.GlobalProperties.*;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
public class Utils {

    public static void exportToCsv(ArrayList<Customer> list) {
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

    public static ArrayList<Customer> importFromCsv(String location) {
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

    public static String getCurrentTimeStamp() {
        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT);//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);
            return strDate;
        } catch (Exception ex) {
            _logger.error("Error when trying to get current time. "+ ex.toString());
        }
        return null;
    }

    public static boolean isNotNullOrEmpty(String s){
        return s!=null && (!s.isEmpty());
    }

    public static Session getCustomerSession(){
        try {
            return  _customerFactory.getCurrentSession();
        } catch (Exception ex) {
            _logger.error("Exception while trying to get customer session. "+ ex.toString());
            return null;
        }
    }
    public static Session getPurchaseSession(){
        try {
            return  _purchaseFactory.getCurrentSession();
        } catch (Exception ex) {
            _logger.error("Exception while trying to get purchase session. "+ ex.toString());
            return null;
        }
    }
    public static Session getSubscriptionSession(){
        try {
            return  _subscriptionFactory.getCurrentSession();
        } catch (Exception ex) {
            _logger.error("Exception while trying to get subscription session. "+ ex.toString());
            return null;
        }
    }
}
