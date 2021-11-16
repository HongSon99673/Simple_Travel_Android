package com.example.simpletravel.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Trip;

import java.util.List;

public class DialogListTripAdapter extends BaseAdapter {
    private List<Trip> tripList;

    public DialogListTripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @Override
    public int getCount() {
        return tripList.size();
    }

    @Override
    public Object getItem(int i) {
        return tripList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View viewdialog;
        if(view == null){
            viewdialog = View.inflate(viewGroup.getContext(), R.layout.item_dialog_listview_trip, null);
        } else viewdialog = view;

        Trip trip = (Trip) getItem(i);

        ((TextView) viewdialog.findViewById(R.id.dialoglistTrip_txt_Name_Trip)).setText(trip.getNameTrip());
        ((TextView) viewdialog.findViewById(R.id.dialoglistrip_txt_SaveItem)).setText(String.valueOf(trip.getSavedItem()+ "mục đã lưu"));

        return viewdialog;
    }
}
