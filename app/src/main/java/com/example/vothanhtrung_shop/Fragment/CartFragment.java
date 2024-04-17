package com.example.vothanhtrung_shop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vothanhtrung_shop.R;
import com.example.vothanhtrung_shop.adaptar.CartAdapter;
import com.example.vothanhtrung_shop.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;

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

        List<String> cartFoodName = new ArrayList<>();
        cartFoodName.add("Burger");
        cartFoodName.add("Sandwich");
        cartFoodName.add("Momo");
        cartFoodName.add("Súp");
        cartFoodName.add("Sandwich");
        cartFoodName.add("Momo");

        List<String> cartItemPrice = new ArrayList<>();
        cartItemPrice.add("$5");
        cartItemPrice.add("$6");
        cartItemPrice.add("$8");
        cartItemPrice.add("$9");
        cartItemPrice.add("$10");
        cartItemPrice.add("$10");

        List<Integer> cartImage = new ArrayList<>();
        cartImage.add(R.drawable.menu1  );
        cartImage.add(R.drawable.menu2);
        cartImage.add(R.drawable.menu3);
        cartImage.add(R.drawable.menu4);
        cartImage.add(R.drawable.menu5);
        cartImage.add(R.drawable.menu6);

        CartAdapter adapter = new CartAdapter(cartFoodName, cartItemPrice, cartImage);
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.cartRecyclerView.setAdapter(adapter);

        return rootView;
    }
}
