package p_henriqued.javafxstudy.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

    public static Stage currentStage(ActionEvent actionEvent){
        return (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    public static Long tryParseToLong(String id){
        try{
           return Long.parseLong(id);
        }catch (NumberFormatException exception){
            return null;
        }
    }

}
