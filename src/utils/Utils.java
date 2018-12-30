/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.Constants.DATE_FORMAT;
import static model.GlobalProperties.*;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
public class Utils {

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
            return  get_customerFactory().getCurrentSession();
        } catch (Exception ex) {
            _logger.error("Exception while trying to get customer session. "+ ex.toString());
            return null;
        }
    }
    public static Session getPurchaseSession(){
        try {
            return  get_purchaseFactory().getCurrentSession();
        } catch (Exception ex) {
            _logger.error("Exception while trying to get purchase session. "+ ex.toString());
            return null;
        }
    }
    public static Session getSubscriptionSession(){
        try {
            return get_SubscriptionFactory().getCurrentSession();
        } catch (Exception ex) {
            _logger.error("Exception while trying to get subscription session. "+ ex.toString());
            return null;
        }
    }

    public static boolean isValidMail(String mail){
        if(mail.isEmpty()){
            return true;
        }
        return mail.contains("@")&&mail.contains(".");
    }
    public static boolean isValidPhone(String phone){
        if(phone.isEmpty()){
            return true;
        }
        phone=phone.replaceAll("-","");
        if(phone.length()<9 || phone.length()>10){
            return false;
        }
        int phoneAsInt;
        try {
            phoneAsInt=Integer.parseInt(phone);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

}
