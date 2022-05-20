package com.courseproject;

public class User {
    private int id;
    private String name;
    private String login;
    private String password;
    private int salary;
    private String role;
    private String pos;
    private int bonus;
    private String gender;
    private String status;

    public User(int id, String name, String login, String password, int salary, String role, String pos, int bonus, String gender, String status) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.salary = salary;
        this.role = role;
        this.pos = pos;
        this.bonus = bonus;
        this.gender = gender;
        this.status = status;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
