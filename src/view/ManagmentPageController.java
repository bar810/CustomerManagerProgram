/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @author bbrownsh
 * @since 1/1/2019
 */
public class ManagmentPageController extends AbstractView {

    @FXML
    private void goToHomePage(ActionEvent event){
        goTo(event,"HomePage.fxml");
    }

    @FXML
    private void moveToBackup(ActionEvent event){
        goTo(event,"backupPage.fxml");
    }

    @FXML
    private void moveToProperties(ActionEvent event){
        goTo(event,"PropertiesPage.fxml");
    }

    @FXML
    private void moveToReports(ActionEvent event){
        goTo(event,"ReportsPage.fxml");
    }

    @FXML
    private void moveToEditCustomer(ActionEvent event){
        goTo(event,"EditCustomerPage.fxml");
    }
}
