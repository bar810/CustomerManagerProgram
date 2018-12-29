package view.findCustomerPage.selectActionPage.loadSubscriptionPage;


import entities.Subscription;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static utils.Constants.*;
import static utils.GeneralViewFunctions.alertToScreen;
import static utils.GlobalProperties.getCachedSubscriptions;
import static utils.GlobalProperties.getCachedViewCustomer;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.insertSubscriptionToDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.updateSubscriptionBalance;

public class LoadSubscriptionPageController implements Initializable {

    @FXML
    private void backButton(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../../selectActionPage/SelectActionPage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean alertForSubscription(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("אישור פעולה");
        alert.setHeaderText(null);
        alert.setContentText("האם אתה בטוח שברצונך לאשר את הפעולה ?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    @FXML
    private void loadMeals(ActionEvent event){
     if(alertForSubscription()){
         //check if there is subscription. - if yes just reload if no create new.
         Subscription subscription=isThisCustomerHaveSubscriptionAlready(getCachedViewCustomer().getCustomerID(),MEALS_SUBSCRIPTION);
         if(subscription!=null){
             //reload
             updateSubscriptionBalance(subscription.getSubscriptionID(),subscription.getBalance()+DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT);
         }
         else{
             //create new
             insertSubscriptionToDB(new Subscription(getCachedViewCustomer().getCustomerID(),DEFAULT_MEALS_SUBSCRIPTION_MEALS_AMOUNT,MEALS_SUBSCRIPTION));
         }
         SucceededAlertAndGoToHomePage(event);
     }
     else{
         this.backButton(event);
     }
    }

    @FXML
    private void loadVIP(ActionEvent event){
        if(alertForSubscription()){
            //check if there is subscription. - if yes just reload if no create new.
            Subscription subscription=isThisCustomerHaveSubscriptionAlready(getCachedViewCustomer().getCustomerID(),VIP_SUBSCRIPTION);
            if(subscription!=null){
                //reload
                updateSubscriptionBalance(subscription.getSubscriptionID(),subscription.getBalance()+DEFAULT_VIP_SUBSCRIPTION_AMOUNT);
            }
            else{
                //create new
                insertSubscriptionToDB(new Subscription(getCachedViewCustomer().getCustomerID(),DEFAULT_VIP_SUBSCRIPTION_AMOUNT,VIP_SUBSCRIPTION));
            }
            //Alert
            SucceededAlertAndGoToHomePage(event);
        }
        else{
            this.backButton(event);
        }
    }

    private void SucceededAlertAndGoToHomePage(ActionEvent event){
        alertToScreen(Alert.AlertType.INFORMATION,"אימות נתונים","מנוי נרכש בהצלחה !");
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../../../../view/homePage/HomePage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Subscription isThisCustomerHaveSubscriptionAlready(int customerID,String type){
        for (Subscription s: getCachedSubscriptions()){
            if(s.getCoustomerID()==customerID && s.getType().equals(type)){
                return s;
            }
        }
        return null;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}

