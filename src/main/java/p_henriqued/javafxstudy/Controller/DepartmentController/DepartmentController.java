package p_henriqued.javafxstudy.Controller.DepartmentController;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import p_henriqued.javafxstudy.MainApplication;
import p_henriqued.javafxstudy.models.Department.Department;

import java.net.URL;
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
}
