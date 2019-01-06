package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.BasicMethods;

import static model.BasicMethods.backupDataToMail;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static utils.SQLQueries.GeneralScripts.removeAllDataFromAllDBTables;

/**
 * @author bbrownsh
 * @since 1/2/2019
 */
public class backupPageController extends AbstractView {

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
        goTo(event,"ManagmentPage.fxml");
    }

    private void goToHomeScreen(ActionEvent event){
        goTo(event,"HomePage.fxml");
    }
}
