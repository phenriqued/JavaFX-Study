package p_henriqued.javafxstudy.Controller.DepartmentController;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import p_henriqued.javafxstudy.Servicies.DepartmentService.DepartmentService;
import p_henriqued.javafxstudy.listeners.DataChangeListener;
import p_henriqued.javafxstudy.models.Department.Department;
import p_henriqued.javafxstudy.util.Alert.Alerts;
import p_henriqued.javafxstudy.util.Constraints.Constraints;
import p_henriqued.javafxstudy.util.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DepartmentFormsController implements Initializable {

    @Setter
    private Department departmentEntity;
    @Setter
    private DepartmentService service;
    List<DataChangeListener> dataChangeListeners = new ArrayList<>();
    @Getter
    @FXML
    private Label idDepartmentLabel;
    @FXML
    private TextField departmentNameTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @FXML
    protected void onBtnSaveOnClick(ActionEvent event){
        if (Objects.isNull(departmentEntity) || Objects.isNull(service)) throw new IllegalStateException("Entity or Service can't be null.");
        if(Objects.isNull(departmentNameTextField) || departmentNameTextField.getText().isBlank()) return;
        try {
            departmentEntity = getFormData();
            service.saveOrUpdate(departmentEntity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }catch (Exception e){
            Alerts.AlertShow("Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onBtnCancelOnClick(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configTextField();
    }

    public void updateFormData() {
        if (Objects.isNull(departmentEntity)) throw new IllegalStateException("Entity was null");

        idDepartmentLabel.setText(String.valueOf(departmentEntity.getId()));
        departmentNameTextField.setText(departmentEntity.getName());
    }

    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    private void configTextField(){
        Constraints.setTextFieldMaxLength(departmentNameTextField, 15);
    }

    private Department getFormData(){
        Department dep = new Department();
        dep.setId(Utils.tryParseToLong(idDepartmentLabel.getText()));
        dep.setName(departmentNameTextField.getText());
        return dep;
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

}
