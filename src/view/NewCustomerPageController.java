package view;


import entities.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.List;

import static model.GeneralViewFunctions.alertToScreen;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.getAllCustomersFromDB;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.insertCustomerToDB;
import static utils.Utils.isValidMail;
import static utils.Utils.isValidPhone;

public class NewCustomerPageController extends AbstractView {

    @FXML
    private Pane checkCustomerBalancePage;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField mail;

    @FXML
    private void backButton(ActionEvent event){
        goTo(event,"HomePage.fxml");
    }
    @FXML
    private void submit(ActionEvent event){
        boolean everythingIsGood=true;
        String firstName= this.firstName.getText();
        String lastName= this.lastName.getText();
        String phoneNumber=phone.getText();
        String mail= this.mail.getText();
        // first - validate the input. first name, last name and phone are mandatory. than validate phone and mail (if inserted)
        if(firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()){
            alertToScreen(Alert.AlertType.WARNING,"אימות נתונים","חובה להכניס שם פרטי, שם משפחה וטלפון");
            everythingIsGood=false;
        }
        if(everythingIsGood && (!isValidPhone(phoneNumber))){
            alertToScreen(Alert.AlertType.WARNING,"אימות נתונים","מספר הטלפון לא תקין- מספרים בלבד ובאורך תקין");
            everythingIsGood=false;
        }
        if(everythingIsGood && (!isValidMail(mail))){
            alertToScreen(Alert.AlertType.WARNING,"אימות נתונים","מבנה המייל לא תקין. חובה להכיל @, וסיומת (com.)");
            everythingIsGood=false;
        }

        if(everythingIsGood){
            //Here I have valid information. check if this customer is already exist in db.
            List<Customer> customersInDB=getAllCustomersFromDB();
            for(Customer c :customersInDB){
                if(c.getFirstName().equals(firstName) &&c.getLastName().equals(lastName)&& c.getPhoneNumber().equals(phoneNumber)){
                    alertToScreen(Alert.AlertType.WARNING,"אימות נתונים","הלקוח כבר נמצא במערכת. לקוח חדש חייב להיות בעל שם פרטי, שם משפחה או טלפון שונה מלקוח שכבר נמצא במערכת");
                    everythingIsGood=false;
                    break;
                }
            }
        }

        if(everythingIsGood){
            //Here I have valid customer. insert the new customer to DB and if succeed -  print the number.
            int id=insertCustomerToDB(new Customer(firstName,lastName,mail,phoneNumber));
            if(id==-1){
                alertToScreen(Alert.AlertType.WARNING,"אימות נתונים","שגיאה במהלך הכנסת הלקוח למערכת");
                everythingIsGood=false;
            }else{
                alertToScreen(Alert.AlertType.INFORMATION,"אימות נתונים","לקוח חדש הוכנס בהצלחה. מספר לקוח: " +id);
            }
                goTo(event,"HomePage.fxml");
        }
    }
}

