package com.example.simpletravel.adapter;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Services;

import java.util.ArrayList;
import java.util.List;

public class ItemSearchAdapter extends ArrayAdapter<Services> {
    private List<Services> mservices;


    public ItemSearchAdapter(@NonNull Context context, int resource, @NonNull List<Services> objects) {
        super(context, resource, objects);
        mservices = new ArrayList<>(objects);
    }



    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_fragment_item_search, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.item_search_txt_NameLocation);
        TextView textViewLocation = convertView.findViewById(R.id.item_search_txt_Location);

        Services services = getItem(position);

        if(services != null){
            textViewName.setText(services.getName());
            textViewLocation.setText(services.getAddress());
        }
        return convertView;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            List<Services> suggets = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                suggets.addAll(mservices);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Services services : mservices) {
                    if (services.getName().toLowerCase().contains(filterPattern)) {
                        suggets.add(services);
                    }
                }
            }
            filterResults.values = suggets;
            filterResults.count = suggets.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Services) resultValue).getName();
        }
    };
}
