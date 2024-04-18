package com.example.vothanhtrung_shop.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vothanhtrung_shop.ApiCaller;
import com.example.vothanhtrung_shop.Cart;
import com.example.vothanhtrung_shop.R;
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

        ApiCaller apiCaller = ApiCaller.getInstance(getContext());

        apiCaller.makeStringRequest(ApiCaller.url + "/cartDetails/cart/"+ Cart.getId(), new ApiCaller.ApiResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("aaaaa", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i< jsonArray.length();i++){

                        JSONObject jsonObjectProduct = new JSONObject(jsonArray.getJSONObject(i).getString("product"));
                        Log.d("aaaaa", jsonObjectProduct.getString("title"));
                        cartFoodName.add(jsonObjectProduct.getString("title"));
                        int price = jsonObjectProduct.getInt("price");
                        cartItemPrice.add(String.valueOf(price));
                        cartImage.add(jsonObjectProduct.getString("photo"));
                        CartAdapter adapter = new CartAdapter(cartFoodName, cartItemPrice, cartImage);
                        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                        binding.cartRecyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        Log.d("aaaaavvvv", cartFoodName.toString());

//        cartFoodName.add("Burger");
//        cartFoodName.add("Sandwich");
//        cartFoodName.add("Momo");
//        cartFoodName.add("Súp");
//        cartFoodName.add("Sandwich");
//        cartFoodName.add("Momo");



//        cartItemPrice.add("$6");
//        cartItemPrice.add("$8");
//        cartItemPrice.add("$9");
//        cartItemPrice.add("$10");
//        cartItemPrice.add("$10");

//        cartImage.add(R.drawable.menu2);
//        cartImage.add(R.drawable.menu3);
//        cartImage.add(R.drawable.menu4);
//        cartImage.add(R.drawable.menu5);
//        cartImage.add(R.drawable.menu6);

        return rootView;
    }
}