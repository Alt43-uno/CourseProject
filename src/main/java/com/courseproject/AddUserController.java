package com.courseproject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class AddUserController {
    ObservableList<String> roles = FXCollections.observableArrayList("hrmanager", "worker", "director");

    final ToggleGroup group = new ToggleGroup();

    @FXML
    private RadioButton apache;
    @FXML
    private RadioButton female;
    @FXML
    private RadioButton male;

    Label selectedLbl = new Label();

    @FXML
    private Button butAdd;

    @FXML
    private Text errorLabel;

    @FXML
    private TextField frFn;

    @FXML
    private TextField frSt;

    @FXML
    private TextField login;

    @FXML
    private TextField moFn;

    @FXML
    private TextField moSt;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private TextField position;

    @FXML
    private ComboBox<String> role;
    @FXML
    Label lbl = new Label();

    @FXML
    private TextField salary;

    @FXML
    private TextField thFn;

    @FXML
    private TextField thSt;

    @FXML
    private TextField tuFn;

    @FXML
    private TextField tuSt;

    @FXML
    private TextField weFn;

    @FXML
    private TextField weSt;

    private String selectedradio = "Male";

    @FXML
    protected void initialize() {
        male.setToggleGroup(group);
        female.setToggleGroup(group);
        apache.setToggleGroup(group);
        male.setSelected(true);
        role.setItems(roles);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            public void changed(ObservableValue<? extends Toggle> changed, Toggle oldValue, Toggle newValue) {

                RadioButton selectedBtn = (RadioButton) newValue;
                selectedradio = selectedBtn.getText();
            }
        });
        role.setOnAction(event -> lbl.setText(role.getValue()));
        butAdd.setOnAction(event -> {
            String nameValue = name.getText();
            String loginValue = login.getText();
            String passValue = password.getText();
            int salaryValue = Integer.parseInt(salary.getText());
            String positionValue = position.getText();
            DatabaseHandler dbHandler = new DatabaseHandler();

            if (!nameValue.equals("") && !loginValue.equals("")
                    && !passValue.equals("") && !positionValue.equals("") && role != null) {
                errorLabel.setText("");
                dbHandler.signUpUser(nameValue, loginValue, passValue, role.getValue(), salaryValue, positionValue, selectedradio, moSt.getText(), moFn.getText(), tuSt.getText(), tuFn.getText(), weSt.getText(), weFn.getText(), thSt.getText(), thFn.getText(), frSt.getText(), frFn.getText());
                System.out.println("Success!");
                butAdd.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("dialog.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Success!");
                stage.show();

            } else {
                errorLabel.setText("Fill all fields.");
            }

        });

    }
}
