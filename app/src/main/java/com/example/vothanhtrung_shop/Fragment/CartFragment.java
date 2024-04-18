package com.example.vothanhtrung_shop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vothanhtrung_shop.ApiCaller;
import com.example.vothanhtrung_shop.Cart;
import com.example.vothanhtrung_shop.OrderActivity;
import com.example.vothanhtrung_shop.R;
import com.example.vothanhtrung_shop.User;
import com.example.vothanhtrung_shop.adaptar.CartAdapter;
import com.example.vothanhtrung_shop.databinding.FragmentCartBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    View viewContainer;
    JSONArray jsonArrayCart;
    List<String> cartFoodName = new ArrayList<>();
    List<String> cartItemPrice = new ArrayList<>();
    List<String> cartImage = new ArrayList<>();
    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        Button btnOrder = rootView.findViewById(R.id.button_pay); // Thay đổi từ viewContainer sang rootView
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("ListItemCart", jsonArrayCart.toString());
                startActivity(intent);
                getActivity().finish();
            }
        });

        ApiCaller apiCaller = ApiCaller.getInstance(getContext());
        apiCaller.makeStringRequest(ApiCaller.url + "/cartDetails/cart/"+ Cart.getId(), new ApiCaller.ApiResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("aaaaa", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    jsonArrayCart = jsonArray; // Lưu lại jsonArray để sử dụng sau này
                    for(int i = 0; i< jsonArray.length();i++){
                        JSONObject jsonObjectProduct = new JSONObject(jsonArray.getJSONObject(i).getString("product"));
                        Log.d("aaaaa", jsonObjectProduct.getString("title"));
                        cartFoodName.add(jsonObjectProduct.getString("title"));
                        int price = jsonObjectProduct.getInt("price");
                        cartItemPrice.add(String.valueOf(price));
                        cartImage.add(jsonObjectProduct.getString("photo"));
                    }
                    CartAdapter adapter = new CartAdapter(cartFoodName, cartItemPrice, cartImage);
                    binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.cartRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("ApiCaller", "Error fetching cart details: " + errorMessage);
            }
        });

        return rootView; // Trả về rootView thay vì viewContainer
    }
}