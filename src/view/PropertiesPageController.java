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
 * @since 1/2/2019
 */
public class PropertiesPageController  extends AbstractView {

    @FXML
    private void goBack(ActionEvent event){
        goTo("ManagmentPage.fxml");
    }
}
