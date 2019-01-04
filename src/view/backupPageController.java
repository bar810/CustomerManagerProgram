package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.BasicMethods;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.BasicMethods.backupDataToMail;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static utils.SQLQueries.GeneralScripts.removeAllDataFromAllDBTables;

/**
 * @author bbrownsh
 * @since 1/2/2019
 */
public class backupPageController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void uploadDataFromCsvClicked(ActionEvent event){


    }

    @FXML
    private void backupToMailClicked(ActionEvent event){
        if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך לבצע את הפעולה ?")==ButtonType.OK) {
            Thread t1 = new Thread(BasicMethods::backupDataToMail);
            t1.start();
        }
    }

    @FXML
    private void cleanDbClicked(ActionEvent event){
        if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך למחוק את כל הנתונים? הפעולה לא ניתנת לשחזור ?")==ButtonType.OK) {
            Thread t1 = new Thread(() -> {
                backupDataToMail();
                removeAllDataFromAllDBTables();
            });
            t1.start();
            goToHomeScreen(event);
        }
    }

    @FXML
    private void goBack(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("ManagmentPage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.setMaximized(true);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToHomeScreen(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.setMaximized(true);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
