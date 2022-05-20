package com.courseproject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnect()
            throws ClassNotFoundException, SQLException {

        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(url, dbUser, dbPass);

        return dbConnection;
    }

    public Integer getLastUserId() {
        int lastid = 0;
        try {
            Statement st = getDbConnect().createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(" + Const.USERS_ID + ") AS max_id FROM " + Const.USER_TABLE);
            if (rs.next()) {
                lastid = rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lastid;
    }

    public String getEntryById(int id) {
        ResultSet rerSet = null;
        String entry = "";
        String select = "SELECT * FROM " + Const.TIMES_TABLE + " WHERE " +
                Const.TIME_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            prSt.setInt(1, id);

            rerSet = prSt.executeQuery();
            while (true) {
                try {
                    if (!rerSet.next()) break;
                    entry = rerSet.getString("entry");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return entry;
    }

    public void editSalUser(int id, int salary, int bonus) {

        String insert = "UPDATE " + Const.USER_TABLE +
                " SET "
                + Const.USERS_SALARY + " = ?" + ", "
                + Const.USERS_BONUS + " = ?"
                + " WHERE " + Const.USERS_ID + " = ?;";

        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(insert);
            prSt.setInt(1, salary);
            prSt.setInt(2, bonus);
            prSt.setInt(3, id);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void editDbUser(int id, String name, String login, String password, String role, int salary, String pos,
                           String moSt, String moFn, String tuSt, String tuFn,
                           String weSt, String weFn, String thSt, String thFn,
                           String frSt, String frFn) {

        String insert = "UPDATE " + Const.USER_TABLE +
                " SET "
                + Const.USERS_LOGIN + " = ?" + ", "
                + Const.USERS_NAME + " = ?" + ", "
                + Const.USERS_PASS + " = ?" + ", "
                + Const.USERS_ROLE + " = ?" + ", "
                + Const.USERS_SALARY + " = ?" + ", "
                + Const.USERS_POS + " = ?"
                + " WHERE " + Const.USERS_ID + " = ?;";

        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, name);
            prSt.setString(3, password);
            prSt.setString(4, role);
            prSt.setInt(5, salary);
            prSt.setString(6, pos);
            prSt.setInt(7, id);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int counter = 0;
        ResultSet resulttime = getTimeById(id);
        while (true) {
            try {
                if (!resulttime.next()) break;
                counter++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (counter <= 0) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

            String insertTime = "INSERT INTO " + Const.TIMES_TABLE + "(" + Const.TIME_ID
                    + ", " + Const.TIME_ENTRY + ", " + Const.TIME_MOST + ", " +
                    Const.TIME_MOFN + ", " + Const.TIME_TUST + ", " + Const.TIME_TUFN + ", " + Const.TIME_WEST
                    + ", " + Const.TIME_WEFN + ", " + Const.TIME_THST + ", " + Const.TIME_THFN + ", " + Const.TIME_FRST + ", " + Const.TIME_FRFN + ")" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            try {

                PreparedStatement prSt = getDbConnect().prepareStatement(insertTime);
                prSt.setInt(1, id);
                prSt.setString(2, formatter.format(date).toString());
                prSt.setString(3, moSt);
                prSt.setString(4, moFn);
                prSt.setString(5, tuSt);
                prSt.setString(6, tuFn);
                prSt.setString(7, weSt);
                prSt.setString(8, weFn);
                prSt.setString(9, thSt);
                prSt.setString(10, thFn);
                prSt.setString(11, frSt);
                prSt.setString(12, frFn);

                prSt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        String insertTime = "UPDATE " + Const.TIMES_TABLE +
                " SET "
                + Const.TIME_MOST + " = ? " + ", "
                + Const.TIME_MOFN + " = ? " + ", "
                + Const.TIME_TUST + " = ? " + ", "
                + Const.TIME_TUFN + " = ? " + ", "
                + Const.TIME_WEST + " = ? " + ", "
                + Const.TIME_WEFN + " = ? " + ", "
                + Const.TIME_THST + " = ? " + ", "
                + Const.TIME_THFN + " = ? " + ", "
                + Const.TIME_FRST + " = ? " + ", "
                + Const.TIME_FRFN + " = ? " +
                " WHERE " + Const.TIME_ID + " = ?;";

        try {

            PreparedStatement prSt = getDbConnect().prepareStatement(insertTime);
            prSt.setString(1, moSt);
            prSt.setString(2, moFn);
            prSt.setString(3, tuSt);
            prSt.setString(4, tuFn);
            prSt.setString(5, weSt);
            prSt.setString(6, weFn);
            prSt.setString(7, thSt);
            prSt.setString(8, thFn);
            prSt.setString(9, frSt);
            prSt.setString(10, frFn);
            prSt.setInt(11, id);

            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void signUpUser(String name, String login, String password, String role, int salary, String pos, String gender,
                           String moSt, String moFn, String tuSt, String tuFn,
                           String weSt, String weFn, String thSt, String thFn,
                           String frSt, String frFn) {

        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_LOGIN
                + ", " + Const.USERS_NAME + ", " + Const.USERS_PASS + ", " +
                Const.USERS_ROLE + ", " + Const.USERS_SALARY + ", " + Const.USERS_POS + ", " + Const.USERS_BONUS + ", " + Const.USERS_GENDER + ")" +
                "VALUES(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, name);
            prSt.setString(3, password);
            prSt.setString(4, role);
            prSt.setInt(5, salary);
            prSt.setString(6, pos);
            prSt.setInt(7, 0);
            prSt.setString(8, gender);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int lastiduser = getLastUserId();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        String insertTime = "INSERT INTO " + Const.TIMES_TABLE + "(" + Const.TIME_ID
                + ", " + Const.TIME_ENTRY + ", " + Const.TIME_MOST + ", " +
                Const.TIME_MOFN + ", " + Const.TIME_TUST + ", " + Const.TIME_TUFN + ", " + Const.TIME_WEST
                + ", " + Const.TIME_WEFN + ", " + Const.TIME_THST + ", " + Const.TIME_THFN + ", " + Const.TIME_FRST + ", " + Const.TIME_FRFN + ")" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement prSt = getDbConnect().prepareStatement(insertTime);
            prSt.setInt(1, lastiduser);
            prSt.setString(2, formatter.format(date).toString());
            prSt.setString(3, moSt);
            prSt.setString(4, moFn);
            prSt.setString(5, tuSt);
            prSt.setString(6, tuFn);
            prSt.setString(7, weSt);
            prSt.setString(8, weFn);
            prSt.setString(9, thSt);
            prSt.setString(10, thFn);
            prSt.setString(11, frSt);
            prSt.setString(12, frFn);

            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Integer getUserId(User user) {
        ResultSet rerSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + "=? AND " + Const.USERS_PASS + "=?";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());

            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int id = 0;
        try {
            while (rerSet.next()) {
                id = rerSet.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public ResultSet getAllUser() {
        ResultSet res = null;

        String select = "SELECT * FROM " + Const.USER_TABLE;

        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            res = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }

    public ResultSet getUserById(int id) {
        ResultSet rerSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            prSt.setInt(1, id);

            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return rerSet;
    }

    public ResultSet getTimeById(int id) {
        ResultSet rerSet = null;

        String select = "SELECT * FROM " + Const.TIMES_TABLE + " WHERE " +
                Const.TIME_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            prSt.setInt(1, id);

            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return rerSet;
    }

    public void removeUser(int id) {
        String select = " UPDATE " + Const.USER_TABLE + " SET "
                + Const.USERS_STATUS + "=?"
                + " WHERE " + Const.USERS_ID + "=?;";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            prSt.setString(1, "Fired");
            prSt.setInt(2, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user) {
        ResultSet rerSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + "=? AND " + Const.USERS_PASS + "=?";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());

            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return rerSet;
    }

    public void editWorpass(String login, String password) {
        String insert = " UPDATE " + Const.USER_TABLE + " SET " + Const.USERS_PASS + "=?" + " WHERE " + Const.USERS_LOGIN + "=?;";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(insert);

            prSt.setString(1, password);
            prSt.setString(2, login);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Boolean checkpass(String pass, int id) {
        ResultSet r = getUserById(id);
        String curpass = "";
        while (true) {
            try {
                if (!r.next()) break;
                curpass = r.getString("password");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pass.equals(curpass)) {
            return true;
        }
        return false;
    }

    // Functions for statistics

    public Integer getWorks() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT count(*) FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_STATUS + "= 'Works'";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
    public Integer getNotWorks() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT count(*) FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_STATUS + "= 'Fired'";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
    public ResultSet getSalary() {
        ResultSet rerSet = null;

        String select = "SELECT salary FROM " + Const.USER_TABLE;
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);

            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return rerSet;
    }
    public Integer getFemale() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT count(*) FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_GENDER + "= 'Female'";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
    public Integer getMale() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT count(*) FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_GENDER + "= 'Male'";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
    public Integer getOtherGender() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT count(*) FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_GENDER + "= 'Other'";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
    public Integer getWorkers() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT count(*) FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_ROLE + "= 'worker'";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
    public Integer getHRManagers() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT count(*) FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_ROLE + "= 'hrmanager'";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
    public Integer getDirectors() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT count(*) FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_ROLE + "= 'director'";
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    public Integer getSumSalary() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT sum(salary) FROM " + Const.USER_TABLE;
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
    public Integer getSumBonus() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT sum(bonus) FROM " + Const.USER_TABLE;
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    public Integer getBiggestSalary() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT MAX(salary) FROM " + Const.USER_TABLE;
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            System.out.println(rerSet.getInt(1));
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    public Integer getBiggestBonus() {
        ResultSet rerSet = null;
        int c = 0;
        String select = "SELECT MAX(bonus) FROM " + Const.USER_TABLE;
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            rerSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rerSet.next();
            c = rerSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }


}