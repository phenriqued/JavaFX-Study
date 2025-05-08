package p_henriqued.javafxstudy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Locale;
import java.util.function.BiFunction;


public class OperationController {

    @FXML
    private TextField numberOneTextField;
    @FXML
    private TextField numberSecondTextField;

    @FXML
    private Label resultLabel;

    @FXML
    protected void onSumButtonClick() {
        operation(Double::sum);
    }
    @FXML
    protected void onSubtractButtonClick() {
        operation((x, y) -> x - y);
    }
    @FXML
    protected void onMultipleButtonClick() {
        operation((x, y) -> x * y);
    }
    @FXML
    protected void onDivisionButtonClick() {
        operation((x, y) -> x / y);
    }
    @FXML
    protected void onCleanButtonClick(){
        numberOneTextField.setText("");
        numberSecondTextField.setText("");
        resultLabel.setText("");
    }

    //Metodo resposável por fazer as operações utilizando a interface BiFunctional.
    private void operation(BiFunction<Double, Double, Double> function){
        try {
            Locale.setDefault(Locale.US);
            resultLabel.setText("");
            Double numberOne = Double.parseDouble(numberOneTextField.getText());
            Double numberSecond = Double.parseDouble(numberSecondTextField.getText());
            Double result = function.apply(numberOne, numberSecond);
            resultLabel.setText(String.format("%.2f", result));
        }catch (NumberFormatException | ArithmeticException exception){
            AlertController.alertShow("ERROR", "ERROR PERFORMING THE OPERATION!", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

}