package model;

import entities.Customer;
import entities.Purchase;
import entities.Subscription;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static model.GlobalProperties.*;
import static model.GlobalProperties.getProperty;
import static utils.Constants.*;
import static utils.MailSender.sendAsHtml;
import static utils.SQLQueries.GeneralScripts.DBToCsv;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.*;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.insertPurchaseToDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.*;
import static utils.Utils.getCurrentTimeStamp;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */
public class BasicMethods {
    public static void addCustomer(Customer customer){
        //check if this customer is already exist in DB. if yes- return message. if not - insert this new customer
        if(!thisCustomerExistingInDB(customer))
        {
            insertCustomerToDB(customer);
        }else {
            _logger.warning("Customer is already exist. "+customer.toString());
        }
    }
    public static void sendDailyReport(String date){
        //only numbers
        int numOfVIPSubscriptionsBought=0;
        int numOfMealsSubscriptionsBought=0;
        double amountOfVIPPurchases=0;
        double amountOfMealsPurchases=0;

        Date dateToCheck= StringToDate(date);

        for(Purchase p:getCachedPurchases()){
            Date p_date= StringToDate(p.getDate());
            if(p_date.getDay()==dateToCheck.getDay()&&p_date.getMonth()==dateToCheck.getMonth()&&p_date.getYear()==dateToCheck.getYear()){
                //they are from the same day
                if(p.getComments().equals(PURCHASE_SUBSCRIPTION_COMMENT)){
                    if(p.getType().equals(MEALS_SUBSCRIPTION)){
                        numOfMealsSubscriptionsBought++;
                    }else{
                        numOfVIPSubscriptionsBought++;
                    }
                }else{
                    if(p.getType().equals(MEALS_SUBSCRIPTION)){
                        amountOfMealsPurchases+=p.getAmount();
                    }else{
                        amountOfVIPPurchases+=p.getAmount();
                    }
                }
            }

        }
        String msg1=numOfMealsSubscriptionsBought+"מנויי ארוחות שנקנו: ";
        String msg2=numOfVIPSubscriptionsBought+", מנויי ויאיפי שנקנו: ";
        String msg3=amountOfMealsPurchases+", רכישות ארוחות: ";
        String msg4=amountOfVIPPurchases+", רכישות ויאיפי: ";

        sendAsHtml(getProperty(MANAGER_MAIL_ADDRESS),"סיכום יומי", msg1+", "+msg2+", "+msg3+", "+msg4,"");

        //how much meals bought
        //how much VIP bought
        //how many subscriptions bought from each one
    }
    public static void deleteCustomer(int customerId){
        //delete his subscriptions before.
        for(Subscription s : getCachedSubscriptions()){
            if(s.getCoustomerID()==customerId){
                //delete this subscription
                removeOneSubscription(s.getSubscriptionID());
            }
        }
        //finally, remove the customer
        removeOneCustomer(customerId);
    }
    public static void buySubscription(Customer customer, String type){
        //check if there is customer.
        if(thisCustomerExistingInDB(customer)){
            //check if there is subscription from this type- if yes update. if not create new
            List<Subscription> list= getAllSubscriptionFromDBWithConditions("",String.valueOf(customer.getCustomerID()),"","",type.toString());
            if(list.size()>1){
                //not valid case
                _logger.error("Customer have more than 1 subscription from on type. not valid case. Customer: "+customer);
            }
            else{
                boolean createNewSubscription=list.size()==0;
                double amount=type.equals(MEALS_SUBSCRIPTION)?Double.parseDouble(getProperty(DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT)):Double.parseDouble(getProperty(DEFAULT_VIP_SUBSCRIPTION_AMOUNT));
                if(createNewSubscription){
                    insertSubscriptionToDB(new Subscription(customer.getCustomerID(),getCurrentTimeStamp(),amount,type));
                }else{
                    //need to update the current subscription
                    updateSubscriptionBalance(list.get(0).getSubscriptionID(),list.get(0).getBalance()+amount);
                    updateSubscriptionPurchaseDate(list.get(0).getSubscriptionID(),getCurrentTimeStamp());

                }
            }

        }
    }
    public static void buyProduct(Customer customer,String type,double price){
        //check if there is customer.
        if(thisCustomerExistingInDB(customer)){
            //check if there is subscription
            List<Subscription> list=getAllSubscriptionFromDBWithConditions("",String.valueOf(customer.getCustomerID()),"","",type.toString());
            if(list.size()>1){
                //not valid case
                _logger.error("Customer have more than 1 subscription. not valid case. Customer: "+customer);
            }
            if(list.size()==1){
                //check if there is enough price
                Subscription subscription=list.get(0);
                if(subscription.getBalance()>=price){
                    //buy the product, update table
                    updateSubscriptionBalance(subscription.getSubscriptionID(),subscription.getBalance()-price);
                    //add this buying to purchase table
                    insertPurchaseToDB(new Purchase(customer.getCustomerID(),price,subscription.getBalance()-price,type,""));
                }else{
                    //do not enough balance
                    _logger.debug("Customer doesn't have enough balance . Customer: "+customer);

                }
            }
        }
    }
    private static boolean thisCustomerExistingInDB(Customer customer){
        List<Customer> customers=getAllCustomersFromDBWithConditions("",customer.getFirstName(),customer.getLastName(),customer.getMailAddress(),customer.getPhoneNumber());
        assert customers != null;
        return customers.size()==1;
    }
    private static Subscription getSubscriptionByCustomerId(int customerId,String type){
        for(Subscription s: getCachedSubscriptions()){
            if(s.getCoustomerID()==customerId && s.getType().equals(type)){
                return s;
            }
        }
        return null;
    }
    public static void addValueToCustomerSubscription(int customerId,String type,double amount){
        Subscription subscription=getSubscriptionByCustomerId(customerId,type);
        assert subscription != null;
        updateSubscriptionBalance(subscription.getSubscriptionID(),subscription.getBalance()+amount);

    }
    public static int minBetweenDates(String date1,String date2){
        Date d1;
        Date d2;
        long difference;
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMAT);
            d1=myFormat.parse(date1);
            d2=myFormat.parse(date2);
            return (int) (d1.getTime() - d2.getTime())/6000;
        } catch (Exception ex) {
            return 10000;
        }
    }
    public static Date StringToDate(String str){
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMAT);
            return myFormat.parse(str);
        } catch (Exception ex) {
            return null;
        }
    }
    public static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }
    public static void backupDataToMail(){
        DBToCsv(CUSTOMER_TABLE_LOCATION, PURCHASE_TABLE_LOCATION, SUBSCRIPTION_TABLE_LOCATION);
        sendAsHtml(getProperty(MANAGER_MAIL_ADDRESS),"גיבוי נתונים: " +getCurrentTimeStamp(), "לקוחות",CUSTOMER_TABLE_LOCATION);
        sendAsHtml(getProperty(MANAGER_MAIL_ADDRESS),"גיבוי נתונים: " +getCurrentTimeStamp(), "מנויים",SUBSCRIPTION_TABLE_LOCATION);
        sendAsHtml(getProperty(MANAGER_MAIL_ADDRESS),"גיבוי נתונים: " +getCurrentTimeStamp(), "רכישות",PURCHASE_TABLE_LOCATION);

        File file1 = new File(CUSTOMER_TABLE_LOCATION);
        File file2 = new File(PURCHASE_TABLE_LOCATION);
        File file3 = new File(SUBSCRIPTION_TABLE_LOCATION);
        file1.delete();
        file2.delete();
        file3.delete();
    }
}
