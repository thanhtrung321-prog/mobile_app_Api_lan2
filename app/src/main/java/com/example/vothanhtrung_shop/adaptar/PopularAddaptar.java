package com.example.vothanhtrung_shop.adaptar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vothanhtrung_shop.ApiCaller;
import com.example.vothanhtrung_shop.DetailNoAdmin;
import com.example.vothanhtrung_shop.Product;
import com.example.vothanhtrung_shop.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PopularAddaptar extends RecyclerView.Adapter<PopularAddaptar.PopularViewHolder> {

    private final Context context;
    private final List<Product> productList;

    public PopularAddaptar(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.populer_item, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView productNameTextView;
        private final ImageView productImageView;
        private  TextView priceproduct;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.foodNamePopuler);
            productImageView = itemView.findViewById(R.id.imageView5);
            priceproduct=itemView.findViewById(R.id.PricePopuler);
            itemView.setOnClickListener(this);
        }

        public void bind(Product product) {
            productNameTextView.setText(product.getTitle());
            priceproduct.setText(String.valueOf(product.getPrice())); // Hiển thị giá sản phẩm
            Picasso.get()
                    .load(ApiCaller.url + "/image/products/" + product.getPhoto())
                    .into(productImageView);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Product product = productList.get(position);
                // Chuyển sang DetailNoAdmin khi nhấn vào sản phẩm
                Intent intent = new Intent(context, DetailNoAdmin.class);
                intent.putExtra("productId",product.getId());
                intent.putExtra("productName", product.getTitle());
                intent.putExtra("productImageURL", product.getPhoto());
                intent.putExtra("productDescription", product.getDescription());
                intent.putExtra("pricedetail",product.getPrice());
                context.startActivity(intent);
            }
        }
    }
}
