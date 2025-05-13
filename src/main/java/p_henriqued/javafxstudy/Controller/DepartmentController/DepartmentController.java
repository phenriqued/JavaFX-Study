package p_henriqued.javafxstudy.Controller.DepartmentController;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;
import p_henriqued.javafxstudy.MainApplication;
import p_henriqued.javafxstudy.Servicies.DepartmentService.DepartmentService;
import p_henriqued.javafxstudy.models.Department.Department;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DepartmentController implements Initializable {

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
    public void onButtonNewClick(){

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
}
