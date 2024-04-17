    package com.example.vothanhtrung_shop.adaptar;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.vothanhtrung_shop.MyViewHolder;
    import com.example.vothanhtrung_shop.R;
    import com.example.vothanhtrung_shop.SearchMenu;

    import java.util.List;

    public class SearchAdapter extends RecyclerView.Adapter<MyViewHolder> {
         Context context;
         List<SearchMenu> searchMenus;

        public SearchAdapter(Context context, List<SearchMenu> searchMenus) {
            this.context = context;
            this.searchMenus = searchMenus;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.menu_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           holder.menufoodNamePopuler.setText(searchMenus.get(position).getFoodName());
           holder.MenupricePopuler.setText(searchMenus.get(position).getSearchPrice());
           holder.menuImage.setImageResource(searchMenus.get(position).getImageSearch());
        }

        @Override
        public int getItemCount() {
            return searchMenus.size();
        }
    }
