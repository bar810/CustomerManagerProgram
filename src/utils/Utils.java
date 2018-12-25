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
import static utils.GlobalProperties.*;

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
            return _SubscriptionFactory.getCurrentSession();
        } catch (Exception ex) {
            _logger.error("Exception while trying to get subscription session. "+ ex.toString());
            return null;
        }
    }
}
