import entities.Subscription;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.GlobalProperties;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.MAIN_PROGRAM_TEXT;
import static utils.GlobalProperties._logger;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.*;

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

        Subscription a= new Subscription(1,"meal",1.5,Subscription.SubscriptionType.MEALS);
        Subscription a1=new Subscription(1,"vip",1.5,Subscription.SubscriptionType.VIP);
        List<Subscription> l=new ArrayList<>();
        l.add(a);
        l.add(a1);
        insertRangeOfSubscriptionsToDB(l);

        Subscription s=getSubscriptionByID(1);

        List<Subscription> ll=getAllSubscriptionFromDBWithConditions("","","meal","1","0");

        updateSubscriptionBalance(1,50);

        removeOneSubscription(2);

        removeAllSubscriptionsTable();

        _logger.debug("Program Finished");
        GlobalProperties.closeConnections();
    }
}
