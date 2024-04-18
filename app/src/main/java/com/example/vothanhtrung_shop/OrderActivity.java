package com.example.vothanhtrung_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderActivity extends AppCompatActivity {
    String receivedData;

    static boolean addSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        LottieAnimationView animationView = findViewById(R.id.animation);
        Intent intent = getIntent();
// Nhận dữ liệu từ Intent
        if (intent != null) {
            receivedData = intent.getStringExtra("ListItemCart");
        }
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
                if (addSuccess) {
                    startActivity(new Intent(OrderActivity.this, OrderSuccessActivity.class));
                    finish();
                }
            }
        });
        addOrder();
    }

    void addOrder() {
        ApiCaller apiCaller = ApiCaller.getInstance(getBaseContext());
        Order order = new Order(User.getId());
        apiCaller.addOrder(order, new
                ApiCaller.ApiResponseListener<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            Order.setId(response.getInt("id"));
                            JSONArray jsonArray = new JSONArray(receivedData);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                int quantity =
                                        jsonArray.getJSONObject(i).getInt("quantity");
                                JSONObject jsonProductObject = new
                                        JSONObject(jsonArray.getJSONObject(i).getString("product"));
                                int productid = jsonProductObject.getInt("id");
                                OrderDetail orderDetail = new
                                        OrderDetail(quantity, Order.getId(), productid);
                                apiCaller.addOrderDetail(orderDetail, new ApiCaller.ApiResponseListener<JSONObject>() {
                                    @Override

                                    public void onSuccess(JSONObject response) {
                                    }

                                    @Override

                                    public void onError(String errorMessage) {

                                    }
                                });
                            }

                            apiCaller.deleteCart(Cart.getId(), new

                                    ApiCaller.ApiResponseListener<String>() {
                                        @Override

                                        public void onSuccess(String response) {

                                            Cart.setId(0);
                                            addSuccess = true;
                                        }

                                        @Override

                                        public void onError(String errorMessage) {

                                        }
                                    });
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                    }
                });
    }
}