package p_henriqued.javafxstudy.Controller.SellerController;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;
import p_henriqued.javafxstudy.Servicies.DepartmentService.DepartmentService;
import p_henriqued.javafxstudy.Servicies.SellersService.SellerService;
import p_henriqued.javafxstudy.infra.Exceptions.ValidationException;
import p_henriqued.javafxstudy.listeners.DataChangeListener;
import p_henriqued.javafxstudy.models.Department.Department;
import p_henriqued.javafxstudy.models.Sellers.Seller;
import p_henriqued.javafxstudy.util.Constraints.Constraints;
import p_henriqued.javafxstudy.util.Utils;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormsController implements Initializable {

    @Setter
    private Seller sellerEntity;
    @Setter
    private SellerService service;
    @Setter
    private DepartmentService departmentService;
    private ObservableList<Department> obsList;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
    @Getter
    @FXML
    private Label idSellerLabel;
    @FXML
    private TextField sellerNameTextField;
    @FXML
    private Label errorNameLabel;
    @FXML
    private TextField sellerEmailTextField;
    @FXML
    private Label errorEmailLabel;
    @FXML
    private DatePicker sellerBirthDateTextField;
    @FXML
    private Label errorBirthDateLabel;
    @FXML
    private TextField sellerBaseSalaryTextField;
    @FXML
    private Label errorBaseSalaryLabel;
    @FXML
    private ComboBox<Department> comboBoxDepartment;
    @FXML
    private Label errorDepartmentLabel;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @FXML
    protected void onBtnSaveOnClick(ActionEvent event){
        if (Objects.isNull(sellerEntity) || Objects.isNull(service)) throw new IllegalStateException("Entity or Service can't be null.");
        try {
            sellerEntity = getFormData();
            service.saveOrUpdate(sellerEntity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (ValidationException e){
            setErrorMessages(e.getError());
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
        if (Objects.isNull(sellerEntity)) throw new IllegalStateException("Entity was null");

        Locale.setDefault(Locale.US);
        idSellerLabel.setText(String.valueOf(sellerEntity.getId()));
        sellerNameTextField.setText(sellerEntity.getName());
        sellerEmailTextField.setText(sellerEntity.getEmail());
        sellerBirthDateTextField.setValue(sellerEntity.getBirthDate());
        sellerBaseSalaryTextField.setText(String.format("%.2f",sellerEntity.getBaseSalary()));
        if(sellerEntity.getDepartment() == null){
            comboBoxDepartment.getSelectionModel().clearSelection();
        }else{
            comboBoxDepartment.setValue(sellerEntity.getDepartment());
        }
    }

    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    private void configTextField(){
        Constraints.setTextFieldMaxLength(sellerNameTextField, 70);
        Constraints.setTextFieldMaxLength(sellerEmailTextField, 60);
        Utils.formatDatePicker(sellerBirthDateTextField, "dd/MM/yyyy");
        Constraints.setTextFieldBigDecimal(sellerBaseSalaryTextField);
        initializeComboBoxDepartment();
    }

    public void loadAssociatedObjects(){
        if(Objects.isNull(departmentService)) throw new IllegalStateException("The Department Service was Null");
        List<Department> departmentList = departmentService.findAllDepartments();
        obsList = FXCollections.observableList(departmentList);
        comboBoxDepartment.setItems(obsList);
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

    private Seller getFormData(){
        cleanLabels();
        Seller dep = new Seller();
        dep.setId(Utils.tryParseToLong(idSellerLabel.getText()));
        if(Objects.isNull(sellerNameTextField.getText()) || sellerNameTextField.getText().trim().isEmpty())
            throw new ValidationException("Name Seller", "The seller name cannot be null");
        dep.setName(sellerNameTextField.getText());
        if(Objects.isNull(sellerEmailTextField.getText()) || sellerEmailTextField.getText().trim().isEmpty())
            throw new ValidationException("Email Seller", "The seller Email cannot be null");
        dep.setEmail(sellerEmailTextField.getText());
        if(Objects.isNull(sellerBirthDateTextField.getValue()))  throw new ValidationException("Birth of Date Seller", "The Seller's date of birth cannot be null");
        if(!sellerBirthDateTextField.getValue().isBefore(LocalDate.now())) throw new ValidationException("Birth of Date Seller", "Something wrong with the seller's date of birth.");
        dep.setBirthDate(sellerBirthDateTextField.getValue());
        if(Objects.isNull(sellerBaseSalaryTextField.getText()) || sellerBaseSalaryTextField.getText().trim().isEmpty())
            throw new ValidationException("Base Salary Seller", "The seller Base Salary cannot be null");
        if(Double.parseDouble(sellerBaseSalaryTextField.getText()) <= 0)
            throw new ValidationException("Base Salary Seller", "Something wrong with the seller's Base Salary");
        dep.setBaseSalary(new BigDecimal(sellerBaseSalaryTextField.getText()));
        if(comboBoxDepartment.getSelectionModel().isEmpty()){
            throw new ValidationException("Seller's Department", "The seller has to be included in a department");
        }
        dep.setDepartment(comboBoxDepartment.getValue());
        return dep;
    }

    private void cleanLabels() {
        errorNameLabel.setText("");
        errorEmailLabel.setText("");
        errorBirthDateLabel.setText("");
        errorBaseSalaryLabel.setText("");
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    public void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if(fields.contains("Name Seller"))
            errorNameLabel.setText(errors.get("Name Seller"));
        if(fields.contains("Email Seller"))
            errorEmailLabel.setText(errors.get("Email Seller"));
        if(fields.contains("Birth of Date Seller"))
            errorBirthDateLabel.setText(errors.get("Birth of Date Seller"));
        if(fields.contains("Base Salary Seller"))
            errorBaseSalaryLabel.setText(errors.get("Base Salary Seller"));
        if(fields.contains("Seller's Department"))
            errorDepartmentLabel.setText(errors.get("Seller's Department"));
    }

}
