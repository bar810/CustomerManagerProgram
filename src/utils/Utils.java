/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import entities.customer.Customer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static utils.Constants.CSV_SEPERATOR;
import static utils.Constants.DATE_FORMAT;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
public class Utils {

    //TODO export to json and from json

    public static void exportToCsv(ArrayList<Customer> list) {
        try
        {

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
            //TODO add log file
        }


    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
