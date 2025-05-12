package p_henriqued.javafxstudy.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import p_henriqued.javafxstudy.MainApplication;
import p_henriqued.javafxstudy.util.Alert.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;


    @FXML
    protected void onMenuItemDepartmentAction(){
        loadView("/p_henriqued/javafxstudy/gui/DepartmentView.fxml");
    }

    @FXML
    protected void onMenuItemAboutAction(){
        loadView("/p_henriqued/javafxstudy/gui/AboutView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void loadView(String absoluteName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVbox = loader.load();

            Scene mainScene = MainApplication.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVbox.getChildren().getFirst();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVbox.getChildren());
        } catch (IOException e) {
            Alerts.AlertShow("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


}