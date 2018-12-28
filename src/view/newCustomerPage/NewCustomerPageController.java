package view.newCustomerPage;


import entities.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static utils.SQLQueries.SQLQueriesAgainstCustomer.getAllCustomersFromDB;
import static utils.SQLQueries.SQLQueriesAgainstCustomer.insertCustomerToDB;
import static utils.Utils.isValidMail;
import static utils.Utils.isValidPhone;

public class NewCustomerPageController implements Initializable {

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
        boolean everythingIsGood=true;
        String fn=firstName.getText();
        String ln=lastName.getText();
        String p=phone.getText();
        String m=mail.getText();
        // first - validate the input. first name, last name and phone are mandatory. than validate phone and mail (if inserted)
        if(fn.isEmpty() || ln.isEmpty() || p.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("אימות נתונים");
            alert.setHeaderText(null);
            alert.setContentText("חובה להכניס שם פרטי, שם משפחה וטלפון");
            alert.showAndWait();
            everythingIsGood=false;
        }
        if(everythingIsGood && (!isValidPhone(p))){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("אימות נתונים");
            alert.setHeaderText(null);
            alert.setContentText("מספר הטלפון לא תקין- מספרים בלבד ובאורך תקין");
            alert.showAndWait();
            everythingIsGood=false;
        }
        if(everythingIsGood && (!isValidMail(m))){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("אימות נתונים");
            alert.setHeaderText(null);
            alert.setContentText("מבנה המייל לא תקין. חובה להכיל @, וסיומת (com.)");
            alert.showAndWait();
            everythingIsGood=false;
        }

        if(everythingIsGood){
            //Here I have valid information. check if this customer is already exist in db.
            List<Customer> customersInDB=getAllCustomersFromDB();
            for(Customer c :customersInDB){
                if(c.getFirstName().equals(fn) &&c.getLastName().equals(ln)&& c.getPhoneNumber().equals(p)){
                    Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("אימות נתונים");
                    alert.setHeaderText(null);
                    alert.setContentText("הלקוח כבר נמצא במערכת. לקוח חדש חייב להיות בעל שם פרטי, שם משפחה או טלפון שונה מלקוח שכבר נמצא במערכת");
                    alert.showAndWait();
                    everythingIsGood=false;
                    break;
                }
            }
        }



        if(everythingIsGood){
            //Here I have valid customer. insert the new customer to DB and if succeed -  print the number.
            int id=insertCustomerToDB(new Customer(fn,ln,p,m));
            if(id==-1){//TODO Check if this this is the case for not good query
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("אימות נתונים");
                alert.setHeaderText(null);
                alert.setContentText("שגיאה במהלך הכנסת הלקוח למערכת");
                alert.showAndWait();
                everythingIsGood=false;
            }else{
               Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("אימות נתונים");
                alert.setHeaderText(null);
                alert.setContentText("לקוח חדש הוכנס בהצלחה. מספר לקוח: " +id);
                alert.showAndWait();
            }
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


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

