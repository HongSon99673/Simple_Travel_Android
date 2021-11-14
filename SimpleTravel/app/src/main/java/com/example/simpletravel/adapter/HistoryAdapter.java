package com.example.simpletravel.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.IdServices;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.ui.search.DetailsSearchFragment;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ListServices> {

    private List<Services> servicesList;

    public HistoryAdapter(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ListServices onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historyview_fragment_discovery,
                parent,false);
        return new ListServices(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListServices holder, int position) {

        Services services = servicesList.get(position);
        if(services == null){
            return;
        }
        byte[] decodedString = Base64.decode(String.valueOf(services.getImages()), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.Image.setImageBitmap(decodedByte);

        holder.txtName.setText(services.getName());
//        holder.txtRating.setText(String.valueOf(services.getRatings()));
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
        holder.txtLocation.setText(services.getAddress());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdServices.IdService = services.getID();//reset IdService
                Toast.makeText(view.getContext(), services.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(servicesList != null){
            return servicesList .size();
        }
        return 0;
    }

    public class ListServices extends RecyclerView.ViewHolder{

        private ImageView Image, Star1, Star2, Star3, Star4, Star5;
        private TextView txtRating, txtQuantity ,txtName,txtSummary,txtLocation;
        private LinearLayout linearLayout;

        public ListServices(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.img_History_Item_Discovery);
            txtName = itemView.findViewById(R.id.txt_Name_Item_Discovery);
            Star1 = itemView.findViewById(R.id.discovery_star_1);
            Star2 = itemView.findViewById(R.id.discovery_star_2);
            Star3 = itemView.findViewById(R.id.discovery_star_3);
            Star4 = itemView.findViewById(R.id.discovery_star_4);
            Star5 = itemView.findViewById(R.id.discovery_star_5);
            txtQuantity = itemView.findViewById(R.id.txt_Quantity_Item_Discovery);
            txtSummary = itemView.findViewById(R.id.txt_Summary_Item_Discovery);
            txtLocation = itemView.findViewById(R.id.txt_Location_Item_Discovery);
            linearLayout = itemView.findViewById(R.id.fragment_details);
        }
    }
}


