package p_henriqued.javafxstudy.Controller.DepartmentController;


import jakarta.persistence.RollbackException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import p_henriqued.javafxstudy.MainApplication;
import p_henriqued.javafxstudy.Servicies.DepartmentService.DepartmentService;
import p_henriqued.javafxstudy.infra.Exceptions.ValidationException;
import p_henriqued.javafxstudy.listeners.DataChangeListener;
import p_henriqued.javafxstudy.models.Department.Department;
import p_henriqued.javafxstudy.util.Alert.Alerts;
import p_henriqued.javafxstudy.util.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class DepartmentController implements Initializable, DataChangeListener {

    @FXML
    private TableView<Department> tableViewDepartment;
    @FXML
    private TableColumn<Department, Long> tableColumnIdDepartment;
    @FXML
    private TableColumn<Department, String> tableColumnNameDepartment;
    @FXML
    private TableColumn<Department, Department> tableColumnEDIT;
    @FXML
    private TableColumn<Department, Department> tableColumnREMOVE;
    @FXML
    private Button buttonNew;

    @Setter
    private DepartmentService service;
    private ObservableList<Department> obsListDepartment;

    @FXML
    public void onButtonNewClick(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Department dep = new Department();
        createDialogForm(dep, "/p_henriqued/javafxstudy/gui/DepartmentFormView.fxml", parentStage);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnIdDepartment.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNameDepartment.setCellValueFactory(new PropertyValueFactory<>("name"));

        Scene mainScene = MainApplication.getMainScene();
        tableViewDepartment.prefHeightProperty().bind(mainScene.heightProperty());
    }

    public void updateTableView(){
        if(Objects.isNull(service)) throw new IllegalStateException("Service was null");

        List<Department> departmentList = service.findAllDepartments();
        obsListDepartment = FXCollections.observableArrayList(departmentList);
        tableViewDepartment.setItems(obsListDepartment);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogForm(Department dep, String absoluteName, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            DepartmentFormsController formsController = loader.getController();
            formsController.setDepartmentEntity(dep);
            formsController.setService(new DepartmentService());
            formsController.subscribeDataChangeListener(this);
            formsController.updateFormData();
            if(formsController.getIdDepartmentLabel().getText().equalsIgnoreCase("null")) formsController.getIdDepartmentLabel().setText("");

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter department data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (ValidationException | IOException e) {
            Alerts.AlertShow("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("edit");
            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "/p_henriqued/javafxstudy/gui/DepartmentFormView.fxml",Utils.currentStage(event)));
            }
        });
    }
    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("remove");
            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Department obj) {
        if(service == null) throw new IllegalStateException("Service was null");
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        if(result.get() == ButtonType.OK){
            try{
                service.remove(obj.getId());
                updateTableView();
            }catch (Exception e){
                Alerts.AlertShow("Error removing object", null, "Unable to complete action.", Alert.AlertType.ERROR);
            }
        }
    }

}
