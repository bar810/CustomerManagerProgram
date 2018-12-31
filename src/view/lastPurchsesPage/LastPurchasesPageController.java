package view.lastPurchsesPage;

import entities.Customer;
import entities.Purchase;
import entities.ViewLastPurchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static model.GlobalProperties.getCachedCustomers;
import static model.GlobalProperties.getCachedPurchases;

public class LastPurchasesPageController implements Initializable {

    @FXML
    private Pane checkCustomerBalancePage;
    @FXML
    private TextField customerID;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField date;
    @FXML
    private RadioButton mealsType;
    @FXML
    private RadioButton vipType;
    @FXML
    private TableView<ViewLastPurchase> table;
    @FXML
    TableColumn<ViewLastPurchase,Integer> purchaseID_col;
    @FXML
    TableColumn<ViewLastPurchase,Integer> customerID_col;
    @FXML
    TableColumn<ViewLastPurchase,Integer> fn_col;
    @FXML
    TableColumn<ViewLastPurchase,Integer> ln_col;
    @FXML
    TableColumn<ViewLastPurchase,Integer> date_col;
    @FXML
    TableColumn<ViewLastPurchase,Integer> type_col;
    @FXML
    TableColumn<ViewLastPurchase,Integer> amount_col;
    @FXML
    TableColumn<ViewLastPurchase,Integer> newBalance_col;
    @FXML
    TableColumn<ViewLastPurchase,Integer> comments_col;

    private List<ViewLastPurchase> viewLastPurchases=new ArrayList<>();

    @FXML
    private void findCustomer(ActionEvent event){
        // List<ViewCustomer> newCustomerList=new ArrayList<>();
        // String id= this.id.getText();
        // String firstName= this.firstName.getText();
        // String lastName= this.lastName.getText();
        // String phoneNumber=phone.getText();
        //
        // boolean insertVar=true;
        // for(ViewCustomer c:viewCustomers){
        //     if((!id.isEmpty())&&(!String.valueOf(c.getCustomerID()).equals(id))){
        //         insertVar=false;
        //     }if((!firstName.isEmpty())&&(!c.getFirstName().equals(firstName))){
        //         insertVar=false;
        //     } if((!lastName.isEmpty())&&(!c.getLastName().equals(lastName))){
        //         insertVar=false;
        //     } if((!phoneNumber.isEmpty())&&(!String.valueOf(c.getCustomerID()).equals(id))){
        //         insertVar=false;
        //     }
        //     if(insertVar){
        //         newCustomerList.add(c);
        //     }
        // }
        // ObservableList<ViewCustomer> data=FXCollections.observableArrayList(newCustomerList);
        // table.setItems(data);
    }

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
    private void showAllCustomersClicked(){
        ObservableList<ViewLastPurchase> data=FXCollections.observableArrayList(viewLastPurchases);
        table.setItems(data);
    }

    @FXML
    private void customerClicked(MouseEvent event){
        // setCachedViewCustomer(table.getSelectionModel().getSelectedItem());
        // if(getCachedViewCustomer()==null){
        //     _logger.warning("try to find Cached view customer was made when it was equals to null");
        // }else{
        //     Parent homePageParent;
        //     Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //
        //     FXMLLoader loader=new FXMLLoader();
        //     loader.setLocation(getClass().getResource("selectActionPage/SelectActionPage.fxml"));
        //     try {
        //         loader.load();
        //     } catch (Exception ex) {
        //     }
        //     homePageParent=loader.getRoot();
        //     appStage.setScene(new Scene(homePageParent));
        //     appStage.show();
        // }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Customer> customersToShow=getCachedCustomers();
        List<Purchase> purchase =getCachedPurchases();
        purchase.sort(Comparator.comparing(Purchase::getDate));
        Customer customer=null;
        for(Purchase p : purchase){

            for(Customer c: customersToShow){
                if(p.getCustomerID()==c.getCustomerID()){
                    customer=c;
                    break;
                }
            }
            viewLastPurchases.add(new ViewLastPurchase(p.getPurchaseID(),p.getCustomerID(),customer.getFirstName(), customer.getLastName(),p.getDate(),p.getType(),p.getAmount(),p.getNewBalance(),p.getComments()));
        }

        ObservableList<ViewLastPurchase> data=FXCollections.observableArrayList(viewLastPurchases);
        purchaseID_col.setCellValueFactory(new PropertyValueFactory<>("purchaseID"));//the name like in the class
        customerID_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));//the name like in the class
        fn_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));//the name like in the class
        ln_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));//the name like in the class
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));//the name like in the class
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));//the name like in the class
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));//the name like in the class
        newBalance_col.setCellValueFactory(new PropertyValueFactory<>("newBalance"));//the name like in the class
        comments_col.setCellValueFactory(new PropertyValueFactory<>("comments"));//the name like in the class
        table.setItems(data);
    }
}

