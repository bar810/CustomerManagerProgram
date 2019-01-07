package view;


import entities.Customer;
import entities.Purchase;
import entities.Subscription;
import entities.ViewCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static model.GlobalProperties.*;
import static utils.Constants.MEALS_SUBSCRIPTION;
import static utils.Constants.VIP_SUBSCRIPTION;

public class FindCustomerPageController extends AbstractView {

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
    private void customerClicked(MouseEvent event){
        setCachedViewCustomer(table.getSelectionModel().getSelectedItem());
        if(getCachedViewCustomer()==null){
            _logger.warning("try to find Cached view customer was made when it was equals to null");
        }else{
            goTo("SelectActionPage.fxml");
        }
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
        lastPurchase_col.setCellValueFactory(new PropertyValueFactory<>("lastPurchase"));
        meals_col.setCellValueFactory(new PropertyValueFactory<>("mealsBalance"));
        vip_col.setCellValueFactory(new PropertyValueFactory<>("vipBalance"));
        table.setItems(data);
    }
}

