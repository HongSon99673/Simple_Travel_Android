package com.example.simpletravel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Services;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ListServices> {

    private List<Services> servicesList;

    public HotelAdapter(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ListServices onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotelsmall_fragment_discovery,
                parent,false);
        return new ListServices(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListServices holder, int position) {

        Services services = servicesList.get(position);
        if(services == null){
            return;
        }

        holder.Image.setImageResource(services.getImages());
        holder.txtName.setText(services.getName());
        holder.txtRating.setText(services.getRatings());
        holder.txtSummary.setText(services.getSummary());
        holder.txtLocation.setText(services.getAddress());

    }

    @Override
    public int getItemCount() {
        if(servicesList != null){
            return servicesList .size();
        }
        return 0;
    }

    public class ListServices extends RecyclerView.ViewHolder{

        private ImageView Image;
        private TextView txtRating;
        private TextView txtName;
        private TextView txtSummary;
        private TextView txtLocation;


        public ListServices(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.img_Avatar_Item_Discovery);
            txtName = itemView.findViewById(R.id.txt_Name_Item_Discovery);
            txtRating = itemView.findViewById(R.id.txt_Rating_Item_Discovery);
            txtSummary = itemView.findViewById(R.id.txt_Summary_Item_Discovery);
            txtLocation = itemView.findViewById(R.id.txt_Location_Item_Discovery);
        }
    }
}