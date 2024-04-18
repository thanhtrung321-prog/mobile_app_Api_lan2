package com.example.vothanhtrung_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailNoAdmin extends AppCompatActivity {

    private JSONObject itemProductData;
    private ApiCaller apiCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_no_admin);


        apiCaller = ApiCaller.getInstance(this);
        Button Btnaddcart = findViewById(R.id.detail_button);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Extract the product details from the intent
        if (intent != null) {
            int productId = intent.getIntExtra("productId", 1);
            Log.d("ProductId", String.valueOf(productId));
            String productName = intent.getStringExtra("productName");
            String productImageURL = intent.getStringExtra("productImageURL");
            String productDescription = intent.getStringExtra("productDescription");
            double productPrice = intent.getDoubleExtra("pricedetail", 0);

            // Find views by their IDs
            TextView productNameTextView = findViewById(R.id.detailfoodname);
            ImageView productImageView = findViewById(R.id.imagedetail);
            TextView productDescriptionTextView = findViewById(R.id.detaildescription);
            TextView priceTextView = findViewById(R.id.detailPrice);


            // Set the product details to the corresponding views
            productNameTextView.setText(productName);
            productDescriptionTextView.setText(productDescription);
            priceTextView.setText(String.valueOf(productPrice)); // Convert double to String

            // Load product image using Picasso library
            if (productImageURL != null && !productImageURL.isEmpty()) {
                Picasso.get().load(ApiCaller.url + "/image/products/" + productImageURL).into(productImageView);
            }

            // Store product details for adding to cart
            try {
                itemProductData = new JSONObject();
                itemProductData.put("id", productId);
                itemProductData.put("quantity", 1); // Default quantity is 1
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Set up the click listener for the "Home" button
        ImageView buttonHome = findViewById(R.id.button_left_product);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                finish();
            }
        });

        Btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                // Đặt delay 1 giây để xử lý việc thêm vào giỏ hàng và chuyển sang Fragment giỏ hàng
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Kiểm tra xem đã có giỏ hàng hay chưa
                        if (Cart.getId() != 0) {
                            // Nếu đã có giỏ hàng, kiểm tra sản phẩm có trong giỏ hàng hay không
                            try {
                                boolean isProductInCart = Cart.getInstance(User.getId()).getProductQuantities().containsKey(itemProductData.getInt("id"));
                                if (isProductInCart) {
                                    // Nếu sản phẩm đã tồn tại trong giỏ hàng, chỉ tăng số lượng
                                    int currentQuantity = Cart.getInstance(User.getId()).getProductQuantities().get(itemProductData.getInt("id"));
                                    Cart.getInstance(User.getId()).updateItemQuantity(itemProductData.getInt("id"), currentQuantity + 1);
                                } else {
                                    // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới vào giỏ hàng
                                    apiCaller.addCartDetail(Cart.getId(), itemProductData.getInt("id"), 1, new ApiCaller.ApiResponseListener<JSONObject>() {
                                        @Override
                                        public void onSuccess(JSONObject response) {
                                            try {
                                                // Hiển thị thông báo
                                                Toast.makeText(getApplicationContext(), "Sản phẩm đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                throw new RuntimeException(e);
                                            }
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            // Xử lý lỗi nếu có
                                            Log.e("AddToCartError", errorMessage);
                                            Toast.makeText(getApplicationContext(), "Có lỗi xảy ra khi thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                            }
                        } else {
                            // Nếu chưa có giỏ hàng, tạo mới giỏ hàng và thêm sản phẩm đầu tiên vào
                            apiCaller.addCart(User.getId(), new ApiCaller.ApiResponseListener<JSONObject>() {
                                @Override
                                public void onSuccess(JSONObject response) {
                                    try {
                                        // Lưu id của giỏ hàng mới được tạo
                                        int newCartId = response.getInt("id");
                                        // Thiết lập id của giỏ hàng mới
                                        Cart.setId(newCartId);
                                        // Thêm sản phẩm vào giỏ hàng
                                        apiCaller.addCartDetail(newCartId, itemProductData.getInt("id"), 1, new ApiCaller.ApiResponseListener<JSONObject>() {
                                            @Override
                                            public void onSuccess(JSONObject response) {
                                                // Hiển thị thông báo
                                                Toast.makeText(getApplicationContext(), "Sản phẩm đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onError(String errorMessage) {
                                                // Xử lý lỗi nếu có
                                                Log.e("AddToCartError", errorMessage);
                                                Toast.makeText(getApplicationContext(), "Có lỗi xảy ra khi thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        throw new RuntimeException(e);
                                    }
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    // Xử lý lỗi nếu có
                                    Log.e("AddCartError", errorMessage);
                                    Toast.makeText(getApplicationContext(), "Có lỗi xảy ra khi tạo giỏ hàng!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        finish();
                    }
                }, 1000);
            }
        });
    }
}
