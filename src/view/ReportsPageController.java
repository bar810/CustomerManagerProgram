
package view;

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
import java.util.ResourceBundle;

import static model.BasicMethods.sendDailyReport;
import static utils.Utils.getCurrentTimeStamp;

/**
 * @author bbrownsh
 * @since 1/2/2019
 */
public class ReportsPageController  implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void sendDailyReportClciked(ActionEvent event){
        sendDailyReport(getCurrentTimeStamp());
    }

    @FXML
    private void goBack(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("ManagmentPage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
