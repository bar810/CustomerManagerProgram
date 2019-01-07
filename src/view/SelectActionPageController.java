package view;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static model.GlobalProperties.formatDouble;
import static model.GlobalProperties.getCachedViewCustomer;


public class SelectActionPageController extends AbstractView {

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
        goTo("FindCustomerPage.fxml");
    }
    @FXML
    private void goToLoadSubscription(ActionEvent event){
        goTo("LoadSubscriptionPage.fxml");
    }
    @FXML
    private void goToBuyMeals(ActionEvent event){
        goTo("BuyMealsPage.fxml");
    }
    @FXML
    private void goToBuyVip(ActionEvent event){
        goTo("BuyVipPage.fxml");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mealsBalanceLabel.setText("יתרת לקוח ארוחות: "+ formatDouble(Double.parseDouble(getCachedViewCustomer().getMealsBalance())));
        this.vipBalanceLabel.setText("יתרת לקוח ויאיפי: "+ formatDouble(Double.parseDouble(getCachedViewCustomer().getVipBalance())));
        this.customerIDLabel.setText(getCachedViewCustomer().getFirstName()+" "+getCachedViewCustomer().getLastName());

        if(Double.parseDouble(getCachedViewCustomer().getVipBalance())==0){
            buyVIPButton.setDisable(true);
        }
        if(Double.parseDouble(getCachedViewCustomer().getMealsBalance())==0){
            buyMealsButton.setDisable(true);
        }
        }
}

