package view.homePage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.GeneralViewFunctions.alertToScreen;
import static model.GeneralViewFunctions.exit;
import static model.GlobalProperties.*;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.getAllCustomersFromDB;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.getAllPurchasesFromDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.getAllSubscriptionsFromDB;

public class HomePageController implements Initializable {

    @FXML
    private GridPane homePage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCachedSubscriptions(getAllSubscriptionsFromDB());
        setCachedPurchases(getAllPurchasesFromDB());
        setCachedCustomers(getAllCustomersFromDB());
    }

    @FXML
    private void moveToNewCustomerPage(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../newCustomerPage/NewCustomerPage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void moveToFindCustomerPage(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../findCustomerPage/FindCustomerPage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    // @FXML
    // private void moveToBuyMealPage(ActionEvent event){
    //     try {
    //         Parent homePageParent=FXMLLoader.load(getClass().getResource("../mealsPage/MealsPage.fxml"));
    //         Scene homePageScene=new Scene(homePageParent);
    //         Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    //         appStage.setScene(homePageScene);
    //         appStage.show();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }




    @FXML
    private void moveToLastPurchsesPage(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../lastPurchsesPage/LastPurchsesPage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void aboutClicked(ActionEvent event){
        alertToScreen(Alert.AlertType.INFORMATION,"אודות","התוכנה נכתבה על ידי בר בראונשטיין 2018. גרסא - 1.0 ");
    }
    @FXML
    private void exitClicked(ActionEvent event){
        exit();
    }
}

