package com.example.vothanhtrung_shop;

public class OrderDetail {
    private int quantity;
    private int orderid;
    private int productid;

    public OrderDetail(int quantity, int orderid, int productid) {
        this.quantity = quantity;
        this.orderid = orderid;
        this.productid = productid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }
}