package com.courseproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DialogController {

    @FXML
    private Button okDialog;

    @FXML
    private Text textDialog;

    @FXML
    protected void initialize() {
        textDialog.setText("Successfully saved!");
        okDialog.setOnAction(event -> {
            okDialog.getScene().getWindow().hide();
        });
    }
}
