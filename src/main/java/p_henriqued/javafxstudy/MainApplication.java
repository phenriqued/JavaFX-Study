package p_henriqued.javafxstudy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import lombok.Getter;
import p_henriqued.javafxstudy.Repositories.Repository.Repository;

import java.io.IOException;

public class MainApplication extends Application {

    @Getter
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/MainView.fxml"));
            ScrollPane scrollPane = loader.load();
            this.mainScene = new Scene(scrollPane);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            primaryStage.setScene(mainScene);
            new Repository<>();
            primaryStage.setTitle("JavaFX application");
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}