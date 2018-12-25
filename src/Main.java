import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.GlobalProperties;

import static utils.Constants.*;
import static utils.GlobalProperties._logger;
import static utils.SQLQueries.GeneralScripts.csvToDB;
import static utils.SQLQueries.GeneralScripts.removeAllDataFromAllDBTables;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("impl/sample.fxml"));
        primaryStage.setTitle(MAIN_PROGRAM_TEXT);
        // primaryStage.setScene(new Scene(root, 300, 275));

        Button button=new Button();
        button.setText("כפתור - לחץ עלי !!");

        Button button2=new Button();
        button2.setText("כפץור 2");

        StackPane layout=new StackPane();
        layout.getChildren().add(button);
        layout.getChildren().add(button2);

        Scene scene=new Scene(layout,500,400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        GlobalProperties.init();
        _logger.debug("Program Started");
        //launch(args);

        removeAllDataFromAllDBTables();
        // Customer bar=new Customer("Bar","Brownshtein","00","00");
        // Customer gal=new Customer("Gal","Brownshtein","00","00");
        // addCustomer(bar);
        // addCustomer(gal);
        // buySubscription(bar,VIP_SUBSCRIPTION);
        // buySubscription(gal, MEALS_SUBSCRIPTION);
        //
        // buyProduct(bar,VIP_SUBSCRIPTION,50);
        // buyProduct(gal,MEALS_SUBSCRIPTION,1);
        //
        // DBToCsv(CUSTOMER_TABLE_LOCATION,PURCHASE_TABLE_LOCATION,SUBSCRIPTION_TABLE_LOCATION);
        csvToDB(CUSTOMER_TABLE_LOCATION,PURCHASE_TABLE_LOCATION,SUBSCRIPTION_TABLE_LOCATION);
        _logger.debug("Program Finished");
        GlobalProperties.closeConnections();
    }
}
