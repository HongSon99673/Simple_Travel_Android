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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdServices;
import com.example.simpletravel.ui.search.DetailsSearchFragment;

import java.util.List;

public class VinicityLocationAdapter extends RecyclerView.Adapter<VinicityLocationAdapter.ListServices> {

    private List<Services> servicesList;

    public VinicityLocationAdapter(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ListServices onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locationvicinity_fragment_search,
                parent, false);
        return new ListServices(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListServices holder, int position) {

        Services services = servicesList.get(position);
        if (services == null) {
            return;
        }
        //set text name service
        holder.txtName.setText(services.getName());
        //set images service
        byte[] decodedString = Base64.decode(String.valueOf(services.getImages()), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.Image.setImageBitmap(decodedByte);
        //set star service
        if (services.getRatings() == 1) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (services.getRatings() == 2) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (services.getRatings() == 3) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (services.getRatings() == 4) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (services.getRatings() == 5) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star5.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        //set text quantity
        holder.txtQuantity.setText(String.valueOf(services.getQuantity()));
        //set summary
        holder.txtSummary.setText(services.getSummary());
        //set status service
        holder.txtStatus.setText(services.getNameStatus());
        //event click item in list view
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdServices.IdService = services.getID();
                FragmentActivity activity = (FragmentActivity) view.getContext();
                Fragment myFragment = new DetailsSearchFragment();
                activity.getSupportFragmentManager().beginTransaction().add(
                        R.id.frameLayout_search, myFragment).addToBackStack(DetailsSearchFragment.TAG1).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (servicesList != null) {
            return servicesList.size();
        }
        return 0;
    }

    public class ListServices extends RecyclerView.ViewHolder {

        private TextView txtName, txtSummary, txtQuantity, txtStatus;
        private ImageView Image, Star1, Star2, Star3, Star4, Star5;
        private LinearLayout linearLayout;

        public ListServices(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.img_VinicityLocation_Search);
            txtName = itemView.findViewById(R.id.txt_NameLocation_Search);
            txtSummary = itemView.findViewById(R.id.txt_Summary_Search);
            txtQuantity = itemView.findViewById(R.id.search_txt_Quantity);
            txtStatus = itemView.findViewById(R.id.search_txt_Status);
            Star1 = itemView.findViewById(R.id.search_star_1);
            Star2 = itemView.findViewById(R.id.search_star_2);
            Star3 = itemView.findViewById(R.id.search_star_3);
            Star4 = itemView.findViewById(R.id.search_star_4);
            Star5 = itemView.findViewById(R.id.search_star_5);
            linearLayout = itemView.findViewById(R.id.search_vincinity_item_Layout);

        }
    }
}
