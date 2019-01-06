package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

import static model.GeneralViewFunctions.*;
import static model.GlobalProperties.*;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.getAllCustomersFromDB;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.getAllPurchasesFromDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.getAllSubscriptionsFromDB;

public class HomePageController extends AbstractView {

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
        goTo("NewCustomerPage.fxml");
    }
    @FXML
    private void moveToFindCustomerPage(ActionEvent event){
        goTo("FindCustomerPage.fxml");
    }
    @FXML
    private void goToAuth(ActionEvent event){
        goTo("LoginPage.fxml");
    }
    @FXML
    private void moveToLastPurchsesPage(ActionEvent event){
        goTo("LastPurchasesPage.fxml");

    }
    @FXML
    private void aboutClicked(ActionEvent event){
        alertToScreen(Alert.AlertType.INFORMATION,"אודות","התוכנה נכתבה על ידי בר בראונשטיין 2018. גרסא - 1.0 ");
    }
    @FXML
    private void exitClicked(ActionEvent event){
        if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך לסגור את התוכנה ?")==ButtonType.OK) {
            exit();
        }
    }
}

