package view;

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
import java.util.*;

import static model.BasicMethods.*;
import static model.GeneralViewFunctions.alertToScreen;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static model.GlobalProperties.*;
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
    private ComboBox comboBoxDate;
    @FXML
    private ComboBox comboBoxType;
    @FXML
    private RadioButton showSubscriptions;
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
        String date=(String) comboBoxDate.getValue();
        boolean mealsOnly= comboBoxType.getValue() != null && comboBoxType.getValue().equals(MEALS_SUBSCRIPTION);
        boolean vipOnly= comboBoxType.getValue() != null && comboBoxType.getValue().equals(VIP_SUBSCRIPTION);
        boolean showSubscriptions= this.showSubscriptions.isSelected();
        if(comboBoxType.getValue() == null||(comboBoxType.getValue() != null && comboBoxType.getValue().equals("הכל"))){
            mealsOnly= true;
            vipOnly=true;
        }
        boolean insertVar=true;
        for(ViewLastPurchase p:viewLastPurchases){
            if((!id.isEmpty())&&(!String.valueOf(p.getCustomerID()).equals(id))){
                insertVar=false;
            }if((!firstName.isEmpty())&&(!p.getFirstName().equals(firstName))){
                insertVar=false;
            }if((!lastName.isEmpty())&&(!p.getLastName().equals(lastName))){
                insertVar=false;
            }if(handleDateCondition(date,p.getDate())){
                 insertVar=false;
            }if(!mealsOnly && p.getType().equals(MEALS_SUBSCRIPTION)){
                insertVar=false;
            }if(!vipOnly && p.getType().equals(VIP_SUBSCRIPTION)){
                insertVar=false;
            }if(!showSubscriptions && (p.getComments().equals(PURCHASE_SUBSCRIPTION_COMMENT))){
                insertVar=false;

            }
            if(insertVar){
                newCustomerList.add(p);
            }
            insertVar=true;
        }
        ObservableList<ViewLastPurchase> data=FXCollections.observableArrayList(newCustomerList);
        table.setItems(data);
    }
    private boolean handleDateCondition(String condition,String date) {
    if (condition==null ||condition.isEmpty()|| condition.equals("הכל")) {
        return false;
    }
    Date nowTime = StringToDate(getCurrentTimeStamp());
    Date purchaseDate = StringToDate(date);
    Calendar c = Calendar.getInstance();
    c.setFirstDayOfWeek(Calendar.SUNDAY);
    boolean sameDay = nowTime.getDay() == purchaseDate.getDay();
    boolean sameMonth = nowTime.getMonth() == purchaseDate.getMonth();
    boolean sameYear = nowTime.getYear() == purchaseDate.getYear();
    boolean dayBefore = nowTime.getDay() - 1 == purchaseDate.getDay();

    switch (condition) {
        case TODAY_OPTION:
            return !(sameDay && sameMonth && sameYear);
        case YESTERDAY_OPTON:
            return !(dayBefore && sameMonth && sameYear);
        case THIS_WEEK_OPTION:
            return !(isDateInCurrentWeek(purchaseDate));
        case THIS_MONTH_OPTION:
            return !(sameMonth && sameYear);
    }
    return false;
}
    @FXML
    private void backButton(ActionEvent event){
        try {
            Parent homePageParent=FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Scene homePageScene=new Scene(homePageParent);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            appStage.setScene(homePageScene);
            appStage.setMaximized(true);
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
            if(customer!=null){
                viewLastPurchases.add(new ViewLastPurchase(p.getPurchaseID(),p.getCustomerID(),customer.getFirstName(), customer.getLastName(),p.getDate(),p.getType(),p.getAmount(),p.getNewBalance(),p.getComments()));
            }
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

        showSubscriptions.setSelected(true);
        cancelPurchase.setDisable(true);
        comboBoxDate.getItems().addAll("הכל",TODAY_OPTION,YESTERDAY_OPTON,THIS_WEEK_OPTION,THIS_MONTH_OPTION);
        comboBoxType.getItems().addAll("הכל",MEALS_SUBSCRIPTION,VIP_SUBSCRIPTION);
    }
}

