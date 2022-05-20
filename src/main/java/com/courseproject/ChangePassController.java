package com.courseproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePassController {

    @FXML
    private PasswordField confpass;

    @FXML
    private Label errorlabel;

    @FXML
    private PasswordField newpass;

    @FXML
    private PasswordField oldpass;

    @FXML
    private Button save;

    @FXML
    protected void initialize() {
        save.setOnAction(actionEvent -> {
            String oldp = oldpass.getText().trim();
            String newp = newpass.getText().trim();
            String rnewp = confpass.getText().trim();
            DatabaseHandler dbhandler = new DatabaseHandler();

            if (!oldp.equals("") && !newp.equals("") && !rnewp.equals("")) {
                errorlabel.setText("");
                if (dbhandler.checkpass(oldp, Controller.userId)) {
                    errorlabel.setText("");
                    if (newp.equals(rnewp)) {
                        dbhandler.editWorpass(Controller.userLogin, newp);
                        errorlabel.setText("");

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

                        oldpass.clear();
                        newpass.clear();
                        confpass.clear();
                    } else {
                        errorlabel.setText("New password does not match");
                    }
                } else {
                    errorlabel.setText("Current password is invalid");
                }
            } else {
                errorlabel.setText("Fill in all fields");
            }

        });
    }

}
