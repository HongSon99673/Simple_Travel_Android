package com.example.simpletravel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Temp.IdTrip;
import com.example.simpletravel.model.Trip;
import com.example.simpletravel.ui.planning.DetailsTripFragment;
import com.example.simpletravel.ui.planning.TripPlanningFragment;
import com.example.simpletravel.ui.search.DetailsSearchFragment;

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
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdTrip.IdTrips = trip.getIdTrip();//set id Trip in Temp
                FragmentActivity activity = (FragmentActivity) view.getContext();
                Fragment myFragment = new DetailsTripFragment();
                activity.getSupportFragmentManager().beginTransaction().add(
                        R.id.planning_frameLayout_Main, myFragment).addToBackStack(DetailsTripFragment.TAG).commit();
            }
        });
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
        private LinearLayout linearLayout;

        public ListTrip(@NonNull View itemView) {
            super(itemView);

            SaveItem = itemView.findViewById(R.id.item_trip_txt_SavedItem);
            NameTrip = itemView.findViewById(R.id.item_trip_txt_NameTrip);
            linearLayout = itemView.findViewById(R.id.trip_Layout);
        }
    }

}
