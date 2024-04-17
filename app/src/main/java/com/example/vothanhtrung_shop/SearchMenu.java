package com.example.vothanhtrung_shop;

public class SearchMenu {
    private String foodName;
    private int imageSearch;
    private String searchPrice;

    public SearchMenu(String foodName, String searchPrice, int imageSearch) {
        this.foodName = foodName;
        this.searchPrice = searchPrice;
        this.imageSearch = imageSearch;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getImageSearch() {
        return imageSearch;
    }

    public void setImageSearch(int imageSearch) {
        this.imageSearch = imageSearch;
    }

    public String getSearchPrice() {
        return searchPrice;
    }

    public void setSearchPrice(String searchPrice) {
        this.searchPrice = searchPrice;
    }
}

