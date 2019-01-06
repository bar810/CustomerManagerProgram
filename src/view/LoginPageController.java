package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static model.GeneralViewFunctions.alertToScreen;
import static model.GlobalProperties.getProperty;
import static utils.Constants.PROGRAM_PASSWORD;
import static utils.Constants.PROGRAM_USER;

/**
 * @author bbrownsh
 * @since 1/1/2019
 */
public class LoginPageController extends AbstractView {


    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;

    @FXML
    private void goBack(ActionEvent event){
        goTo(event,"HomePage.fxml");
    }

    @FXML
    private void tryToAuth(ActionEvent event){
        String userName=user.getText();
        String password=pass.getText();
        String userFromProperties= getProperty(PROGRAM_USER);
        String passFromProperties= getProperty(PROGRAM_PASSWORD);
        if(userName.equals(userFromProperties) && password.equals(passFromProperties)){
            goTo(event,"ManagmentPage.fxml");
        }else{
            alertToScreen(Alert.AlertType.INFORMATION,"שגיאה","שם משתמש או סיסמא לא נכונים");

        }

    }
}
