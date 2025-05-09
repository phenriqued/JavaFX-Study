package p_henriqued.javafxstudy.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import p_henriqued.javafxstudy.Entities.UserEntity.UserEntity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PersonController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField numberPhoneField;
    @FXML
    private Label resultUserLabel;
    @FXML
    private ComboBox<UserEntity> userComboBox;
    private ObservableList<UserEntity> obsList;
    private List<UserEntity> listUsers = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Callback<ListView<UserEntity>, ListCell<UserEntity>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(UserEntity item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        userComboBox.setCellFactory(factory);
        userComboBox.setButtonCell(factory.call(null));
        obsList = FXCollections.observableList(listUsers);
        userComboBox.setItems(obsList);
    }

    @FXML
    protected void onSendButtonClick(){
        if(emptyString()) return;
        String name = nameField.getText();
        String email = emailField.getText();
        String numberPhone = numberPhoneField.getText();

        obsList.add(new UserEntity(name, email, numberPhone));
        nameField.clear();
        emailField.clear();
        numberPhoneField.clear();
    }
    @FXML
    protected void onComboBoxClick(){
        UserEntity user = userComboBox.getSelectionModel().getSelectedItem();
        if(Objects.isNull(user)) return;
        resultUserLabel.setText("");
        resultUserLabel.setText(user.toString());
    }
    @FXML
    protected void onCleanClick(){
        resultUserLabel.setText("Selecione um usuário");
    }
    @FXML
    protected void onCleanAllUsersClick(){
        resultUserLabel.setText("Usuários Apagados!");
        obsList.clear();
        listUsers.clear();
    }

    private Boolean emptyString(){
        return (nameField.getText().isEmpty() || emailField.getText().isEmpty() || numberPhoneField.getText().isEmpty());
    }


}
