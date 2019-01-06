package view;


import entities.Purchase;
import entities.Subscription;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static model.GeneralViewFunctions.alertToScreen;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static model.GlobalProperties.*;
import static utils.Constants.*;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.insertPurchaseToDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.updateSubscriptionBalance;

public class BuyMealsPageController extends AbstractView {

    @FXML
    Label customer_balance_label;
    @FXML
    Label customer_name_label;
    @FXML
    Button oneMealButton;
    @FXML
    Button oneHotMealButton;
    @FXML
    Button oneHotMealAndDrinkButton;
    @FXML
    Button oneDrinkButton;
    @FXML
    Button twoMealButton;
    @FXML
    Button twoHotMealButton;
    @FXML
    Button twoHotMealAndDrinkButton;
    @FXML
    Button twoDrinkButton;

    @FXML
    private void backButton(ActionEvent event){
        goTo("SelectActionPage.fxml");
    }
    @FXML
    private void MealsChosen(ActionEvent event){
        String source1 = event.getSource().toString();
        source1=source1.split("id=")[1];
        source1=source1.split(",")[0];
        switch (source1) {
            case "oneMealButton":
                tryToMakePurchase(event,Double.parseDouble(getProperty(MEAL_PRICE)));
                break;
            case "oneHotMealButton":
                tryToMakePurchase(event,Double.parseDouble(getProperty(HOT_MEAL_PRICE)));
                break;
            case "oneHotMealAndDrinkButton":
                tryToMakePurchase(event,Double.parseDouble(getProperty(HOT_MEAL_PRICE))+Double.parseDouble(getProperty(DRINK_PRICE)));
                break;
            case "oneDrinkButton":
                tryToMakePurchase(event,Double.parseDouble(getProperty(DRINK_PRICE)));
                break;
            case "twoMealButton":
                tryToMakePurchase(event,2*Double.parseDouble(getProperty(MEAL_PRICE)));
                break;
            case "twoHotMealButton":
                tryToMakePurchase(event,2*Double.parseDouble(getProperty(HOT_MEAL_PRICE)));
                break;
            case "twoHotMealAndDrinkButton":
                tryToMakePurchase(event,2*(Double.parseDouble(getProperty(HOT_MEAL_PRICE))+Double.parseDouble(getProperty(DRINK_PRICE))));
                break;
            case "twoDrinkButton":
                tryToMakePurchase(event,2*Double.parseDouble(getProperty(DRINK_PRICE)));
                break;
            default:
                _logger.error("cannot parse what meal the customer choose. (should not be happen)");
                break;
        }
    }

    private void tryToMakePurchase(ActionEvent event, double amount) {
        //check if there is enough balance
        if (getCachedViewCustomer().getMealsBalance() < amount) {
            alertToScreen(Alert.AlertType.INFORMATION,"שגיאה","ללקוח אין מספיק יתרה");
            goTo("HomePage.fxml");
        }
        if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך לאשר את הפעולה ?")==ButtonType.OK){
            double newBalance=getCachedViewCustomer().getMealsBalance()-amount;
            Subscription subscription= getSubscriptionByCustomerID(getCachedViewCustomer().getCustomerID(),MEALS_SUBSCRIPTION);
            if(subscription==null){
                alertToScreen(Alert.AlertType.INFORMATION,"שגיאה","שגיאה במהלך הקנייה.לא נמצא מנוי. לא בוצעה רכישה");
                goTo("HomePage.fxml");
                }
            updateSubscriptionBalance(subscription.getSubscriptionID(),newBalance);
            insertPurchaseToDB(new Purchase(getCachedViewCustomer().getCustomerID(),amount,newBalance,MEALS_SUBSCRIPTION,""));
            alertToScreen(Alert.AlertType.INFORMATION,"רכישה","רכישה בוצעה בהצלחה");
            goTo("HomePage.fxml");
        }else{
            this.backButton(event);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double balance=getCachedViewCustomer().getMealsBalance();
        customer_name_label.setText(getCachedViewCustomer().getFirstName()+" "+getCachedViewCustomer().getLastName());
        customer_balance_label.setText("יתרת הלקוח: "+String.valueOf(balance)+" ארוחות");

        if(balance<Double.parseDouble(getProperty(MEAL_PRICE))){
            oneMealButton.setDisable(true);
        }
        if(balance<Double.parseDouble(getProperty(HOT_MEAL_PRICE))){
            oneHotMealButton.setDisable(true);
        }
        if(balance<Double.parseDouble(getProperty(HOT_MEAL_PRICE))+Double.parseDouble(getProperty(DRINK_PRICE))){
            oneHotMealAndDrinkButton.setDisable(true);
        }
        if(balance<Double.parseDouble(getProperty(DRINK_PRICE))){
            oneDrinkButton.setDisable(true);
        }
        if(balance<2*Double.parseDouble(getProperty(MEAL_PRICE))){
            twoMealButton.setDisable(true);
        }
        if(balance<2*Double.parseDouble(getProperty(HOT_MEAL_PRICE))){
            twoHotMealButton.setDisable(true);
        }
        if(balance<2*(Double.parseDouble(getProperty(HOT_MEAL_PRICE))+Double.parseDouble(getProperty(DRINK_PRICE)))){
            twoHotMealAndDrinkButton.setDisable(true);
        }
        if(balance<2*Double.parseDouble(getProperty(DRINK_PRICE))){
            twoDrinkButton.setDisable(true);
        }
    }
}

