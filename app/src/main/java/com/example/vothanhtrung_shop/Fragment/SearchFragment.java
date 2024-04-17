package com.example.vothanhtrung_shop.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vothanhtrung_shop.R;
import com.example.vothanhtrung_shop.SearchMenu;
import com.example.vothanhtrung_shop.adaptar.PopularAddaptar;
import com.example.vothanhtrung_shop.adaptar.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.searchView);
        // Tạo danh sách sản phẩm cần hiển thị
        List<SearchMenu> searchMenuList = new ArrayList<>();
        searchMenuList.add(new SearchMenu("burger", "$5", R.drawable.menu1));
        searchMenuList.add(new SearchMenu("sandwich", "$7", R.drawable.menu2));
        searchMenuList.add(new SearchMenu("momo", "$8", R.drawable.menu3));
        searchMenuList.add(new SearchMenu("burger", "$10", R.drawable.menu4));
        searchMenuList.add(new SearchMenu("sandwich", "$9", R.drawable.menu6));
        searchMenuList.add(new SearchMenu("momo", "$11", R.drawable.menu5));
        searchMenuList.add(new SearchMenu("momo", "$21", R.drawable.menu7));


        // Tạo một instance của SearchAdapter và gán cho RecyclerView
        SearchAdapter searchAdapter = new SearchAdapter(getContext(), searchMenuList);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
