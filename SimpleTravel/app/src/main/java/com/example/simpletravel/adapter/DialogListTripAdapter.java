package com.example.simpletravel.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.simpletravel.R;
import com.example.simpletravel.model.ListTrip;
import com.example.simpletravel.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class DialogListTripAdapter extends ArrayAdapter<ListTrip> {
    private List<ListTrip> tripList;

    public DialogListTripAdapter(@NonNull Context context, int resource, @NonNull List<ListTrip> objects) {
        super(context, resource, objects);
        tripList = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        View viewdialog;
        ViewHodler holder;
        if (view == null) {
            viewdialog = View.inflate(parent.getContext(), R.layout.item_dialog_listview_trip, null);

            holder = new ViewHodler();
            holder.NameTrip = (TextView) viewdialog.findViewById(R.id.dialoglistTrip_txt_Name_Trip);
            holder.isChecked = (CheckBox) viewdialog.findViewById(R.id.item_dialoglistrip_rb_Choose);
            viewdialog.setTag(holder);
            //event click checkbox
            holder.isChecked.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    ListTrip country = (ListTrip) cb.getTag();
                    if(cb.isChecked() == true){
                        Toast.makeText(getContext(),
                                "Bạn đã chọn Chuyến đi: " + cb.getText() + country.getNameTrip(),
                                Toast.LENGTH_SHORT).show();
                    }
                    if(cb.isChecked() == false){
                        Toast.makeText(getContext(),
                                "Bạn đã bỏ chọn Chuyến đi: " + cb.getText() + country.getNameTrip(),
                                Toast.LENGTH_SHORT).show();
                    }
                    country.setChecked(cb.isChecked());
                }
            });
        } else {
            viewdialog = view;
            holder = (ViewHodler) viewdialog.getTag();
        }

        ListTrip country = tripList.get(position);
        holder.NameTrip.setText(country.getNameTrip());
        holder.isChecked.setChecked(country.isChecked());
        holder.isChecked.setTag(country);

        return viewdialog;
    }

    class ViewHodler{
        TextView NameTrip;
        String Images;
        CheckBox isChecked;
    }
}


