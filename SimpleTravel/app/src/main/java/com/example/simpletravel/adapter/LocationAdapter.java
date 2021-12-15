package com.example.simpletravel.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Comment;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;

import java.util.List;

public class LocationAdapter extends BaseAdapter {
    private List<Location> locationList;

    public LocationAdapter(List<Location> locationList) {
        this.locationList = locationList;
    }

    @Override
    public int getCount() {
        if(locationList != null){
            return locationList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return locationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.item_gridview_serch, null);
        } else viewProduct = view;

        //bind data vao layout
        Location list = (Location) getItem(i);
        //set images location
        byte[] decodedString = Base64.decode(String.valueOf(list.getImageLocation()), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ((ImageView) viewProduct.findViewById(R.id.item_gridview_Img)).setImageBitmap(decodedByte);
        //set name location
        ((TextView) viewProduct.findViewById(R.id.item_gridview_NameLocation)).setText(list.getNameLocation());

        return viewProduct;
    }
}
