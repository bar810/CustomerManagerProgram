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
import javafx.scene.control.*;
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

import static model.BasicMethods.addValueToCustomerSubscription;
import static model.BasicMethods.minBetweenDates;
import static model.GeneralViewFunctions.alertToScreen;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static model.GlobalProperties.getCachedCustomers;
import static model.GlobalProperties.getCachedPurchases;
import static model.GlobalProperties.getProperty;
import static utils.Constants.*;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.removeOnePurchase;
import static utils.Utils.getCurrentTimeStamp;

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
    private RadioButton subscriptionsOnly;
    @FXML
    private TableView<ViewLastPurchase> table;
    @FXML
    private Button cancelPurchase;
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
        List<ViewLastPurchase> newCustomerList=new ArrayList<>();

        String id= this.customerID.getText();
        String firstName= this.firstName.getText();
        String lastName= this.lastName.getText();
        String date=this.date.getText();
        boolean mealsOnly=mealsType.isSelected();
        boolean vipOnly=vipType.isSelected();
        boolean subscriptionsOnly= this.subscriptionsOnly.isSelected();

        boolean insertVar=true;
        for(ViewLastPurchase p:viewLastPurchases){
            if((!id.isEmpty())&&(!String.valueOf(p.getCustomerID()).equals(id))){
                insertVar=false;
            }if((!firstName.isEmpty())&&(!p.getFirstName().equals(firstName))){
                insertVar=false;
            } if((!lastName.isEmpty())&&(!p.getLastName().equals(lastName))){
                insertVar=false;
            } if((!date.isEmpty())&&(!String.valueOf(p.getDate()).equals(date))){
                insertVar=false;
            }if(!mealsOnly && p.getType().equals(MEALS_SUBSCRIPTION)){
                insertVar=false;
            }if(!vipOnly && p.getType().equals(VIP_SUBSCRIPTION)){
                insertVar=false;
            }if(subscriptionsOnly && (!p.getComments().equals(PURCHASE_SUBSCRIPTION_COMMENT))){
                insertVar=false;

            }
            if(insertVar){
                newCustomerList.add(p);
            }
            insertVar=true;
        }
        ObservableList<ViewLastPurchase> data=FXCollections.observableArrayList(newCustomerList);
        table.setItems(data);    }


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
        ViewLastPurchase purchaseClicked=table.getSelectionModel().getSelectedItem();
        if(minBetweenDates(getCurrentTimeStamp(),purchaseClicked.getDate())<Integer.parseInt(getProperty(TIME_TILL_CANCEL_PURCHASE))) {
            cancelPurchase.setDisable(false);
        }else{
            cancelPurchase.setDisable(true);
        }
    }

    @FXML
    private void cancelPurchedButoon(ActionEvent event) {
        ViewLastPurchase purchaseClicked = table.getSelectionModel().getSelectedItem();
        if (minBetweenDates(getCurrentTimeStamp(), purchaseClicked.getDate()) < Integer.parseInt(getProperty(TIME_TILL_CANCEL_PURCHASE))) {
            if (alertToScreenWithResponse(Alert.AlertType.CONFIRMATION, "אישור פעולה", "האם אתה בטוח שברצונך לאשר את הפעולה ?") == ButtonType.OK) {
                double amount = purchaseClicked.getAmount();
                String type = purchaseClicked.getType();
                //update the amount
                addValueToCustomerSubscription(purchaseClicked.getCustomerID(), purchaseClicked.getType(), purchaseClicked.getAmount());
                //remove purchase
                removeOnePurchase(purchaseClicked.getPurchaseID());
                System.out.println("ss");
                alertToScreen(Alert.AlertType.INFORMATION, "ביטול רכישה", "רכישה בוטלה בהצלחה !בוצע זיכוי ללקוח");
                this.backButton(event);
            }
        }
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

        mealsType.setSelected(true);
        vipType.setSelected(true);
        subscriptionsOnly.setSelected(false);
        cancelPurchase.setDisable(true);
    }
}

