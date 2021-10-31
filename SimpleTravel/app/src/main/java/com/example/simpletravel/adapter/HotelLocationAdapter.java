package com.example.simpletravel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;

import java.util.List;

public class HotelLocationAdapter extends RecyclerView.Adapter<HotelLocationAdapter.ListLocation> {
    private List<Location> locationList;

    public HotelLocationAdapter(List<Location> locationList) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public ListLocation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotelsmall_fragment_discovery,parent, false);
        return new ListLocation(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListLocation holder, int position) {

        Location location = locationList.get(position);
        if(location == null){
            return;
        }

//        holder.imageLocation.setImageResource(location.getImageLocation());
        holder.nameLocation.setText(location.getNameLocation());
    }

    @Override
    public int getItemCount() {
        if (locationList != null){
            return locationList.size();
        }
        return 0;
    }

    public class ListLocation extends RecyclerView.ViewHolder {
        private ImageView imageLocation;
        private TextView nameLocation;

        public ListLocation(@NonNull View itemView) {
            super(itemView);

            imageLocation = itemView.findViewById(R.id.img_HotelLocation_Item_Discovery);
            nameLocation = itemView.findViewById(R.id.txt_NameLocation_Hotel_Discovery);
        }
    }




}