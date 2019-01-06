package view;


import entities.Purchase;
import entities.Subscription;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import static model.GeneralViewFunctions.alertToScreen;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static model.GlobalProperties.*;
import static utils.Constants.*;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.insertPurchaseToDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.insertSubscriptionToDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.updateSubscriptionBalance;

public class LoadSubscriptionPageController extends AbstractView {

    @FXML
    private void backButton(ActionEvent event){
        goTo("SelectActionPage.fxml");
    }
    @FXML
    private void loadMeals(ActionEvent event){
     if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך לאשר את הפעולה ?")==ButtonType.OK){
         //check if there is subscription. - if yes just reload if no create new.
         Subscription subscription=isThisCustomerHaveSubscriptionAlready(getCachedViewCustomer().getCustomerID(),MEALS_SUBSCRIPTION);
         double newBalnce;
         if(subscription!=null){
             //reload
             updateSubscriptionBalance(subscription.getSubscriptionID(),subscription.getBalance()+Double.parseDouble(getProperty(DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT)));
             newBalnce=subscription.getBalance()+Double.parseDouble(getProperty(DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT));
         }
         else{
             //create new
             insertSubscriptionToDB(new Subscription(getCachedViewCustomer().getCustomerID(),Double.parseDouble(getProperty(DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT)),MEALS_SUBSCRIPTION));
             newBalnce=Double.parseDouble(getProperty(DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT));
         }
         insertPurchaseToDB(new Purchase(getCachedViewCustomer().getCustomerID(),Double.parseDouble(getProperty(DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT)),newBalnce,MEALS_SUBSCRIPTION,PURCHASE_SUBSCRIPTION_COMMENT));
         SucceededAlertAndGoToHomePage(event);
     }
     else{
         this.backButton(event);
     }
    }
    @FXML
    private void loadVIP(ActionEvent event){
        if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך לאשר את הפעולה ?")==ButtonType.OK){
            //check if there is subscription. - if yes just reload if no create new.
            Subscription subscription=isThisCustomerHaveSubscriptionAlready(getCachedViewCustomer().getCustomerID(),VIP_SUBSCRIPTION);
            double newBalnce;
            if(subscription!=null){
                //reload
                updateSubscriptionBalance(subscription.getSubscriptionID(),subscription.getBalance()+Double.parseDouble(getProperty(DEFAULT_VIP_SUBSCRIPTION_AMOUNT)));
                newBalnce=subscription.getBalance()+Double.parseDouble(getProperty(DEFAULT_VIP_SUBSCRIPTION_AMOUNT));

            }
            else{
                //create new
                insertSubscriptionToDB(new Subscription(getCachedViewCustomer().getCustomerID(),Double.parseDouble(getProperty(DEFAULT_VIP_SUBSCRIPTION_AMOUNT)),VIP_SUBSCRIPTION));
                newBalnce=Double.parseDouble(getProperty(DEFAULT_VIP_SUBSCRIPTION_AMOUNT));

            }
            insertPurchaseToDB(new Purchase(getCachedViewCustomer().getCustomerID(),Double.parseDouble(getProperty(DEFAULT_VIP_SUBSCRIPTION_AMOUNT)),newBalnce,VIP_SUBSCRIPTION,PURCHASE_SUBSCRIPTION_COMMENT));
            SucceededAlertAndGoToHomePage(event);
        }
        else{
            this.backButton(event);
        }
    }
    private void SucceededAlertAndGoToHomePage(ActionEvent event){
        alertToScreen(Alert.AlertType.INFORMATION,"אימות נתונים","מנוי נרכש בהצלחה !");
        goTo("HomePage.fxml");
    }
    private Subscription isThisCustomerHaveSubscriptionAlready(int customerID,String type){
        for (Subscription s: getCachedSubscriptions()){
            if(s.getCoustomerID()==customerID && s.getType().equals(type)){
                return s;
            }
        }
        return null;
    }
}

