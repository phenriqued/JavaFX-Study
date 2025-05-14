package p_henriqued.javafxstudy.Controller.SellerController;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;
import p_henriqued.javafxstudy.MainApplication;
import p_henriqued.javafxstudy.Servicies.SellersService.SellerService;
import p_henriqued.javafxstudy.listeners.DataChangeListener;
import p_henriqued.javafxstudy.models.Sellers.Seller;
import p_henriqued.javafxstudy.util.Alert.Alerts;
import p_henriqued.javafxstudy.util.Utils;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerController implements Initializable, DataChangeListener {

    @FXML
    private TableView<Seller> tableViewSeller;
    @FXML
    private TableColumn<Seller, Long> tableColumnIdSeller;
    @FXML
    private TableColumn<Seller, String> tableColumnNameSeller;
    @FXML
    private TableColumn<Seller, String> tableColumnEmailSeller;
    @FXML
    private TableColumn<Seller, LocalDate> tableColumnBirthDateSeller;
    @FXML
    private TableColumn<Seller, BigDecimal> tableColumnBaseSalarySeller;
    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;
    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;
    @FXML
    private Button buttonNew;

    @Setter
    private SellerService service;
    private ObservableList<Seller> obsListSeller;

    @FXML
    public void onButtonNewClick(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Seller seller = new Seller();
        createDialogForm(seller, "/p_henriqued/javafxstudy/gui/DepartmentFormView.fxml", parentStage);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnIdSeller.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNameSeller.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnEmailSeller.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnBirthDateSeller.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tableColumnBaseSalarySeller.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));

        Scene mainScene = MainApplication.getMainScene();
        tableViewSeller.prefHeightProperty().bind(mainScene.heightProperty());
    }

    public void updateTableView(){
        if(Objects.isNull(service)) throw new IllegalStateException("Service was null");

        List<Seller> departmentList = service.findAllDepartments();
        obsListSeller = FXCollections.observableArrayList(departmentList);
        tableViewSeller.setItems(obsListSeller);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogForm(Seller seller, String absoluteName, Stage parentStage){
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//            Pane pane = loader.load();
//
//            DepartmentFormsController formsController = loader.getController();
//            formsController.setDepartmentEntity(dep);
//            formsController.setService(new DepartmentService());
//            formsController.subscribeDataChangeListener(this);
//            formsController.updateFormData();
//            if(formsController.getIdDepartmentLabel().getText().equalsIgnoreCase("null")) formsController.getIdDepartmentLabel().setText("");
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Enter department data");
//            dialogStage.setScene(new Scene(pane));
//            dialogStage.setResizable(false);
//            dialogStage.initOwner(parentStage);
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.showAndWait();
//        } catch (ValidationException | IOException e) {
//            Alerts.AlertShow("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
//        }
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("edit");
            @Override
            protected void updateItem(Seller obj, boolean empty) {
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
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");
            @Override
            protected void updateItem(Seller obj, boolean empty) {
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

    private void removeEntity(Seller obj) {
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
