package com.example.vothanhtrung_shop;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public ImageView menuImage;
    public TextView menufoodNamePopuler;
    public TextView MenupricePopuler;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        menuImage = itemView.findViewById(R.id.menuImage);
        menufoodNamePopuler = itemView.findViewById(R.id.menufoodNamePopuler);
        MenupricePopuler = itemView.findViewById(R.id.MenupricePopuler);
    }
}
