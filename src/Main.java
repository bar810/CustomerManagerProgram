import entities.customer.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.GlobalProperties;

import static utils.Constants.MAIN_PROGRAM_TEXT;
import static utils.GlobalProperties._logger;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.insertCustomerToDB;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.removeAllCustomerTable;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.updateCustomerFirstName;

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

        insertCustomerToDB(new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754"));
        insertCustomerToDB(new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754"));
        insertCustomerToDB(new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754"));
        int id=insertCustomerToDB(new Customer("Bar","Brownshtein","bar810@gmail.com","0548004754"));
        updateCustomerFirstName(id,"newName");
        removeAllCustomerTable();

        GlobalProperties.closeConnections();
        _logger.debug("Program Finished");
    }
}
