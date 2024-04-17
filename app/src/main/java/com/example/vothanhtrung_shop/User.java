package com.example.vothanhtrung_shop;

public class User {
    static private int id;
    static private String namewelcome = "admin";
    private String username;
    private String password;
    private String email;

    private String numphone;

    private String photo;

    public User(String username, String pass, String email, String numphone, String photo) {
        this.username = username;
        this.password = pass;
        this.email = email;
        this.numphone = numphone;
        this.photo = photo;
    }

    public static String getNamewelcome() {

        return namewelcome;
    }

    public static void setNamewelcome(String namewelcome) {
        User.namewelcome = namewelcome;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumphone() {
        return numphone;
    }

    public void setNumphone(String numphone) {
        this.numphone = numphone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
