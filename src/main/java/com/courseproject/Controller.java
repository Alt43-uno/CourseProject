package com.courseproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Controller {

    static int userId;
    static String userName;
    static String userLogin;
    static String userRole;
    static int userSalary;
    static String userPos;
    static int userBonus;

    static void setUser(int id, String name, String login, String role, int salary, String pos, int bonus) {
        userId = id;
        userName = name;
        userLogin = login;
        userSalary = salary;
        userRole = role;
        userPos = pos;
        userBonus = bonus;

        System.out.println(userId);
        System.out.println(userName);
        System.out.println(userLogin);
        System.out.println(userSalary);
        System.out.println(userRole);
        System.out.println(userPos);
        System.out.println(userBonus);

    }

    static void setUserId(int id) {
        userId = id;
        System.out.println(userId);
    }

    @FXML
    private Text greetings;

    @FXML
    private Label infoLabel;

    @FXML
    private Button logout;

    @FXML
    protected void initialize() {
        logout.setOnAction(event -> {
            System.out.println("exit");
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
    }

    ;

    public static void copyByStream(String sourcePath, String targetPath) throws Exception {
        // Открываем входной поток
        FileInputStream fis = new FileInputStream(sourcePath);
        // Открываем выходной поток
        FileOutputStream fos = new FileOutputStream(targetPath);

        // читать и записывать информацию
        int len = 0;
        while ((len = fis.read()) != -1) {
            fos.write(len);
        }

        // Закройте поток, сначала откройте, затем закройте, затем сначала откройте, закройте
        fos.close(); // После открытия, первое закрытие
        fis.close(); // сначала открываем, а потом закрываем
    }

}
