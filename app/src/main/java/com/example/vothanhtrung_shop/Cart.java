package com.example.vothanhtrung_shop;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private static int id = 0;
    private int userId;
    private static Cart instance;
    private Map<Integer, Integer> productQuantities; // Lưu trữ số lượng sản phẩm với key là productId

    private Cart(int userId) {
        this.userId = userId;
        this.productQuantities = new HashMap<>();
    }

    public static Cart getInstance(int userId) {
        if (instance == null) {
            instance = new Cart(userId);
        }
        return instance;
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

    public Map<Integer, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Integer, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addItem(int productId, int quantity) {
        productQuantities.put(productId, productQuantities.getOrDefault(productId, 0) + quantity);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeItem(int productId) {
        productQuantities.remove(productId);
    }

    // Cập nhật số lượng của sản phẩm trong giỏ hàng
    public void updateItemQuantity(int productId, int quantity) {
        productQuantities.put(productId, quantity);
    }
}
