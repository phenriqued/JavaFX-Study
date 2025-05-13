package p_henriqued.javafxstudy.Controller.DepartmentController;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import p_henriqued.javafxstudy.MainApplication;
import p_henriqued.javafxstudy.Servicies.DepartmentService.DepartmentService;
import p_henriqued.javafxstudy.listeners.DataChangeListener;
import p_henriqued.javafxstudy.models.Department.Department;
import p_henriqued.javafxstudy.util.Alert.Alerts;
import p_henriqued.javafxstudy.util.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DepartmentController implements Initializable, DataChangeListener {

    @FXML
    private TableView<Department> tableViewDepartment;
    @FXML
    private TableColumn<Department, Long> tableColumnIdDepartment;
    @FXML
    private TableColumn<Department, String> tableColumnNameDepartment;
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
        } catch (IOException e) {
            Alerts.AlertShow("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }
}
