package view.findCustomerPage.selectActionPage;


import entities.Subscription;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.findCustomerPage.selectActionPage.buyVipPage.BuyVipPageController;
import view.findCustomerPage.selectActionPage.loadSubscriptionPage.LoadSubscriptionPageController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.GeneralViewFunctions.alertToScreen;
import static model.GlobalProperties.formatDouble;
import static model.GlobalProperties.getCachedSubscriptions;
import static model.GlobalProperties.getCachedViewCustomer;


public class SelectActionPageController implements Initializable {

    @FXML
    Label mealsBalanceLabel;
    @FXML
    Label vipBalanceLabel;
    @FXML
    Label customerIDLabel;
    @FXML
    Button buyVIPButton;
    @FXML
    Button buyMealsButton;
    @FXML
    private void backButton(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../FindCustomerPage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void goToLoadSubscription(ActionEvent event){

        Parent homePageParent;
        Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("loadSubscriptionPage/loadSubscriptionPage.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
        }
        LoadSubscriptionPageController controller=loader.getController();
        homePageParent=loader.getRoot();
        appStage.setScene(new Scene(homePageParent));
        appStage.show();

    }

    public void goToBuyMeals(ActionEvent event){
        Parent homePageParent;
        Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("buyMealsPage/BuyMealsPage.fxml"));
        try {
            loader.load();
        } catch (Exception ignored) {
        }
        homePageParent=loader.getRoot();
        appStage.setScene(new Scene(homePageParent));
        appStage.show();
    }

    public void goToBuyVip(ActionEvent event){
        Parent homePageParent;
        Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("buyVipPage/BuyVipPage.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
        }
        BuyVipPageController controller=loader.getController();
        homePageParent=loader.getRoot();
        appStage.setScene(new Scene(homePageParent));
        appStage.show();
    }

    private void SucceededAlertAndGoToHomePage(ActionEvent event){
        alertToScreen(Alert.AlertType.INFORMATION,"אימות נתונים","מנוי נרכש בהצלחה !");

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

    private Subscription isThisCustomerHaveSubscriptionAlready(int customerID,String type){
        for (Subscription s: getCachedSubscriptions()){
            if(s.getCoustomerID()==customerID && s.getType().equals(type)){
                return s;
            }
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mealsBalanceLabel.setText("יתרת לקוח ארוחות: "+ formatDouble(getCachedViewCustomer().getMealsBalance()));
        this.vipBalanceLabel.setText("יתרת לקוח ויאיפי: "+ formatDouble(getCachedViewCustomer().getVipBalance()));
        this.customerIDLabel.setText(getCachedViewCustomer().getFirstName()+" "+getCachedViewCustomer().getLastName());

        if(getCachedViewCustomer().getVipBalance()==0){
            buyVIPButton.setDisable(true);
        }
        if(getCachedViewCustomer().getMealsBalance()==0){
            buyMealsButton.setDisable(true);
        }
        }
}

