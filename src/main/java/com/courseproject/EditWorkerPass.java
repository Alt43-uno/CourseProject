package com.courseproject;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.courseproject.animated.Shake;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EditWorkerPass {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField confirmfield;

    @FXML
    private PasswordField newpasswordfield;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button save;


    @FXML
    private TextField login;

    @FXML
    private Label error;

    @FXML
    protected void initialize() {
        save.setOnAction(event -> {
            String log = login.getText().trim();
            String pass = String.valueOf(passwordfield.getText()).trim();
            String newpass = String.valueOf(newpasswordfield.getText()).trim();
            String conpass = String.valueOf(confirmfield.getText()).trim();


            if (!pass.equals("") && !pass.equals("") && !newpass.equals("") && !conpass.equals("")) {
                try {
                    if (log.equals(Controller.userLogin)) {
                        if (loginUser(log, pass)) {
                            if (newpass.equals(conpass)) {
                                DatabaseHandler dbHandler = new DatabaseHandler();
                                dbHandler.editWorpass(log, newpass);
                                error.setText("Password changed :)");

                            } else {
                                Shake userLoginAnim = new Shake(newpasswordfield);
                                Shake userPassAnim = new Shake(confirmfield);
                                userLoginAnim.playAnim();
                                userPassAnim.playAnim();
                                error.setText("Passwords do not match!");
                            }
                        } else {
                            Shake userLoginAnim = new Shake(login);
                            Shake userPassAnim = new Shake(passwordfield);
                            userLoginAnim.playAnim();
                            userPassAnim.playAnim();
                            error.setText("Invalid login or password.");
                        }
                    } else {
                        error.setText("Login is wrong!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error");
//                if (log.equals("") && pass.equals("")) {
//                    Shake userLoginAnim=new Shake(login);
//                    Shake userPassAnim=new Shake(passwordfield);
//                    userLoginAnim.playAnim();
//                    userPassAnim.playAnim();
//                    error.setText("Enter Login and Password!");
//                }
                if (log.equals("")) {
                    Shake userLoginAnim = new Shake(login);
                    userLoginAnim.playAnim();
                    error.setText("Fill in the labels!");
                }
                if (pass.equals("")) {
                    Shake userPassAnim = new Shake(passwordfield);
                    userPassAnim.playAnim();
                    error.setText("Fill in the labels!");
                }
                if (newpass.equals("")) {
                    Shake userPassAnim = new Shake(newpasswordfield);
                    userPassAnim.playAnim();
                    error.setText("Fill in the labels!");
                }
                if (conpass.equals("")) {
                    Shake userPassAnim = new Shake(confirmfield);
                    userPassAnim.playAnim();
                    error.setText("Fill in the labels!");
                }
//                else if(log.equals("") && newpass.equals("")){
//                    Shake userLoginAnim=new Shake(login);
//                    Shake userPassAnim=new Shake(newpasswordfield);
//                    userLoginAnim.playAnim();
//                    userPassAnim.playAnim();
//                    error.setText("Enter Login and New password");
//                }else if(log.equals("") && conpass.equals("")){
//                    Shake userLoginAnim=new Shake(login);
//                    Shake userPassAnim=new Shake(confirmfield);
//                    userLoginAnim.playAnim();
//                    userPassAnim.playAnim();
//                    error.setText("Enter Login and Confirm password");
//                }else if(pass.equals("") && conpass.equals("")){
//                    Shake userLoginAnim=new Shake(confirmfield);
//                    Shake userPassAnim=new Shake(passwordfield);
//                    userLoginAnim.playAnim();
//                    userPassAnim.playAnim();
//                    error.setText("Enter Password and Confirm password");
//                }else if(pass.equals("") && newpass.equals("")){
//                    Shake userLoginAnim=new Shake(newpasswordfield);
//                    Shake userPassAnim=new Shake(passwordfield);
//                    userLoginAnim.playAnim();
//                    userPassAnim.playAnim();
//                    error.setText("Enter Password and New password");
//                }
            }
        });
    }

    private boolean loginUser(String login, String password) throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        while (true) {
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            counter++;
        }
        if (counter >= 1) {
            return true;
        }

        return false;
    }
}
