package view.findCustomerPage.selectActionPage.buyMealsPage;


import entities.Purchase;
import entities.ViewCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utils.Constants.*;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.insertPurchaseToDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.updateSubscriptionBalance;

public class BuyMealsPageController implements Initializable {

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
    private ViewCustomer Customer;

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

    @FXML
    private void MealsChosen(ActionEvent event){
        String source1 = event.getSource().toString();
        source1=source1.split("id=")[1];
        source1=source1.split(",")[0];
        switch (source1) {
            case "oneMealButton":
                tryToMakePurchase(event,MEAL_PRICE);
                break;
            case "oneHotMealButton":
                tryToMakePurchase(event,HOT_MEAL_PRICE);
                break;
            case "oneHotMealAndDrinkButton":
                tryToMakePurchase(event,HOT_MEAL_PRICE+DRINK_PRICE);
                break;
            case "oneDrinkButton":
                tryToMakePurchase(event,DRINK_PRICE);
                break;
            case "twoMealButton":
                tryToMakePurchase(event,2*MEAL_PRICE);
                break;
            case "twoHotMealButton":
                tryToMakePurchase(event,2*HOT_MEAL_PRICE);
                break;
            case "twoHotMealAndDrinkButton":
                tryToMakePurchase(event,2*(HOT_MEAL_PRICE+DRINK_PRICE));
                break;
            case "twoDrinkButton":
                tryToMakePurchase(event,2*DRINK_PRICE);
                break;
            default:
                //TODO
                break;
        }
    }

    private void goToHomeScreen(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../../../homePage/HomePage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToMakePurchase(ActionEvent event,double amount) {
        //check if there is enough balance
        if (Customer.getMealsBalance() < amount) {
            //TODO error
        }
        //TODO :: ask again
        double newBalance=Customer.getMealsBalance()-amount;
        updateSubscriptionBalance(Customer.getCustomerID(),newBalance);
        insertPurchaseToDB(new Purchase(Customer.getCustomerID(),amount,newBalance));
        //TODO :: message to screen and go to home screen
        goToHomeScreen(event);

    }
    public void setCustomer(ViewCustomer c){
        this.Customer=c;
    }

    public void setLabel(String fn,String ln ,double balance){
        customer_name_label.setText(fn+" "+ln);
        customer_balance_label.setText("יתרת הלקוח: "+String.valueOf(balance)+" ארוחות");

        if(balance<MEAL_PRICE){
            oneMealButton.setDisable(true);
        }
        if(balance<HOT_MEAL_PRICE){
            oneHotMealButton.setDisable(true);
        }
        if(balance<HOT_MEAL_PRICE+DRINK_PRICE){
            oneHotMealAndDrinkButton.setDisable(true);
        }
        if(balance<DRINK_PRICE){
            oneDrinkButton.setDisable(true);
        }
        if(balance<2*MEAL_PRICE){
            twoMealButton.setDisable(true);
        }
        if(balance<2*HOT_MEAL_PRICE){
            twoHotMealButton.setDisable(true);
        }
        if(balance<2*(HOT_MEAL_PRICE+DRINK_PRICE)){
            twoHotMealAndDrinkButton.setDisable(true);
        }
        if(balance<2*DRINK_PRICE){
            twoDrinkButton.setDisable(true);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}

