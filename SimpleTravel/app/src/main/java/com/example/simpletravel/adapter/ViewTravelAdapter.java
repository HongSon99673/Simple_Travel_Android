package com.example.simpletravel.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Location;

import java.util.List;

public class ViewTravelAdapter extends RecyclerView.Adapter<ViewTravelAdapter.ListLocation> {

    private List<Location> locationList;

    public ViewTravelAdapter(List<Location> locationList) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public ListLocation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewtravel_fragment_discovery, parent, false);
        return new ListLocation(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListLocation holder, int position) {

        Location location = locationList.get(position);

        if (location == null) {
            return;
        }
//        byte[] decodedString = Base64.decode(String.valueOf(location.getImageLocation()), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        holder.imageLocation.setImageBitmap(decodedByte);
        holder.nameLocation.setText(location.getNameLocation());
        holder.nameCountry.setText(location.getNameCountry() + ", " + location.getNameContinents());
    }


    @Override
    public int getItemCount() {
        if (locationList != null) {
            return locationList.size();
        }
        return 0;
    }

    public class ListLocation extends RecyclerView.ViewHolder {
        private ImageView imageLocation;
        private TextView nameLocation, nameCountry;

        public ListLocation(@NonNull View itemView) {
            super(itemView);

            imageLocation = itemView.findViewById(R.id.img_ViewTravel_Item_Discovery);
            nameLocation = itemView.findViewById(R.id.txt_Name_ViewTravel_Discovery);
            nameCountry = itemView.findViewById(R.id.txt_Location_ViewTravel_Discovery);

        }
    }
}
