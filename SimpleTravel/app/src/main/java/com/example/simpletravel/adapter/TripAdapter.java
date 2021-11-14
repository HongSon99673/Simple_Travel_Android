package com.example.simpletravel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ListTrip>  {
    private List<Trip> tripList;

    public TripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public ListTrip onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip,parent, false);

        return new ListTrip(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTrip holder, int position) {

        Trip trip = tripList.get(position);
        if (trip == null){
            return;
        }

        holder.SaveItem.setText(String.valueOf(trip.getSavedItem()+ " mục đã lưu"));
        holder.NameTrip.setText(trip.getNameTrip());
    }

    @Override
    public int getItemCount() {
        if (tripList != null){
            return tripList.size();
        }
        return 0;
    }

    public class ListTrip extends RecyclerView.ViewHolder{

        private TextView SaveItem, NameTrip;

        public ListTrip(@NonNull View itemView) {
            super(itemView);

            SaveItem = itemView.findViewById(R.id.item_trip_txt_SavedItem);
            NameTrip = itemView.findViewById(R.id.item_trip_txt_NameTrip);
        }
    }

}
