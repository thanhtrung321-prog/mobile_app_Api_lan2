package com.example.vothanhtrung_shop.adaptar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vothanhtrung_shop.ApiCaller;
import com.example.vothanhtrung_shop.R;
import com.example.vothanhtrung_shop.databinding.CartItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final List<String> cartItems;       // Danh sách các mặt hàng trong giỏ hàng
    private final List<String> cartItemPrice;    // Danh sách giá của các mặt hàng
    private final List<String> cartImage;       // Danh sách ảnh đại diện cho mỗi mặt hàng
    private final int[] itemQuantities;          // Mảng lưu số lượng của mỗi mặt hàng trong giỏ hàng

    // Constructor để khởi tạo Adapter với dữ liệu
    public CartAdapter(List<String> cartItems, List<String> cartItemPrice, List<String> cartImage) {
        this.cartItems = cartItems;
        this.cartItemPrice = cartItemPrice;
        this.cartImage = cartImage;
        this.itemQuantities = new int[cartItems.size()]; // Khởi tạo mảng số lượng với số phần tử bằng số mặt hàng
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo ra một ViewHolder mới từ layout của mỗi item trong RecyclerView
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CartItemBinding binding = CartItemBinding.inflate(inflater, parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        // Gán dữ liệu vào ViewHolder tại vị trí position
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng item trong danh sách giỏ hàng
        return cartItems.size();
    }

    // Lớp ViewHolder để giữ thông tin của mỗi item trong RecyclerView
    public class CartViewHolder extends RecyclerView.ViewHolder {
        private final CartItemBinding binding;

        public CartViewHolder(CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // Thiết lập sự kiện cho nút "minusButton" để giảm số lượng mặt hàng
            binding.minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    // Kiểm tra xem vị trí có hợp lệ và số lượng mặt hàng lớn hơn 0
                    if (position != RecyclerView.NO_POSITION && itemQuantities[position] > 0) {
                        // Giảm số lượng mặt hàng và cập nhật TextView hiển thị số lượng
                        itemQuantities[position]--;
                        binding.quantity.setText(String.valueOf(itemQuantities[position]));
                    }
                }
            });

            // Thiết lập sự kiện cho nút "plusButton" để tăng số lượng mặt hàng
            binding.plusebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    // Kiểm tra xem vị trí có hợp lệ
                    if (position != RecyclerView.NO_POSITION) {
                        // Tăng số lượng mặt hàng và cập nhật TextView hiển thị số lượng
                        itemQuantities[position]++;
                        binding.quantity.setText(String.valueOf(itemQuantities[position]));
                    }
                }
            });
            binding.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    // Kiểm tra xem vị trí có hợp lệ
                    if (position != RecyclerView.NO_POSITION) {
                        // Xóa mặt hàng tại vị trí position
                        cartItems.remove(position);
                        cartItemPrice.remove(position);
                        cartImage.remove(position);
                        // Cập nhật số lượng mặt hàng
                        notifyItemRemoved(position);
                    }
                }
            });
        }

        // Phương thức này gán dữ liệu vào các thành phần giao diện của ViewHolder
        public void bind(int position) {
            // Lấy số lượng của mặt hàng tại vị trí position
            int quantity = itemQuantities[position];
            // Gán thông tin mặt hàng vào các TextView tương ứng
            binding.cartFoodName.setText(cartItems.get(position));
            binding.cartiItemPrice.setText(cartItemPrice.get(position));

            Picasso.get()
                    .load(ApiCaller.url+"/image/products/"+cartImage.get(position)
                    )
//                    .placeholder(R.drawable.load)
//                    .error(R.drawable.error_200)
                    .into(binding.cartImage);

            binding.quantity.setText(String.valueOf(quantity)); // Hiển thị số lượng
        }
    }
}
