package com.example.libary.model;

import java.io.Serializable;

public class History implements Serializable {
    private int Image;
    private String Ratings;
    private String Name;

    public History() {
    }

    public History(int image, String ratings, String name) {
        this.Image = image;
        this.Ratings = ratings;
        this.Name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
