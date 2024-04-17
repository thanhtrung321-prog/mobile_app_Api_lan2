package com.example.vothanhtrung_shop;

import android.net.Uri;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailUploadproduct {

    private DatabaseReference productsRef;

    public DetailUploadproduct() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        productsRef = database.getReference("products");
    }

    public void uploadProduct(String productName, double productPrice, Uri productImageUri, String productDescription, String productIngredients) {
        // Tạo một đối tượng ProductAdminDetail từ thông tin nhập vào
        ProductAdminDetail product = new ProductAdminDetail(productName, productPrice, productImageUri.toString(), productDescription, productIngredients);

        // Thêm sản phẩm vào Firebase Realtime Database
        String productId = productsRef.push().getKey();
        productsRef.child(productId).setValue(product);
    }

    public void uploadProductFromEditTexts(EditText namefoodtext, EditText pricetext, EditText textdescription, EditText listcomponent) {
        // Lấy dữ liệu từ các EditText
        String productName = namefoodtext.getText().toString();
        double productPrice = Double.parseDouble(pricetext.getText().toString());
        String productDescription = textdescription.getText().toString();
        String productIngredients = listcomponent.getText().toString();

        // Gọi phương thức uploadProduct để thêm sản phẩm vào Firebase
        uploadProduct(productName, productPrice, null, productDescription, productIngredients);
    }
}
