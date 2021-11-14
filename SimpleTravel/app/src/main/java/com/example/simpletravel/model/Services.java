package com.example.simpletravel.model;

import java.io.InputStream;
import java.io.Serializable;

public class Services implements Serializable {

    private int ID;
    private String Name;
    private int Ratings;
    private int Quantity;
    private String Summary;
    private String Phone;
    private String URL;
    private String Address;
    private String NameStatus;
    private String OpenTime;
    private int SuggestTime;
    private String Images;

    public Services() {
    }

    public Services(int ID, String name, int ratings, int quantity, String summary,
                    String phone, String URL, String address, String nameStatus,
                    String openTime, int suggestTime,String images) {
        this.ID = ID;
        Name = name;
        Ratings = ratings;
        Quantity = quantity;
        Summary = summary;
        Phone = phone;
        this.URL = URL;
        Address = address;
        NameStatus = nameStatus;
        OpenTime = openTime;
        SuggestTime = suggestTime;
        Images = images;
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

    public int getRatings() {
        return Ratings;
    }

    public void setRatings(int ratings) {
        Ratings = ratings;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNameStatus() {
        return NameStatus;
    }

    public void setNameStatus(String nameStatus) {
        NameStatus = nameStatus;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public int getSuggestTime() {
        return SuggestTime;
    }

    public void setSuggestTime(int suggestTime) {
        SuggestTime = suggestTime;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    @Override
    public String toString() {
        return "Services{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Ratings=" + Ratings +
                ", Quantity=" + Quantity +
                ", Summary='" + Summary + '\'' +
                ", Phone='" + Phone + '\'' +
                ", URL='" + URL + '\'' +
                ", Address='" + Address + '\'' +
                ", NameStatus='" + NameStatus + '\'' +
                ", OpenTime='" + OpenTime + '\'' +
                ", SuggestTime=" + SuggestTime +
                ", Images=" + Images +
                '}';
    }
}
