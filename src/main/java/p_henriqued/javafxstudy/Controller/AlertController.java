package p_henriqued.javafxstudy.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertController {

    public static void alertShow(String headerText, String title, String contentText,AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.show();
    }

}
