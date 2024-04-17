package com.example.vothanhtrung_shop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.vothanhtrung_shop.ApiCaller;
import com.example.vothanhtrung_shop.Product;
import com.example.vothanhtrung_shop.R;

import com.example.vothanhtrung_shop.adaptar.PopularAddaptar;
import com.example.vothanhtrung_shop.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ApiCaller apiCaller;
    private JSONObject userObject;
    private String userName;

    private PopularAddaptar popularAdapter;
    private List<Product> productList = new ArrayList<>();

    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize apiCaller
        apiCaller = ApiCaller.getInstance(getContext());

        // Load slideShows and set up ImageSlider
        apiCaller.makeStringRequest(apiCaller.url + "/slideShows", new ApiCaller.ApiResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                JSONArray jsonArrayData = apiCaller.stringToJsonArray(response);
                if (jsonArrayData != null) {
                    ArrayList<SlideModel> imageList = new ArrayList<>();
                    for (int i = 0; i < jsonArrayData.length(); i++) {
                        try {
                            JSONObject slideJson = jsonArrayData.getJSONObject(i);
                            String imageUrl = apiCaller.url + "/image/slideShows/" + slideJson.getString("photo");
                            imageList.add(new SlideModel(imageUrl, ScaleTypes.FIT));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ImageSlider imageSlider = binding.imageSlider;
                    imageSlider.setImageList(imageList, ScaleTypes.FIT);
                    imageSlider.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemSelected(int i) {
                            String itemMessage = "Hình Ảnh Hiện Tại " + i;
                            Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void doubleClick(int i) {
                        }
                    });
                } else {
                    Log.e("Error", "Invalid JSON string");
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("Error", errorMessage);
                // Handle error
            }
        });

        // Initialize RecyclerView for popular products
        RecyclerView popularRecyclerView = binding.PopulerRecyclerView;
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Context context = requireContext();
        popularAdapter = new PopularAddaptar(context, productList);
        popularRecyclerView.setAdapter(popularAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String userString = bundle.getString("userObject");
            try {
                userObject = new JSONObject(userString);
                userName = userObject.getString("username");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Load products
        apiCaller.makeStringRequest(apiCaller.url + "/products", new ApiCaller.ApiResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                JSONArray jsonArrayData = apiCaller.stringToJsonArray(response);
                if (jsonArrayData != null) {
                    // Clear the productList before adding new products
                    productList.clear();
                    try {
                        for (int i = 0; i < jsonArrayData.length(); i++) {
                            JSONObject productJson = jsonArrayData.getJSONObject(i);
                            Product product;
                            product = new Product();
                            product.setId(productJson.getInt("id"));
                            product.setTitle(productJson.getString("title"));
                            product.setPrice(productJson.getDouble("price"));
                            product.setPhoto(productJson.getString("photo"));
                            product.setDescription(productJson.getString("description"));
                            product.setPrice(productJson.getDouble("price"));
                            productList.add(product);
                        }
                        popularAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Error", "Invalid JSON string");
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("Error", errorMessage);
                // Handle error
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
