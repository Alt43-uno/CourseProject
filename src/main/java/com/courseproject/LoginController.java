package com.courseproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    ObservableList<String> roles = FXCollections.observableArrayList("hrmanager", "worker", "director");

    @FXML
    ComboBox<String> roleField;

    @FXML
    Label lbl = new Label();

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label infoLabel;

    @FXML
    protected void initialize() {

        roleField.setItems(roles);
        roleField.setOnAction(event -> lbl.setText(roleField.getValue()));
        loginButton.setOnAction(event -> {
            String role = roleField.getValue();
            String login = loginField.getText().trim();
            String pass = passwordField.getText();
            if (!login.equals("") && !pass.equals("") && role != null) {

                try {
                    if (loginUser(role, login, pass)) {
                        System.out.println("LOGIN SUCCESSFUL");
//                        loginButton.getScene().getWindow().hide();

                        if (role.equals("worker")) {

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("worker.fxml"));
                            try {
                                loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Parent root = loader.getRoot();
                            Stage stage = new Stage();
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.setTitle(Controller.userName + " Worker");

                            stage.show();
                        } else if (role.equals("hrmanager")) {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("hrmanager.fxml"));
                            try {
                                loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Parent root = loader.getRoot();
                            Stage stage;
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.setTitle("HRManager Panel");

                            stage.show();
                        } else if (role.equals("director")) {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("director.fxml"));
                            try {
                                loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Parent root = loader.getRoot();
                            Stage stage = new Stage();
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Director Panel");

                            stage.show();
                        }
                        infoLabel.setId("greenText");
                        infoLabel.setText("SUCCESS");
                    } else {
                        infoLabel.setText("Invalid login or password.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            } else {
                System.out.println("Error");
                if (role == null) {
                    infoLabel.setText("Choose your role.");
                } else {
                    if (login.equals("") && pass.equals("")) {
                        infoLabel.setText("Enter Login and Password.");
                    } else if (login.equals("")) {
                        infoLabel.setText("Enter Login.");
                    } else {
                        infoLabel.setText("Enter Password.");
                    }
                }
//                infoLabel.setText("");
                infoLabel.setId("errorText");

            }
        });
    }

    private boolean loginUser(String role, String login, String pass) throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(login);
        user.setPassword(pass);

        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        while (true) {
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (result.getString("role").equals(role)) {
                Controller.setUser(result.getInt("id"),
                        result.getString("name"),
                        result.getString("login"),
                        result.getString("role"),
                        result.getInt("salary"),
                        result.getString("pos"),
                        result.getInt("bonus"));
            } else {
                return false;
            }


            counter++;
        }
        return counter >= 1;
    }
}
