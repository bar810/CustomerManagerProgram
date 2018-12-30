package view.findCustomerPage.selectActionPage.buyVipPage;


import entities.Purchase;
import entities.Subscription;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utils.Constants.VIP_SUBSCRIPTION;
import static model.GeneralViewFunctions.alertToScreen;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static utils.GlobalProperties.getCachedViewCustomer;
import static utils.GlobalProperties.getSubscriptionByCustoemrID;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.insertPurchaseToDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.updateSubscriptionBalance;

public class BuyVipPageController implements Initializable {

    @FXML
    Label customer_balance_label;
    @FXML
    Label customer_name_label;
    @FXML
    TextField amountToUse;
    @FXML
    Button buyButton;

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
    private void buyClicked(ActionEvent event){
        String amount=amountToUse.getText();
        try {
            double amountAsDouble=Double.parseDouble(amount);
            if(amountAsDouble>getCachedViewCustomer().getVipBalance()){
                alertToScreen(Alert.AlertType.INFORMATION,"שגיאה","אין מספיק יתרה");
            }else{
                if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך לאשר את הפעולה ?")==ButtonType.OK){
                    //
                    double newBalance=getCachedViewCustomer().getVipBalance()-amountAsDouble;
                    Subscription subscription=getSubscriptionByCustoemrID(getCachedViewCustomer().getCustomerID(),VIP_SUBSCRIPTION);
                    if(subscription==null){
                        alertToScreen(Alert.AlertType.INFORMATION,"שגיאה","שגיאה במהלך הקנייה. לא נמצא מנוי. לא בוצעה רכישה");
                        goToHomeScreen(event);
                    }
                    updateSubscriptionBalance(subscription.getSubscriptionID(),newBalance);
                    insertPurchaseToDB(new Purchase(getCachedViewCustomer().getCustomerID(),amountAsDouble,newBalance));
                    alertToScreen(Alert.AlertType.INFORMATION,"רכישה","רכישה בוצעה בהצלחה");
                    goToHomeScreen(event);
                    //
                }else{
                    this.backButton(event);
                }
            }
        } catch (Exception ex) {
            alertToScreen(Alert.AlertType.INFORMATION,"שגיאה","יתן להכניס מספרים בלבד");
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double balance=getCachedViewCustomer().getMealsBalance();
        customer_name_label.setText(getCachedViewCustomer().getFirstName()+" "+getCachedViewCustomer().getLastName());
        customer_balance_label.setText("יתרת הלקוח: "+String.valueOf(balance)+" ארוחות");
        }
}

