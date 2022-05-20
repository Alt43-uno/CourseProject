package com.courseproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.courseproject.Controller.copyByStream;

public class WorkerController {
    ObservableList<String> sal = FXCollections.observableArrayList("per day", "per month", "per year");

    ObservableList<String> bon = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text bonus;

    @FXML
    private Text errorLabel;

    @FXML
    private Text greetings;

    @FXML
    private Button logout;

    @FXML
    private ComboBox<String> period;

    @FXML
    private Text name;

    @FXML
    private Text position;


    @FXML
    Label lbl = new Label();


    @FXML
    private Text salary;


    @FXML
    private Button editPass;

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
    private TextField moFn;

    @FXML
    private TextField moSt;

    @FXML
    private TextField frFn;

    @FXML
    private TextField frSt;

    @FXML
    private Text entry;

    @FXML
    private Hyperlink editAvatar;

    @FXML
    private Circle avatar;

    @FXML
    protected void initialize() {
        DatabaseHandler dbhandler = new DatabaseHandler();
        name.setText(Controller.userName);
        position.setText(Controller.userPos);
        setForm(Controller.userId);

        File resourcesDir = new File("src/main/resources/com/courseproject/img");
        File nullavatar = new File(resourcesDir.getAbsolutePath() + "/" + Controller.userId + ".png");
        if (nullavatar.exists()) {
            Image image = new Image(resourcesDir.getAbsolutePath() + "/" + Controller.userId + ".png");
            avatar.setFill(new ImagePattern(image));
        } else {
            System.out.println("not exist");
            Image image = new Image(resourcesDir.getAbsolutePath() + "/" + "0.png");
            avatar.setFill(new ImagePattern(image));
        }

        entry.setText(dbhandler.getEntryById(Controller.userId));
        period.setItems(sal);
        salary.setText(Controller.userSalary + " som");
        bonus.setText(Controller.userBonus + " som");

        period.setOnAction(event -> {
            String sa = period.getValue();
            lbl.setText(period.getValue());
            if (sa.equals("per month")) {
                salary.setText(Controller.userSalary + " som");
            } else if (sa.equals("per year")) {
                salary.setText(Controller.userSalary * 12 + " som");
            } else {
                salary.setText(Controller.userSalary / 30 + " som");
            }

        });

        editAvatar.setOnAction(actionEvent -> {
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(imageFilter);
            String FileDis = String.valueOf(fileChooser.showOpenDialog(null));
            System.out.println(FileDis);
            Image imagee = new Image(FileDis);
            avatar.setFill(new ImagePattern(imagee));
            File resourcesDirectory = new File("src/main/resources/com/courseproject/img");
            System.out.println(resourcesDirectory.getAbsolutePath());
            try {
                copyByStream(FileDis, resourcesDirectory.getAbsolutePath() + "/" + Controller.userId + ".png");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        logout.setOnAction(event -> {
            logout.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("log_in.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        });

        editPass.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("changepass.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        });
    }

    private void setForm(int id) {

        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resulttime = dbHandler.getTimeById(id);
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

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
