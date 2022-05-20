package com.courseproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchController {
    @FXML
    private TableColumn<DirectorVariables, Integer> SearchBonusColumn;

    @FXML
    private TextField SearchField;

    @FXML
    private TableColumn<DirectorVariables, Integer> SearchIDColumn;

    @FXML
    private TableColumn<DirectorVariables, String> SearchNameColumn;

    @FXML
    private TableColumn<DirectorVariables, String> SearchStatusColumn;

    @FXML
    private TableColumn<DirectorVariables, String> SearchPositionColumn;

    @FXML
    private TableColumn<DirectorVariables, String> SearchRoleColumn;

    @FXML
    private TableColumn<DirectorVariables, Integer> SearchSalaryColumn;

    @FXML
    private TableView<User> SearchTable;

    ObservableList<User> usersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        setDirTable();

        FilteredList<User> Filtered = new FilteredList<>(usersList, b -> true);
        SearchField.textProperty().addListener((observable, oldValue,newValue)->{
            Filtered.setPredicate(user -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(String.valueOf(user.getId()).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(String.valueOf(user.getSalary()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if(String.valueOf(user.getBonus()).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(user.getName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(user.getPos().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(user.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(user.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false;

            });
        });
        SortedList<User> sortedList = new SortedList<>(Filtered);
        sortedList.comparatorProperty().bind(SearchTable.comparatorProperty());
        SearchTable.setItems(sortedList);
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
        SearchIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        SearchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SearchSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        SearchRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        SearchPositionColumn.setCellValueFactory(new PropertyValueFactory<>("pos"));
        SearchBonusColumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        SearchStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        SearchIDColumn.setSortType(TableColumn.SortType.DESCENDING);
        SearchTable.setItems(usersList);
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
        SearchIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        SearchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SearchSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        SearchRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        SearchPositionColumn.setCellValueFactory(new PropertyValueFactory<>("pos"));
        SearchBonusColumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        SearchStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        SearchIDColumn.setSortType(TableColumn.SortType.DESCENDING);
        SearchTable.setItems(usersList);
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
        SearchIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        SearchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SearchSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        SearchRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        SearchPositionColumn.setCellValueFactory(new PropertyValueFactory<>("pos"));
        SearchBonusColumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        SearchStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        SearchIDColumn.setSortType(TableColumn.SortType.DESCENDING);
        SearchTable.setItems(usersList);
    }
}
