/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package view;

import entities.Customer;
import entities.Purchase;
import entities.Subscription;
import entities.ViewCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static model.BasicMethods.deleteCustomerById;
import static model.BasicMethods.getSubscriptionByCustomerId;
import static model.BasicMethods.thisCustomerExistingInDB;
import static model.GeneralViewFunctions.alertToScreen;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static model.GlobalProperties.*;
import static utils.Constants.MEALS_SUBSCRIPTION;
import static utils.Constants.VIP_SUBSCRIPTION;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.*;
import static utils.SQLQueries.SQLQueriesAgainstPurchase.getAllPurchasesFromDB;
import static utils.SQLQueries.SQLQueriesAgainstSubscription.*;
import static utils.Utils.isValidMail;
import static utils.Utils.isValidPhone;

/**
 * @author bbrownsh
 * @since 1/2/2019
 */
public class EditCustomerPageController extends AbstractView {


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
    private TextField addCustomer_firstName;
    @FXML
    private TextField addCustomer_lastName;
    @FXML
    private TextField addCustomer_phone;
    @FXML
    private TextField addCustomer_mail;
    @FXML
    private TextField addCustomer_mealsAmount;
    @FXML
    private TextField addCustomer_vipAmount;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private TableView<ViewCustomer> table;
    @FXML
    TableColumn<ViewCustomer,Integer> id_col;
    @FXML
    TableColumn<ViewCustomer, String> fn_col;
    @FXML
    TableColumn<ViewCustomer,String> ln_col;
    @FXML
    TableColumn<ViewCustomer,String> phone_col;
    @FXML
    TableColumn<ViewCustomer,String> mail_col;
    @FXML
    TableColumn<ViewCustomer, String> meals_col;
    @FXML
    TableColumn<ViewCustomer,String> vip_col;
    @FXML
    TableColumn<ViewCustomer,String> lastPurchase_col;
    private List<ViewCustomer> viewCustomers=new ArrayList<>();

