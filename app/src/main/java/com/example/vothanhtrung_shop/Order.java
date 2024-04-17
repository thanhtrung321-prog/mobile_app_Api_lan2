package com.example.vothanhtrung_shop;

public class Order {
    static private int id;
    private int iduser;
    private String date = "2027-04-10T16:55:18.873+00:00";

    public Order(int iduser) {
        this.iduser = iduser;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Order.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}