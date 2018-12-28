package view.loadSubscriptionPage;


import entities.Customer;
import entities.Purchase;
import entities.Subscription;
import entities.ViewCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static utils.Constants.MEALS_SUBSCRIPTION;
import static utils.Constants.VIP_SUBSCRIPTION;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.getAllCustomersFromDB;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.getAllPurchasesFromDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.getAllSubscriptionsFromDB;

public class LoadSubscriptionPageController implements Initializable {

    @FXML
    private Pane checkCustomerBalancePage;
    @FXML
    private TextField id;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField mail;
    @FXML
    private TableView<ViewCustomer> table;
    @FXML
    TableColumn<ViewCustomer,Integer> id_col;
    @FXML
    TableColumn<ViewCustomer,Integer> fn_col;
    @FXML
    TableColumn<ViewCustomer,Integer> ln_col;
    @FXML
    TableColumn<ViewCustomer,Integer> meals_col;
    @FXML
    TableColumn<ViewCustomer,Integer> vip_col;
    @FXML
    TableColumn<ViewCustomer,Integer> lastPurchase_col;


    private List<ViewCustomer> viewCustomers=new ArrayList<>();


    @FXML
    private void backButton(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("../homePage/HomePage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void submit(ActionEvent event){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Customer> customersToShow=getAllCustomersFromDB();
        List<Purchase> purchase =getAllPurchasesFromDB();
        List<Subscription> subscriptions=getAllSubscriptionsFromDB();
        //TODO sort the purchases

        for(Customer c : customersToShow){
            double mealsBalance=0;
            double vipBalance=0;
            String lastPurchase="";

            for(Subscription s : subscriptions){
                if(s.getCoustomerID()==c.getCustomerID()){
                    if(s.getType().equals(MEALS_SUBSCRIPTION)){
                        mealsBalance=s.getBalance();
                    }
                    else if(s.getType().equals(VIP_SUBSCRIPTION)){
                        vipBalance=s.getBalance();
                    }
                }
            }
            for(Purchase p : purchase){
                if(p.getCustomerID()==c.getCustomerID()){
                    lastPurchase=p.getDate();
                }
            }
            viewCustomers.add(new ViewCustomer(c.getCustomerID(),c.getFirstName(),c.getLastName(),lastPurchase,mealsBalance,vipBalance));
        }

        ObservableList<ViewCustomer> data=FXCollections.observableArrayList(viewCustomers);
        id_col.setCellValueFactory(new PropertyValueFactory<ViewCustomer, Integer>("customerID"));//the name like in the class
        fn_col.setCellValueFactory(new PropertyValueFactory<ViewCustomer, Integer>("firstName"));
        ln_col.setCellValueFactory(new PropertyValueFactory<ViewCustomer, Integer>("lastName"));
        lastPurchase_col.setCellValueFactory(new PropertyValueFactory<ViewCustomer, Integer>("lastPurchase"));
        meals_col.setCellValueFactory(new PropertyValueFactory<ViewCustomer, Integer>("mealsBalance"));
        vip_col.setCellValueFactory(new PropertyValueFactory<ViewCustomer, Integer>("vipBalance"));

        table.setItems(data);
    }
}

