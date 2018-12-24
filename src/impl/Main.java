package impl;

import entities.customer.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

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


        ArrayList<Customer> customers=new ArrayList<Customer>();
        Customer c=new Customer(1,"bar","brownshtein","bar810@gmail.com","0548004754");

        c.buyMealSubscription();
        c.makeMealPurchase(1);
        c.makeMealPurchase(0.5);

        customers.add(c);



    }


    public static void main(String[] args) {
        launch(args);
    }
}
