package view;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import static model.BasicMethods.buySubscription;
import static model.GeneralViewFunctions.alertToScreen;
import static model.GeneralViewFunctions.alertToScreenWithResponse;
import static model.GlobalProperties.getCachedViewCustomer;
import static utils.Constants.MEALS_SUBSCRIPTION;
import static utils.Constants.VIP_SUBSCRIPTION;

public class LoadSubscriptionPageController extends AbstractView {

    @FXML
    private void backButton(ActionEvent event){
        goTo("SelectActionPage.fxml");
    }
    @FXML
    private void loadMeals(ActionEvent event){
     if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך לאשר את הפעולה ?")==ButtonType.OK){
         if(buySubscription(getCachedViewCustomer().getCustomerID(),MEALS_SUBSCRIPTION)){
             SucceededAlertAndGoToHomePage(event);
         }else{
             alertToScreen(Alert.AlertType.INFORMATION,"שגיאה","שגיאה בהוספת מנוי");

         }
     }
     else{
         this.backButton(event);
     }
    }
    @FXML
    private void loadVIP(ActionEvent event){
        if(alertToScreenWithResponse(Alert.AlertType.CONFIRMATION,"אישור פעולה","האם אתה בטוח שברצונך לאשר את הפעולה ?")==ButtonType.OK){
            if(buySubscription(getCachedViewCustomer().getCustomerID(),VIP_SUBSCRIPTION)){
                SucceededAlertAndGoToHomePage(event);
            }else {
                alertToScreen(Alert.AlertType.INFORMATION, "שגיאה", "שגיאה בהוספת מנוי");
            }
        }
        else{
            this.backButton(event);
        }
    }
    private void SucceededAlertAndGoToHomePage(ActionEvent event){
        alertToScreen(Alert.AlertType.INFORMATION,"אימות נתונים","מנוי נרכש בהצלחה !");
        goTo("HomePage.fxml");
    }

}

