package com.example.simpletravel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simpletravel.R;
import com.example.simpletravel.model.ListTrip;
import com.example.simpletravel.model.Services;

import java.util.ArrayList;
import java.util.List;

public class RecentlyRearchAdapter extends ArrayAdapter<Services> {
    private List<Services> servicesList;

    public RecentlyRearchAdapter(@NonNull Context context, int resource, @NonNull List<Services> objects) {
        super(context, resource, objects);
        servicesList = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        View viewdialog;

        RecentlyRearchAdapter.ViewHodler holder;

        if (view == null) {
            viewdialog = View.inflate(parent.getContext(), R.layout.item_evaluate_listview, null);

            holder = new RecentlyRearchAdapter.ViewHodler();
            holder.NameService = (TextView) viewdialog.findViewById(R.id.evaluate_txt_NameService);
            holder.Location = (TextView) viewdialog.findViewById(R.id.evaluate_txt_Location);
            viewdialog.setTag(holder);
            //event click checkbox

        } else {
            viewdialog = view;
            holder = (RecentlyRearchAdapter.ViewHodler) viewdialog.getTag();
        }

        Services services = servicesList.get(position);
        holder.NameService.setText(services.getName());
        holder.Location.setText(services.getAddress());
        return viewdialog;
    }

    class ViewHodler{
        String Images;
        TextView NameService, Location;
    }
}
