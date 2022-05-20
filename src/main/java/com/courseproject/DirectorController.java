package com.courseproject;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.courseproject.Controller.copyByStream;

public class DirectorController {
    ObservableList<String> sal = FXCollections.observableArrayList("per day", "per month", "per year");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ApplyButtom;

    @FXML
    private Button ChangeSalaryButtom;

    @FXML
    private TableColumn<DirectorVariables, Integer> BonusColumn;

    @FXML
    private AnchorPane DirMenu;

    @FXML
    private TableView<User> DirTable;

    @FXML
    private Text DirText;

    @FXML
    private TableColumn<DirectorVariables, Integer> IDColumn;


    @FXML
    private Button SalaryReportButtom;

    @FXML
    private Button SearchButtom;

    @FXML
    private TableColumn<DirectorVariables, String> NameColumn;

    @FXML
    private TableColumn<DirectorVariables, String> PositionColumn;

    @FXML
    private TableColumn<DirectorVariables, String> RoleColumn;

    @FXML
    private TableColumn<DirectorVariables, String> StatusColumn;

    @FXML
    private TableColumn<DirectorVariables, Integer> SalaryColumn;

    @FXML
    private Circle avatar;

    @FXML
    private Button editPass;

    @FXML
    private Button emp_table;

    @FXML
    private AnchorPane profileBlock;

    @FXML
    private Button profile_but;

    @FXML
    private Hyperlink editAvatar;

    @FXML
    private TextField frFn;

    @FXML
    private TextField frSt;

    @FXML
    Label lbl = new Label();

    @FXML
    private PieChart genderChart;

    @FXML
    private Button logout;

    @FXML
    private TextField moFn;

    @FXML
    private TextField moSt;

    @FXML
    private Text name;

    @FXML
    private ComboBox<String> period;

    @FXML
    private Text position;

    @FXML
    private Button refresh;

    @FXML
    private Button searchButton;

    @FXML
    private Text salary;

    @FXML
    private ComboBox<String> sr_st;

    @FXML
    private AnchorPane statsBlock;

    @FXML
    private Button stats_but;

    @FXML
    private AnchorPane tableBlock;

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
    private Text allWorkers;

    @FXML
    private Text allbonuses;

    @FXML
    private Text allhrs;

    @FXML
    private Text allsalaries;

    @FXML
    private PieChart salaryChart;

    @FXML
    private Text bigbonus;

    @FXML
    private Text bigsalary;

