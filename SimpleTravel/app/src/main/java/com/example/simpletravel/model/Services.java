package com.example.simpletravel.model;

import java.io.Serializable;

public class Services implements Serializable {

    private int ID;
    private String Name;
    private String Ratings;
    private String Summary;
    private String Contact;
    private String Address;
    private int Images;

    public Services() {
    }

    public Services(int ID, String name, String ratings,
                    String summary, String contact, String address, int image) {
        this.ID = ID;
        Name = name;
        Ratings = ratings;
        Summary = summary;
        Contact = contact;
        Address = address;
        this.Images = image;
    }

    public Services(int id, String name, String ratings, String sumary, String contact, String address) {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }

    @Override
    public String toString() {
        return "Services{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Ratings='" + Ratings + '\'' +
                ", Summary='" + Summary + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Address='" + Address + '\'' +
                ", Images='" + Images + '\'' +
                '}';
    }
}
