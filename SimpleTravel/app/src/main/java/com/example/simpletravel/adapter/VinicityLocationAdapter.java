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
public class VinicityLocationAdapter extends RecyclerView.Adapter<VinicityLocationAdapter.ListServices> {

    private List<Services> servicesList;

    public VinicityLocationAdapter(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ListServices onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locationvicinity_fragment_search,
                parent,false);
        return new ListServices(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListServices holder, int position) {

        Services services = servicesList.get(position);
        if(services == null){
            return;
        }

//        holder.Image.setImageResource(services.getImages());
        holder.txtName.setText(services.getName());
        holder.txtRating.setText(services.getRatings());
        holder.txtSummary.setText(services.getSummary());


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



        public ListServices(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.img_VinicityLocation_Search);
            txtName = itemView.findViewById(R.id.txt_NameLocation_Search);
            txtRating = itemView.findViewById(R.id.txt_Rating_Search);
            txtSummary = itemView.findViewById(R.id.txt_Summary_Search);

        }
    }
}
