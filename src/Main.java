import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.GlobalProperties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/homePage/HomePage.fxml"));
        //primaryStage.setTitle(MAIN_PROGRAM_TEXT);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        GlobalProperties.init();
        launch(args);


        // removeAllDataFromAllDBTables();
        // int id=insertCustomerToDB(new Customer("bar","brownhtein","0548004754",""));
        // insertSubscriptionToDB(new Subscription(id,10,MEALS_SUBSCRIPTION));
        // insertSubscriptionToDB(new Subscription(id,50,VIP_SUBSCRIPTION));
        //
        // insertPurchaseToDB(new Purchase(id,10,10));

        GlobalProperties.closeConnections();

    }
}
