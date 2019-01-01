package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * @author bbrownsh
 * @since 12/29/2018
 */
public class GeneralViewFunctions {
    public static void alertToScreen(Alert.AlertType type, String title, String content){
        Alert alert=new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static ButtonType alertToScreenWithResponse(Alert.AlertType type, String title, String content){
        Alert alert=new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get();//ButtonType.OK;
    }
    public static void exit(){
        GlobalProperties.closeConnections();
        System.exit(0);
    }
}
