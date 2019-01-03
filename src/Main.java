import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.GlobalProperties;

import static utils.Constants.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/HomePage.fxml"));
        primaryStage.setTitle(MAIN_PROGRAM_TEXT);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        GlobalProperties.init();
        //sendDailyReport(getCurrentTimeStamp());
        launch(args);
        GlobalProperties.closeConnections();
    }
}