    @FXML
    private void deleteCustomer(ActionEvent event){
        if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך למחוק את הלקוח ואת כל המנויים שקיימים תחת השם שלו ?")==ButtonType.OK) {
            ViewCustomer purchaseClicked=table.getSelectionModel().getSelectedItem();
            deleteCustomerById(purchaseClicked.getCustomerID());
            refreshTable();
        }
    }
    @FXML
    private void customerClicked(MouseEvent event){
        deleteCustomerButton.setDisable(false);
    }

    @FXML
    private void fastAddCustomer(ActionEvent event) {
        boolean everythingIsGood = true;
        String firstName = this.addCustomer_firstName.getText();
        String lastName = this.addCustomer_lastName.getText();
        String phoneNumber = addCustomer_phone.getText();
        String mail = addCustomer_mail.getText();
        String mealsValue = addCustomer_mealsAmount.getText();
        String vipValue = addCustomer_vipAmount.getText();


        // first - validate the input. first name, last name and phone are mandatory. than validate phone and mail (if inserted)
        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
            alertToScreen(Alert.AlertType.WARNING, "אימות נתונים", "חובה להכניס שם פרטי, שם משפחה וטלפון");
            everythingIsGood = false;
        }
        if (everythingIsGood && (!isValidPhone(phoneNumber))) {
            alertToScreen(Alert.AlertType.WARNING, "אימות נתונים", "מספר הטלפון לא תקין- מספרים בלבד ובאורך תקין");
            everythingIsGood = false;
        }
        if (everythingIsGood && (!isValidMail(mail))) {
            alertToScreen(Alert.AlertType.WARNING, "אימות נתונים", "מבנה המייל לא תקין. חובה להכיל @, וסיומת (com.)");
            everythingIsGood = false;
        }


        double mealsAmount=0;
        double vipAmount=0;
        boolean insertMeals=false;
        boolean insertVip=false;

        try {
            if(!mealsValue.isEmpty()){
                mealsAmount = Double.parseDouble(mealsValue);
                insertMeals=true;
            }
            if(!vipValue.isEmpty()){
                vipAmount = Double.parseDouble(vipValue);
                insertVip=true;
            }
        } catch (Exception ex) {
            alertToScreen(Alert.AlertType.WARNING, "אימות נתונים", "ניתן להזין ערך מספרי חיובי בלבד בכמות הארוחות והויאיפי");
            everythingIsGood = false;
        }


        if (everythingIsGood) {
            //Here I have valid information. check if this customer is already exist in db.
            List<Customer> customersInDB = getAllCustomersFromDB();
            for (Customer c : customersInDB) {
                if (c.getFirstName().equals(firstName) && c.getLastName().equals(lastName) && c.getPhoneNumber().equals(phoneNumber)) {
                    alertToScreen(Alert.AlertType.WARNING, "אימות נתונים", "הלקוח כבר נמצא במערכת. לקוח חדש חייב להיות בעל שם פרטי, שם משפחה או טלפון שונה מלקוח שכבר נמצא במערכת");
                    everythingIsGood = false;
                    break;
                }
            }
        }


        if (everythingIsGood) {
            //Here I have valid customer. insert the new customer to DB and if succeed -  print the number.
            int id = insertCustomerToDB(new Customer(firstName, lastName, mail, phoneNumber));
            if (id == -1) {
                alertToScreen(Alert.AlertType.WARNING, "אימות נתונים", "שגיאה במהלך הכנסת הלקוח למערכת");
                everythingIsGood = false;
            } else {

                //TODO insert subscriptions if the value is not empty
                if(insertMeals){
                    insertSubscriptionToDB(new Subscription(id,mealsAmount,MEALS_SUBSCRIPTION));
                }
                if(insertVip){
                    insertSubscriptionToDB(new Subscription(id,vipAmount,VIP_SUBSCRIPTION));
                }
                alertToScreen(Alert.AlertType.INFORMATION, "אימות נתונים", "לקוח חדש הוכנס בהצלחה. מספר לקוח: " + id);
            }
            refreshTable();
        }
    }



    @FXML
    private void findCustomer(ActionEvent event){
        List<ViewCustomer> newCustomerList=new ArrayList<>();
        String id= this.id.getText();
        String firstName= this.firstName.getText();
        String lastName= this.lastName.getText();
        String phoneNumber=phone.getText();
        phoneNumber=phoneNumber.replaceAll("-","");

        boolean insertVar=true;
        for(ViewCustomer c:viewCustomers){
            if((!id.isEmpty())&&(!String.valueOf(c.getCustomerID()).equals(id))){
                insertVar=false;
            }if((!firstName.isEmpty())&&(!c.getFirstName().equals(firstName))){
                insertVar=false;
            } if((!lastName.isEmpty())&&(!c.getLastName().equals(lastName))){
                insertVar=false;
            } if((!phoneNumber.isEmpty())&&(!String.valueOf(c.getPhone()).equals(phoneNumber))){
                insertVar=false;
            }
            if(insertVar){
                newCustomerList.add(c);
            }
            insertVar=true;
        }
        ObservableList<ViewCustomer> data=FXCollections.observableArrayList(newCustomerList);
        table.setItems(data);
    }
    @FXML
    private void backButton(ActionEvent event){
            goTo("HomePage.fxml");
    }
    @FXML
    private void showAllCustomersClicked(){
        ObservableList<ViewCustomer> data=FXCollections.observableArrayList(viewCustomers);
        table.setItems(data);
    }
    @FXML
    private void changeCustomerFirstName(TableColumn.CellEditEvent editCell ){
        ViewCustomer c= table.getSelectionModel().getSelectedItem();
        String newFirstName=editCell.getNewValue().toString();
        //check if it is valid
        if(thisCustomerExistingInDB(newFirstName,c.getLastName(),c.getPhone())){
            alertToScreen(Alert.AlertType.WARNING,"אימות נתונים","קיים לקוח אחר עם אותו שם פרטי, שם משפחה וטלפון");
        }else{
            updateCustomerFirstName(c.getCustomerID(),newFirstName);
        }

        refreshTable();
    }

    @FXML
    private void changeCustomerLastName(TableColumn.CellEditEvent editCell ){
        ViewCustomer c= table.getSelectionModel().getSelectedItem();
        String newLastName=editCell.getNewValue().toString();
        //check if it is valid
        if(thisCustomerExistingInDB(c.getFirstName(),newLastName,c.getPhone())){
            alertToScreen(Alert.AlertType.WARNING,"אימות נתונים","קיים לקוח אחר עם אותו שם פרטי, שם משפחה וטלפון");
        }else{
            updateCustomerLastName(c.getCustomerID(),newLastName);
        }
        refreshTable();

    }

    @FXML
    private void changeCustomerMail(TableColumn.CellEditEvent editCell ){
        ViewCustomer c= table.getSelectionModel().getSelectedItem();
        String newMail=editCell.getNewValue().toString();
        if(!isValidMail(newMail)){
            alertToScreen(Alert.AlertType.WARNING,"אימות נתונים","מבנה המייל לא תקין. חובה להכיל @, וסיומת (com.)");
        }
        else{
            updateCustomerMail(c.getCustomerID(),newMail);
        }
        refreshTable();
    }

    @FXML
    private void changeCustomerphone(TableColumn.CellEditEvent editCell ){
        ViewCustomer c= table.getSelectionModel().getSelectedItem();
        String newPhone=editCell.getNewValue().toString();
        if(!isValidPhone(newPhone)){
            alertToScreen(Alert.AlertType.WARNING,"אימות נתונים","מספר הטלפון לא תקין- מספרים בלבד ובאורך תקין");
        }
        else{
            if(thisCustomerExistingInDB(c.getFirstName(),c.getLastName(),newPhone)) {
                alertToScreen(Alert.AlertType.WARNING, "אימות נתונים", "קיים לקוח אחר עם אותו שם פרטי, שם משפחה וטלפון");
            }
            else{
                updateCustomerPhone(c.getCustomerID(),newPhone);
            }
        }
        refreshTable();
    }

    @FXML
    private void changeCustomerMealsSubscriptionValue(TableColumn.CellEditEvent editCell ){
        ViewCustomer c= table.getSelectionModel().getSelectedItem();
        String newMealsValue=editCell.getNewValue().toString();
        double mealsValue=0;
        try {
            mealsValue=Double.parseDouble(newMealsValue);
            if(mealsValue<0) {
                throw new Exception();
            }
            //check if exist
            Subscription s=getSubscriptionByCustomerId(c.getCustomerID(),MEALS_SUBSCRIPTION);
            if(s==null){
                if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","ללקוח אין מנוי ארוחות פעיל. האם ברצונך ליצור אחד ?")==ButtonType.OK) {
                        //create subscription
                    insertSubscriptionToDB(new Subscription(c.getCustomerID(),mealsValue,MEALS_SUBSCRIPTION));
                }
                }else{
                updateSubscriptionBalance(s.getSubscriptionID(),mealsValue);

            }
        } catch (Exception ex) {
            alertToScreen(Alert.AlertType.INFORMATION,"עדכון לקוח","נא להזמין ערך מספרי חיובי בלבד");
        }
        refreshTable();
    }


    @FXML
    private void changeCustomerVipSubscriptionValue(TableColumn.CellEditEvent editCell ){
        ViewCustomer c= table.getSelectionModel().getSelectedItem();
        String newVipValue=editCell.getNewValue().toString();
        double vipValue=0;
        try {
            vipValue=Double.parseDouble(newVipValue);
            if(vipValue<0) {
                throw new Exception();
            }
            //check if exist
            Subscription s=getSubscriptionByCustomerId(c.getCustomerID(),VIP_SUBSCRIPTION);
            if(s==null){
                if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","ללקוח אין מנוי ויאיפי פעיל. האם ברצונך ליצור אחד ?")==ButtonType.OK) {
                    //create subscription
                    insertSubscriptionToDB(new Subscription(c.getCustomerID(),vipValue,VIP_SUBSCRIPTION));
                }
            }else{
                updateSubscriptionBalance(s.getSubscriptionID(),vipValue);
            }
        } catch (Exception ex) {
            alertToScreen(Alert.AlertType.INFORMATION,"עדכון לקוח","נא להזמין ערך מספרי חיובי בלבד");
        }
        refreshTable();
    }





    private void refreshTable(){
        setCachedSubscriptions(getAllSubscriptionsFromDB());
        setCachedPurchases(getAllPurchasesFromDB());
        setCachedCustomers(getAllCustomersFromDB());
        //TODO fix it
        //table.refresh();
        goTo("EditCustomerPage.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Customer> customersToShow=getCachedCustomers();
        List<Purchase> purchase =getCachedPurchases();
        purchase.sort(Comparator.comparing(Purchase::getDate));

        for(Customer c : customersToShow){
            double mealsBalance=0;
            double vipBalance=0;
            String lastPurchase="";

            for(Subscription s : getCachedSubscriptions()){
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
            viewCustomers.add(new ViewCustomer(c.getCustomerID(),c.getFirstName(),c.getLastName(),lastPurchase,String.valueOf(mealsBalance),String.valueOf(vipBalance),c.getPhoneNumber(),c.getMailAddress()));
        }

        ObservableList<ViewCustomer> data=FXCollections.observableArrayList(viewCustomers);
        id_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));//the name like in the class
        fn_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        ln_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        mail_col.setCellValueFactory(new PropertyValueFactory<>("mail"));
        lastPurchase_col.setCellValueFactory(new PropertyValueFactory<>("lastPurchase"));
        meals_col.setCellValueFactory(new PropertyValueFactory<>("mealsBalance"));
        vip_col.setCellValueFactory(new PropertyValueFactory<>("vipBalance"));
        table.setItems(data);
        table.setEditable(true);
        fn_col.setCellFactory(TextFieldTableCell.forTableColumn());
        ln_col.setCellFactory(TextFieldTableCell.forTableColumn());
        phone_col.setCellFactory(TextFieldTableCell.forTableColumn());
        mail_col.setCellFactory(TextFieldTableCell.forTableColumn());
        meals_col.setCellFactory(TextFieldTableCell.forTableColumn());
        vip_col.setCellFactory(TextFieldTableCell.forTableColumn());
        deleteCustomerButton.setDisable(true);
    }
}
