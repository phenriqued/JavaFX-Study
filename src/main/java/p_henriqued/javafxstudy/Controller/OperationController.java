package p_henriqued.javafxstudy.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import p_henriqued.javafxstudy.util.Constraints;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.BiFunction;


public class OperationController implements Initializable {

    @FXML
    private TextField numberOneTextField;
    @FXML
    private TextField numberSecondTextField;

    @FXML
    private Label resultLabel;

    @FXML
    protected void onSumButtonClick() {
        if(!emptyString()) operation(Double::sum);
    }
    @FXML
    protected void onSubtractButtonClick() {
        if(!emptyString()) operation((x, y) -> x - y);
    }
    @FXML
    protected void onMultipleButtonClick() {
        if(!emptyString()) operation((x, y) -> x * y);
    }
    @FXML
    protected void onDivisionButtonClick() {
        if(emptyString()){
           return;
        }
        if(numberSecondTextField.getText().equals("0")){
            resultLabel.setText("Não é possível dividir por 0!");
            return;
        }
        operation((x, y) -> x / y);
    }
    @FXML
    protected void onCleanButtonClick(){
        numberOneTextField.setText("");
        numberSecondTextField.setText("");
        resultLabel.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Constraints.setTextFieldDouble(numberOneTextField);
        Constraints.setTextFieldDouble(numberSecondTextField);
        Constraints.setTextFieldMaxLength(numberOneTextField, 10);
        Constraints.setTextFieldMaxLength(numberSecondTextField, 10);
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

    private Boolean emptyString(){
        return (numberOneTextField.getText().isEmpty() || numberSecondTextField.getText().isEmpty());
    }

}