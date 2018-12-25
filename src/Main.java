import entities.customer.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.GlobalProperties;

import java.util.List;

import static utils.Constants.MAIN_PROGRAM_TEXT;
import static utils.SQLQueriesAgainstCustomer.getAllCustomersFromDBWithConditions;

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
        //launch(args);

        // try {
        //     System.out.println("connecting to DB "+JBDC_CONNECTION_STRING);
        //     Connection myConn=DriverManager.getConnection(JBDC_CONNECTION_STRING,USER_NAME,PASSWORD);
        //     System.out.println("connection successful !");
        //
        // } catch (Exception ex) {
        //     System.out.println("connection failed !");
        //
        // }

        //
        // Customer customer = new Customer("Gal","Brownshtein","bar810@gmail.com","0548004754");
        // int id=insertCustomerToDB(customer);
        // System.out.println("id1: "+id);

        //Customer customer=getCustomerByID(2);

        // //TODO :: not working
        List<Customer> customers=getAllCustomersFromDBWithConditions("","Bar","","","");

        GlobalProperties.closeConnections();
        System.out.println("Program finished !");
    }
}
