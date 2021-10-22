package com.example.libary.model;

import android.widget.ImageView;

public class Location {

    private ImageView img;
    private String NameLocation;
    private String NameCountry;

    public Location() {
    }

    public Location(ImageView img, String nameLocation, String nameCountry) {
        this.img = img;
        this.NameLocation = nameLocation;
        this.NameCountry = nameCountry;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public String getNameLocation() {
        return NameLocation;
    }

    public void setNameLocation(String nameLocation) {
        NameLocation = nameLocation;
    }

    public String getNameCountry() {
        return NameCountry;
    }

    public void setNameCountry(String nameCountry) {
        NameCountry = nameCountry;
    }

    @Override
    public String toString() {
        return img + NameLocation + NameCountry  ;
    }
}
