package view.loginPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.GeneralViewFunctions.alertToScreen;
import static model.GlobalProperties.getProperty;
import static utils.Constants.PROGRAM_PASSWORD;
import static utils.Constants.PROGRAM_USER;

/**
 * @author bbrownsh
 * @since 1/1/2019
 */
public class LoginPageController implements Initializable {


    @FXML
    private TextField user;
    @FXML
    private TextField pass;



    @FXML
    private void goBack(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../homePage/HomePage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tryToAuth(ActionEvent event){
        String userName=user.getText();
        String password=pass.getText();
        String userFromProperties= getProperty(PROGRAM_USER);
        String passFromProperties= getProperty(PROGRAM_PASSWORD);
        if(userName.equals(userFromProperties) && password.equals(passFromProperties)){
            try {
                Parent homePageParent=FXMLLoader.load(getClass().getResource("../managmentPage/ManagmentPage.fxml"));
                Scene homePageScene=new Scene(homePageParent);
                Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                appStage.setScene(homePageScene);
                appStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            alertToScreen(Alert.AlertType.INFORMATION,"שגיאה","שם משתמש או סיסמא לא נכונים");

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}
