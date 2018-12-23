package impl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static utils.Constants.MAIN_PROGRAM_TEXT;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(MAIN_PROGRAM_TEXT);
        // primaryStage.setScene(new Scene(root, 300, 275));

        Button button=new Button();
        button.setText("כפתור - לחץ עלי !!");

        StackPane layout=new StackPane();
        layout.getChildren().add(button);

        Scene scene=new Scene(layout,300,200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
