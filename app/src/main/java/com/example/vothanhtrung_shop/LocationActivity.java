package com.example.vothanhtrung_shop;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vothanhtrung_shop.databinding.ActivityLocationBinding;

public class LocationActivity extends AppCompatActivity {

    private ActivityLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] locationList = {"Việt Nam", "Hàn Quốc", "Mỹ", "Vương Quốc Anh"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationList);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) binding.listOfLocation;
        autoCompleteTextView.setAdapter(adapter);
    }
}
