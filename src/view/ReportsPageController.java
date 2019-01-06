
package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.ResourceBundle;

import static model.BasicMethods.sendDailyReport;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static utils.Utils.getCurrentTimeStamp;

/**
 * @author bbrownsh
 * @since 1/2/2019
 */
public class ReportsPageController  extends AbstractView {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void sendDailyReportClciked(ActionEvent event){
        if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך לבצע את הפעולה ?")==ButtonType.OK) {
            Thread t1 = new Thread(() -> sendDailyReport(getCurrentTimeStamp()));
            t1.start();
            goTo(event,"HomePage.fxml");
        }
    }

    @FXML
    private void goBack(ActionEvent event){
        goTo(event,"ManagmentPage.fxml");
    }
}
