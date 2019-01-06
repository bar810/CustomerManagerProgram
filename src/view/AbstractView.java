package view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.GlobalProperties.setScene;

/**
 * @author bbrownsh
 * @since 1/6/2019
 */
public abstract  class AbstractView implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    void goTo(String path){
        try {
            setScene(FXMLLoader.load(getClass().getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
