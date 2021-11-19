package com.example.simpletravel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoCommentAdapter extends RecyclerView.Adapter<PhotoCommentAdapter.PhotoViewHodler>{
    private Context mcontext;
    private List<Uri> mlistPhoto;

    public PhotoCommentAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void setMlistPhoto(List<Uri> mlistPhoto) {
        this.mlistPhoto = mlistPhoto;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_comment,parent,false);

        return new PhotoViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHodler holder, int position) {
        Uri uri = mlistPhoto.get(position);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mcontext.getContentResolver(), uri);
            holder.imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(mlistPhoto == null){
            return 0;
        } else {
            return mlistPhoto.size();
        }
    }

    public class PhotoViewHodler extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public PhotoViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.evaluate_item_img_Photo);
        }
    }
}
