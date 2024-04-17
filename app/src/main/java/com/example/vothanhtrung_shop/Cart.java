package com.example.vothanhtrung_shop;

public class Cart {
    static private int id = 0;
    private int userId;

    public Cart(int userId) {
        this.userId = userId;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Cart.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}