package p_henriqued.javafxstudy.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import p_henriqued.javafxstudy.MainApplication;
import p_henriqued.javafxstudy.util.Constraints;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
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
    private Button registreButton;

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
        numberOneTextField.clear();
        numberSecondTextField.clear();
        resultLabel.setText("");
    }

    @FXML
    protected void onRegistreButtonClick(ActionEvent event) {
        try {
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view-registration.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            newStage.setScene(scene);

            newStage.initModality(Modality.APPLICATION_MODAL);

            Stage currentStage = (Stage) registreButton.getScene().getWindow();
            newStage.initOwner(currentStage);
            newStage.setTitle("Cadastro");
            newStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Constraints.setTextFieldDouble(numberOneTextField);
            Constraints.setTextFieldDouble(numberSecondTextField);
            Constraints.setTextFieldMaxLength(numberOneTextField, 10);
            Constraints.setTextFieldMaxLength(numberSecondTextField, 10);
        }catch (Exception e){
            System.out.println("Initializable: "+e.getMessage());
        }

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