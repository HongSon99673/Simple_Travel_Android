package com.example.simpletravel.model;

import java.io.Serializable;

public class Location implements Serializable {

    private int IdLocation;
    private String NameLocation;
    private String NameCountry;
    private String NameContinents;
    private int ImageLocation;

    public Location() {
    }

    public Location(int idLocation, String nameLocation, String nameCountry, String nameContinents, int imageLocation) {
        IdLocation = idLocation;
        NameLocation = nameLocation;
        NameCountry = nameCountry;
        NameContinents = nameContinents;
        ImageLocation = imageLocation;
    }

    public int getIdLocation() {
        return IdLocation;
    }

    public void setIdLocation(int idLocation) {
        IdLocation = idLocation;
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

    public String getNameContinents() {
        return NameContinents;
    }

    public void setNameContinents(String nameContinents) {
        NameContinents = nameContinents;
    }

    public int getImageLocation() {
        return ImageLocation;
    }

    public void setImageLocation(int imageLocation) {
        ImageLocation = imageLocation;
    }

    @Override
    public String toString() {
        return "Location{" +
                "IdLocation=" + IdLocation +
                ", NameLocation='" + NameLocation + '\'' +
                ", NameCountry='" + NameCountry + '\'' +
                ", NameContinents='" + NameContinents + '\'' +
                ", ImageLocation=" + ImageLocation +
                '}';
    }
}
