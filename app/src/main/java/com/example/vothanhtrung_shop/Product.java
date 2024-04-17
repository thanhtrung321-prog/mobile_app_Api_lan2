package com.example.vothanhtrung_shop;

public class Product {
    private int id;
    private String description;
    private String photo; // Đây là đường dẫn tới ảnh
    private double price;
    private String title;
    private int category_id;

    public Product() {
        this.id = id;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.title = title;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}