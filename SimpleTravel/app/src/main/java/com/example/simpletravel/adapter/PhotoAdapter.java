package com.example.simpletravel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.simpletravel.R;
import com.example.simpletravel.model.Photo;


import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    private Context mContext;
    private List<Photo> mPhoto;

    public PhotoAdapter(Context mContext, List<Photo> mPhoto) {
        this.mContext = mContext;
        this.mPhoto = mPhoto;
    }

    private ImageView imageView;
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo,container, false);
        imageView = view.findViewById(R.id.search_img_Photo);

        Photo photo = mPhoto.get(position);
        if(photo != null){
            Glide.with(mContext).load(photo.getResourceId()).into(imageView);
        }
        //Add view to viewgroup
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (mPhoto !=  null){
            return mPhoto.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
