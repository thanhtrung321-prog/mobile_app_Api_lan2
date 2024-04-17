package com.example.vothanhtrung_shop.adaptar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vothanhtrung_shop.databinding.BuyAgainItemBinding;

import java.util.ArrayList;
import java.util.List;

public class BuyAgainAdapter extends RecyclerView.Adapter<BuyAgainAdapter.ViewHolder> {
    private List<String> buyAgainFoodName;
    private List<String> buyAgainFoodPrice;
    private List<Integer> buyAgainFoodImage;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public BuyAgainAdapter(ArrayList<String> buyAgainFoodName, ArrayList<String> buyAgainFoodPrice, ArrayList<Integer> buyAgainFoodImage) {
        this.buyAgainFoodName = buyAgainFoodName;
        this.buyAgainFoodPrice = buyAgainFoodPrice;
        this.buyAgainFoodImage = buyAgainFoodImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BuyAgainItemBinding binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        holder.binding.BuyAgainFoodName.setText(buyAgainFoodName.get(position));
        holder.binding.BuyAgainFoodPrice.setText(buyAgainFoodPrice.get(position));
        holder.binding.BuyAgainFoodImage.setImageResource(buyAgainFoodImage.get(position));

        // Set onClickListener for delete button
        holder.binding.BuyAgainFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteButtonClickListener != null) {
                    onDeleteButtonClickListener.onDeleteButtonClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return buyAgainFoodName.size();
    }

    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
        this.onDeleteButtonClickListener = listener;
    }

    public void removeItem(int position) {
        buyAgainFoodName.remove(position);
        buyAgainFoodPrice.remove(position);
        buyAgainFoodImage.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        BuyAgainItemBinding binding;

        public ViewHolder(BuyAgainItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
    }
}