    ObservableList<String> statusList = FXCollections.observableArrayList("Works", "Fired");
    ObservableList<User> usersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {

        DatabaseHandler dbhandler = new DatabaseHandler();

        ObservableList<PieChart.Data> gender = FXCollections.observableArrayList(
                new PieChart.Data("Male", dbhandler.getMale()),
                new PieChart.Data("Other", dbhandler.getOtherGender()),
                new PieChart.Data("Female", dbhandler.getFemale()));
        ObservableList<PieChart.Data> salaryData = FXCollections.observableArrayList();

        ResultSet resultSalary = dbhandler.getSalary();
        int sallow10 = 0;
        int sallow50 = 0;
        int sallow80 = 0;
        int salmore80 = 0;
        while (true) {
            try {
                if (!resultSalary.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (resultSalary.getInt("salary") < 10000) {
                sallow10++;
            } else if (resultSalary.getInt("salary") < 50000) {
                sallow50++;
            } else if (resultSalary.getInt("salary") < 80000) {
                sallow80++;
            } else if (resultSalary.getInt("salary") > 80000) {
                salmore80++;
            }
        }

        salaryData.add(new PieChart.Data("< 10.000", sallow10));
        salaryData.add(new PieChart.Data("< 50.000", sallow50));
        salaryData.add(new PieChart.Data("< 80.000", sallow80));
        salaryData.add(new PieChart.Data("80.000 >", salmore80));

        salaryData.forEach(data ->
                data.nameProperty().bind(

                        Bindings.concat(
                                data.getName(), ": ", data.pieValueProperty()
                        )
                )
        );
        gender.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), ": ", data.pieValueProperty()
                        )
                )
        );


        salaryChart.setData(salaryData);
        genderChart.setData(gender);
        allWorkers.setText(String.valueOf(dbhandler.getWorkers()));
        allhrs.setText(String.valueOf(dbhandler.getHRManagers()));
        allsalaries.setText(dbhandler.getSumSalary()+" soms");
        allbonuses.setText(dbhandler.getSumBonus()+" soms");
        bigsalary.setText(dbhandler.getBiggestSalary()+" soms");
        bigbonus.setText(dbhandler.getBiggestBonus()+" soms");

        sr_st.setValue("Works");
        setTableWorks();
        sr_st.setItems(statusList);

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
            stage.setTitle("Log in");

            stage.show();
        });

        profile_but.setOnAction(actionEvent -> {
            profileBlock.setVisible(true);
            statsBlock.setVisible(false);
            tableBlock.setVisible(false);
        });
        stats_but.setOnAction(actionEvent -> {
            profileBlock.setVisible(false);
            statsBlock.setVisible(true);
            tableBlock.setVisible(false);
        });
        emp_table.setOnAction(actionEvent -> {
            profileBlock.setVisible(false);
            statsBlock.setVisible(false);
            tableBlock.setVisible(true);
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

        ChangeSalaryButtom.setOnAction(ActionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("changesalary.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Change Salary");

            stage.show();


        });

        searchButton.setOnAction(ActionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("search.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Search User");

            stage.show();
        });
        sr_st.setOnAction(actionEvent -> {
            if (sr_st.getValue().equals("Works")) {
                try {
                    DirTable.getItems().clear();
                    setTableWorks();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    DirTable.getItems().clear();
                    setTableNotWorks();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        refresh.setOnAction(event -> {
            if (sr_st.getValue().equals("Works")) {
                try {
                    DirTable.getItems().clear();
                    setTableWorks();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    DirTable.getItems().clear();
                    setTableNotWorks();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        name.setText(Controller.userName);
        position.setText(Controller.userPos);
        setForm(Controller.userId);

        period.setItems(sal);
        salary.setText(Controller.userSalary + " som");

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
            stage.setTitle("Edit Password");

            stage.show();
        });
    }

    public void setDirTable() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAllUser();

        while (true) {
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            usersList.add(new DirectorVariables(result.getInt("id"),
                    result.getString("name"),
                    result.getInt("salary"),
                    result.getString("role"),
                    result.getString("pos"),
                    result.getInt("bonus"),
                    result.getString("status")));
        }
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        RoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<>("pos"));
        BonusColumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        IDColumn.setSortType(TableColumn.SortType.DESCENDING);
        DirTable.setItems(usersList);
    }

    private void setTableWorks() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAllUser();

        while (true) {
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (result.getString("status").equals("Works")) {
                usersList.add(new DirectorVariables(result.getInt("id"),
                        result.getString("name"),
                        result.getInt("salary"),
                        result.getString("role"),
                        result.getString("pos"),
                        result.getInt("bonus"),
                        result.getString("status")));
            }
        }
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        RoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<>("pos"));
        BonusColumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        IDColumn.setSortType(TableColumn.SortType.DESCENDING);
        DirTable.setItems(usersList);
    }


    @FXML
    private void setTableNotWorks() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAllUser();

        while (true) {
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!result.getString("status").equals("Works")) {
                usersList.add(new DirectorVariables(result.getInt("id"),
                        result.getString("name"),
                        result.getInt("salary"),
                        result.getString("role"),
                        result.getString("pos"),
                        result.getInt("bonus"),
                        result.getString("status")));
            }
        }
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        RoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<>("pos"));
        BonusColumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        IDColumn.setSortType(TableColumn.SortType.DESCENDING);
        DirTable.setItems(usersList);
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


