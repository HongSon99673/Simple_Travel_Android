package com.example.simpletravel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.SavedItem;

import java.util.List;

public class SavedItemAdapter extends RecyclerView.Adapter<SavedItemAdapter.ListSavedItem> {
    private List<SavedItem> savedItemList;

    public SavedItemAdapter(List<SavedItem> savedItemList) {
        this.savedItemList = savedItemList;
    }

    @NonNull
    @Override
    public ListSavedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saveitem_fragment_planning,
                parent, false);

        return new ListSavedItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSavedItem holder, int position) {
        SavedItem savedItem = savedItemList.get(position);
        if(savedItem == null){
            return;
        }

        holder.NameType.setText(savedItem.getNameType());
        holder.NameService.setText(savedItem.getNameService());
//        holder.Rating.setText(String.valueOf(savedItem.getRating()));
        if(savedItem.getRating() == 1){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if(savedItem.getRating()  == 2){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if(savedItem.getRating()  == 3){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if(savedItem.getRating()  == 4){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if(savedItem.getRating()  == 5){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star5.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        holder.Summary.setText(savedItem.getSummary());
        holder.TimOpen.setText(savedItem.getTimeOpen());
        holder.NamePlane.setText(savedItem.getNamePlan());
    }

    @Override
    public int getItemCount() {
        if (savedItemList != null){
            return savedItemList.size();
        }
        return 0;
    }

    public class ListSavedItem extends RecyclerView.ViewHolder{
        private TextView NameType, NameService, Rating, Summary, TimOpen, NamePlane;
        private ImageView Star1, Star2, Star3, Star4, Star5;

        public ListSavedItem(@NonNull View itemView) {
            super(itemView);
            NameType = itemView.findViewById(R.id.item_savedtitem_txt_NameType);
            NameService = itemView.findViewById(R.id.item_savedtitem_txt_NameService);
            Star1 = itemView.findViewById(R.id.saveitem_star_1);
            Star2 = itemView.findViewById(R.id.saveitem_star_2);
            Star3 = itemView.findViewById(R.id.saveitem_star_3);
            Star4 = itemView.findViewById(R.id.saveitem_star_4);
            Star5 = itemView.findViewById(R.id.saveitem_star_5);
            Summary= itemView.findViewById(R.id.item_savedtitem_txt_Summary);
            TimOpen = itemView.findViewById(R.id.item_savedtitem_txt_TimeOpen);
            NamePlane = itemView.findViewById(R.id.item_savedtitem_txt_NamePlan);
        }
    }
}
