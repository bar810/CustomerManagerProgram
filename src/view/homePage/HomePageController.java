package view.homePage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private GridPane homePage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
    private void moveToLoadSubscriptionPage(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../loadSubscriptionPage/LoadSubscriptionPage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

