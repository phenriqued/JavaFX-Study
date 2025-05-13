package p_henriqued.javafxstudy.Controller.DepartmentController;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import p_henriqued.javafxstudy.util.Constraints.Constraints;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormsController implements Initializable {

    @FXML
    private TextField departmentNameTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @FXML
    protected void onBtnSaveOnClick(){

    }
    @FXML
    protected void onBtnCancelOnClick(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configTextField();
    }

    private void configTextField(){
        Constraints.setTextFieldMaxLength(departmentNameTextField, 15);
    }
}
