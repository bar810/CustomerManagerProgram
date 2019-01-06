import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.GlobalProperties;

import static model.GlobalProperties.getScene;
import static model.GlobalProperties.initScene;
import static utils.Constants.MAIN_PROGRAM_TEXT;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(MAIN_PROGRAM_TEXT);
        initScene(FXMLLoader.load(getClass().getResource("view/HomePage.fxml")));
        primaryStage.setScene(getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        GlobalProperties.init();
        launch(args);
        GlobalProperties.closeConnections();
    }
}
