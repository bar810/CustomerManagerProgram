package view.findCustomerPage.selectActionPage.buyVipPage;


import entities.Subscription;
import entities.ViewCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BuyVipPageController implements Initializable {

    private ViewCustomer Customer;
    private List<Subscription> subscriptions;

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


    private void SucceededAlertAndGoToHomePage(ActionEvent event){
        // alertToScreen(Alert.AlertType.INFORMATION,"אימות נתונים","נרכש בהצלחה !");
        // try {
        //     Parent homePageParent=FXMLLoader.load(getClass().getResource("../../../../view/homePage/HomePage.fxml"));
        //     Scene homePageScene=new Scene(homePageParent);
        //     Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //     appStage.setScene(homePageScene);
        //     appStage.show();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    private Subscription isThisCustomerHaveSubscriptionAlready(int customerID,String type){
        for (Subscription s: subscriptions){
            if(s.getCoustomerID()==customerID && s.getType().equals(type)){
                return s;
            }
        }
        return null;
    }

    public void setCustomer(ViewCustomer c){
        this.Customer=c;
    }
    public void setSubscriptions(List<Subscription> list){
        this.subscriptions=list;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        }
}

