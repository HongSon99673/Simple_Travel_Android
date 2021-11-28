package com.example.simpletravel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdLocation;
import com.example.simpletravel.model.Temp.IdServices;
import com.example.simpletravel.ui.discovery.LocationFragment;
import com.example.simpletravel.ui.search.DetailsSearchFragment;

import java.util.List;

public class ItemLocationAdapter  extends RecyclerView.Adapter<ItemLocationAdapter.ListServiceLocation>{

    private List<Services> servicesList;

    public ItemLocationAdapter(List<Services> services) {
        this.servicesList = services;
    }

    @NonNull
    @Override
    public ListServiceLocation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discovery_location,
                parent,false);
        return new ListServiceLocation(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListServiceLocation holder, int position) {
        Services services = servicesList.get(position);
        if(services == null){
            return;
        }
//        holder.txtTitle.setText(services.getName());
        holder.txtName.setText(services.getName());
//        byte[] decodedString = Base64.decode(String.valueOf(services.getImages()), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        holder.Image.setImageBitmap(decodedByte);

        if(services.getRatings() == 1){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if(services.getRatings() == 2){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if(services.getRatings() == 3){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if(services.getRatings() == 4){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if(services.getRatings() == 5){
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star5.setImageResource(R.drawable.outline_star_purple500_black_48);
        }

        holder.txtQuantity.setText(String.valueOf(services.getQuantity()));
        holder.txtSummary.setText(services.getSummary());
        holder.txtStatus.setText(services.getNameStatus());
        //event click item in recycle view
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdLocation.IdLocations = 1;//set IdLocation
                IdServices.IdService = services.getID();//set idService
                FragmentActivity activity = (FragmentActivity) view.getContext();
                Fragment myFragment = new DetailsSearchFragment();
                activity.getSupportFragmentManager().beginTransaction().add(
                        R.id.discovery_frameLayout_Main, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(servicesList!= null){
            return servicesList.size();
        }
        return 0;
    }

    public class ListServiceLocation extends RecyclerView.ViewHolder {
        private TextView txtName, txtSummary, txtQuantity, txtStatus, txtTitle;
        private ImageView Image, Star1, Star2, Star3, Star4, Star5;
        private LinearLayout linearLayout;

        public ListServiceLocation(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.discovery_location_Img);
            txtName = itemView.findViewById(R.id.discovery_location_txt_NameLocation);
            txtSummary = itemView.findViewById(R.id.discovery_location_txt_Summary);
            txtQuantity = itemView.findViewById(R.id.discovery_location_Quantity);
            txtStatus = itemView.findViewById(R.id.discovery_location_txt_Status);
            txtTitle = itemView.findViewById(R.id.discovery_location_Title);
            Star1 = itemView.findViewById(R.id.discovery_location_star_1);
            Star2 = itemView.findViewById(R.id.discovery_location_star_2);
            Star3 = itemView.findViewById(R.id.discovery_location_star_3);
            Star4 = itemView.findViewById(R.id.discovery_location_star_4);
            Star5 = itemView.findViewById(R.id.discovery_location_star_5);
            linearLayout = itemView.findViewById(R.id.discovery_location_Layout);

        }
    }
}
