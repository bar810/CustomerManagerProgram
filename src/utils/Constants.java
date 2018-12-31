/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
public final class Constants {


    //properties
    public static final String DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT = "default_subscription_meals_amount";
    public static final String DEFAULT_VIP_SUBSCRIPTION_AMOUNT = "default_subscription_vip_amount";
    public static final String MEAL_PRICE ="meal_price";
    public static final String HOT_MEAL_PRICE ="hot_meal_price";
    public static final String DRINK_PRICE ="drink_price";
    public static final String SAVE_LOG = "save_log";
    public static final String LOG_MAX_SIZE = "max_log_size";
    public static final String MAIL_USER_NAME = "mail.user.name";
    public static final String MAIL_PASSWORD= "mail.password";


    //Texts
    public static final String MAIN_PROGRAM_TEXT = "שלי אוכל מוכן - ניהול מנויים";
    public static final String PURCHASE_SUBSCRIPTION_COMMENT = "רכישת מנוי";

    //format
    public static final String CSV_SEPERATOR = ",";
    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String LOG_FILE_NAME_EXTENSION = "log";

    //Subscription types
    public static final String MEALS_SUBSCRIPTION = "ארוחות";
    public static final String VIP_SUBSCRIPTION = "VIP";

    //Locations for files
    public static final String CUSTOMER_TABLE_LOCATION = "customers.csv";
    public static final String PURCHASE_TABLE_LOCATION = "purchases.csv";
    public static final String SUBSCRIPTION_TABLE_LOCATION = "subscriptions.csv";
    public static final String SQL_CONNECTION_CONFIGURE_FILE_PATH = "utils/hibernate.cfg.xml";
    public static final String CONFIGURE_FILE_PATH = "dataConfig.properties";

    //Customer table names
    public static final String CUSTOMER_TABLE_NAME = "לקוחות";
    public static final String CUSTOMER_ID_TABLE_NAME = "מספר_לקוח";
    public static final String CUSTOMER_FIRST_NAME_TABLE_NAME = "שם_פרטי";
    public static final String CUSTOMER_LAST_NAME_TABLE_NAME = "שם_משפחה";
    public static final String CUSTOMER_MAIL_TABLE_NAME = "מייל";
    public static final String CUSTOMER_PHONE_TABLE_NAME = "טלפון";

    //Purchase table names
    public static final String PURCHASE_TABLE_NAME = "רכישות";
    public static final String PURCHASE_ID_TABLE_NAME = "מספר_רכישה";
    public static final String PURCHASE_CUSTOMER_ID_TABLE_NAME = "מספר_לקוח";
    public static final String PURCHASE_DATE_TABLE_NAME = "תאריך_רכישה";
    public static final String PURCHASE_TYPE_TABLE_NAME = "סוג_מנוי";
    public static final String PURCHASE_COMMENTS_TABLE_NAME = "הערות";
    public static final String PURCHASE_AMOUNT_TABLE_NAME = "סכום_רכישה";
    public static final String PURCHASE_NEW_BALANCE_TABLE_NAME = "יתרת_לקוח";

    //Subscription table names
    public static final String SUBSCRIPTION_TABLE_NAME = "מנויים";
    public static final String SUBSCRIPTION_ID_TABLE_NAME = "מספר_מנוי";
    public static final String SUBSCRIPTION_CUSTOMER_ID_TABLE_NAME = "מספר_לקוח";
    public static final String SUBSCRIPTION_DATE_TABLE_NAME = "תאריך_רכישת_מנוי";
    public static final String SUBSCRIPTION_BALANCE_TABLE_NAME = "יתרה";
    public static final String SUBSCRIPTION_TYPE_TABLE_NAME = "סוג_מנוי";
}
