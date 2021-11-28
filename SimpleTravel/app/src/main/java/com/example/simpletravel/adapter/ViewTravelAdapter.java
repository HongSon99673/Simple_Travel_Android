package com.example.simpletravel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Temp.IdLocation;
import com.example.simpletravel.my_interface.IClickItemService;
import com.example.simpletravel.ui.discovery.LocationFragment;

import java.util.List;

public class ViewTravelAdapter extends RecyclerView.Adapter<ViewTravelAdapter.ListLocation> {

    private List<Location> locationList;
    private IClickItemService iClickItemService;

    public ViewTravelAdapter(List<Location> locationList, IClickItemService iClickItemService) {
        this.locationList = locationList;
        this.iClickItemService = iClickItemService;
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

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdLocation.IdLocations = location.getIdLocation();//set idLocation
                FragmentActivity activity = (FragmentActivity) view.getContext();
                Fragment myFragment = new LocationFragment();
                activity.getSupportFragmentManager().beginTransaction().add(
                        R.id.discovery_frameLayout_Main, myFragment).addToBackStack(LocationFragment.TAG1).commit();
            }
        });
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
        private LinearLayout linearLayout;

        public ListLocation(@NonNull View itemView) {
            super(itemView);

            imageLocation = itemView.findViewById(R.id.img_ViewTravel_Item_Discovery);
            nameLocation = itemView.findViewById(R.id.txt_Name_ViewTravel_Discovery);
            nameCountry = itemView.findViewById(R.id.txt_Location_ViewTravel_Discovery);
            linearLayout = itemView.findViewById(R.id.discovery_item_ViewTravel);

        }
    }
}
