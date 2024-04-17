package com.example.vothanhtrung_shop;

public class Category {
    private int id;
    private String description;
    private String photo;
    private String title;

    public Category() {
        // Default constructor required for Firebase
    }

    public Category(int id, String description, String photo, String title) {
        this.id = id;
        this.description = description;
        this.photo = photo;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
