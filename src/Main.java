import entities.customer.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.GlobalProperties;

import java.util.ArrayList;

import static utils.Constants.MAIN_PROGRAM_TEXT;
import static utils.GlobalProperties._logger;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.insertRangeOfCustomersToDB;
import static utils.Utils.exportToCsv;
import static utils.Utils.importFromCsv;

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

        // insertCustomerToDB(new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754"));
        // insertCustomerToDB(new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754"));
        // insertCustomerToDB(new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754"));
        // int id=insertCustomerToDB(new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754"));
        // updateCustomerFirstName(id,"newName");
        // removeAllCustomerTable();

        Customer c1=new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754");
        Customer c2=new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754");
        ArrayList<Customer>l =new ArrayList<Customer>();
        l.add(c1);
        l.add(c2);
        exportToCsv(l);
        ArrayList<Customer>l2=importFromCsv("Customers.csv");
        insertRangeOfCustomersToDB(l2);

        GlobalProperties.closeConnections();
        _logger.debug("Program Finished");
    }
}
