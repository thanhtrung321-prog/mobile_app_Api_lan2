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
import com.example.vothanhtrung_shop.adaptar.BuyAgainAdapter;
import com.example.vothanhtrung_shop.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;

    private String[] buyAgainFoodName = {"Cháo yến Mạch", "Kem Trứng", "Hủ Tiếu","Salas","Món Ăn Thập Cẩm"};
    private String[] buyAgainFoodPrice = {"$10", "$20", "$30","$50","$80"};
    private int[] buyAgainFoodImage = {R.drawable.menu4, R.drawable.menu3, R.drawable.menu7,R.drawable.menu2,R.drawable.menu1};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup RecyclerView
        setupRecyclerView();

        // Log the arrays
        logArrays();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.BuyAgainRecyview.setLayoutManager(layoutManager);

        // Convert arrays to ArrayLists
        ArrayList<String> buyAgainFoodNameList = new ArrayList<>(Arrays.asList(buyAgainFoodName));
        ArrayList<String> buyAgainFoodPriceList = new ArrayList<>(Arrays.asList(buyAgainFoodPrice));
        ArrayList<Integer> buyAgainFoodImageList = new ArrayList<>();
        for (int image : buyAgainFoodImage) {
            buyAgainFoodImageList.add(image);
        }

        // Initialize adapter and set it to RecyclerView
        BuyAgainAdapter adapter = new BuyAgainAdapter(buyAgainFoodNameList, buyAgainFoodPriceList, buyAgainFoodImageList);
        binding.BuyAgainRecyview.setAdapter(adapter);
    }

    private void logArrays() {
        // Log the arrays
        StringBuilder sbFoodName = new StringBuilder("buyAgainFoodName: ");
        StringBuilder sbFoodPrice = new StringBuilder("buyAgainFoodPrice: ");
        StringBuilder sbFoodImage = new StringBuilder("buyAgainFoodImage: ");
        for (String foodName : buyAgainFoodName) {
            sbFoodName.append(foodName).append(", ");
        }
        for (String foodPrice : buyAgainFoodPrice) {
            sbFoodPrice.append(foodPrice).append(", ");
        }
        for (int image : buyAgainFoodImage) {
            sbFoodImage.append(image).append(", ");
        }
        // Remove the last comma and space
        sbFoodName.delete(sbFoodName.length() - 2, sbFoodName.length());
        sbFoodPrice.delete(sbFoodPrice.length() - 2, sbFoodPrice.length());
        sbFoodImage.delete(sbFoodImage.length() - 2, sbFoodImage.length());


    }
}
