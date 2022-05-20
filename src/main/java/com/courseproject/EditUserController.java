package com.courseproject;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EditUserController {

    ObservableList<String> roles = FXCollections.observableArrayList("hrmanager", "worker", "director");

    @FXML
    Label lbl = new Label();

    @FXML
    private Button butSave;

    @FXML
    private Button butDel;

    @FXML
    private ComboBox<String> chooseUser;

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

    @FXML
    protected void initialize() throws SQLException {
        role.setItems(roles);
        try {
            chooseUser.setItems(setUsersList());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        chooseUser.setOnAction(event -> {
            int choosedUser = Integer.parseInt(chooseUser.getValue().split(" ")[0]);
            System.out.println(choosedUser);
            setForm(choosedUser);
        });
        butSave.setOnAction(event -> {
            DatabaseHandler dbhandler = new DatabaseHandler();
            int choosedUser = Integer.parseInt(chooseUser.getValue().split(" ")[0]);

            dbhandler.editDbUser(choosedUser, name.getText(), login.getText(), password.getText(), role.getValue(), Integer.parseInt(salary.getText()), position.getText(),
                    moSt.getText(), moFn.getText(), tuSt.getText(), tuFn.getText(),
                    weSt.getText(), weFn.getText(), thSt.getText(), thFn.getText(),
                    frSt.getText(), frFn.getText());

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
        });
        butDel.setOnAction(event -> {
            DatabaseHandler dbhandler = new DatabaseHandler();
            int choosedUser = Integer.parseInt(chooseUser.getValue().split(" ")[0]);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Removing User");
            alert.setHeaderText("Do you really want to fire an employee?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {
                dbhandler.removeUser(choosedUser);
                try {
                    chooseUser.getSelectionModel().clearSelection();
                    chooseUser.setItems(setUsersList());
                } catch (SQLException e) {
                    System.out.println("ok");
                }
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
            }


        });
    }

    @FXML
    private ObservableList<String> setUsersList() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<String> usersList = FXCollections.observableArrayList();

        ResultSet result = dbHandler.getAllUser();
        System.out.println("Work");
        while (true) {
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (result.getString("status").equals("Works")) {
                usersList.add(result.getInt("id") + " " + result.getString("name"));
            }
        }
        return usersList;
    }

    private void setForm(int id) {

        DatabaseHandler dbHandler = new DatabaseHandler();

        ResultSet result = dbHandler.getUserById(id);
        ResultSet resulttime = dbHandler.getTimeById(id);
        int counter = 0;
        while (true) {
            try {
                if (!resulttime.next()) break;
                moSt.setText(resulttime.getString("moSt"));
                moFn.setText(resulttime.getString("moFn"));
                thSt.setText(resulttime.getString("thSt"));
                thFn.setText(resulttime.getString("thFn"));
                frSt.setText(resulttime.getString("frSt"));
                frFn.setText(resulttime.getString("frFn"));
                weSt.setText(resulttime.getString("weSt"));
                weFn.setText(resulttime.getString("weFn"));
                tuSt.setText(resulttime.getString("tuSt"));
                tuFn.setText(resulttime.getString("tuFn"));
                counter++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (counter <= 0) {
            moSt.setText("");
            moFn.setText("");
            thSt.setText("");
            thFn.setText("");
            frSt.setText("");
            frFn.setText("");
            weSt.setText("");
            weFn.setText("");
            tuSt.setText("");
            tuFn.setText("");

        }
        while (true) {
            try {
                if (!result.next()) break;
                name.setText(result.getString("name"));
                salary.setText(result.getString("salary"));
                login.setText(result.getString("login"));
                password.setText(result.getString("password"));
                position.setText(result.getString("pos"));
                role.setValue(result.getString("role"));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
