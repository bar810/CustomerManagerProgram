package view;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author bbrownsh
 * @since 1/6/2019
 */
public abstract  class AbstractView implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    void goTo(Event event, String path){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource(path));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parent homePageParent;
        // Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //
        // FXMLLoader loader=new FXMLLoader();
        // loader.setLocation(getClass().getResource("SelectActionPage.fxml"));
        // try {
        //     loader.load();
        // } catch (Exception ex) {
        // }
        // homePageParent=loader.getRoot();
        // appStage.setScene(new Scene(homePageParent));
        // appStage.show();
    }
}
