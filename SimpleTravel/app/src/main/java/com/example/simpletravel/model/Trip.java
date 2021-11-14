package com.example.simpletravel.model;

import java.io.Serializable;

public class Trip implements Serializable {

    private int IdTrip;
    private String NameTrip;
    private int SavedItem;

    public Trip() {
    }

    public Trip(int idTrip, String nameTrip, int savedItem) {
        IdTrip = idTrip;
        NameTrip = nameTrip;
        SavedItem = savedItem;
    }

    public int getIdTrip() {
        return IdTrip;
    }

    public void setIdTrip(int idTrip) {
        IdTrip = idTrip;
    }

    public String getNameTrip() {
        return NameTrip;
    }

    public void setNameTrip(String nameTrip) {
        NameTrip = nameTrip;
    }

    public int getSavedItem() {
        return SavedItem;
    }

    public void setSavedItem(int savedItem) {
        SavedItem = savedItem;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "IdTrip=" + IdTrip +
                ", NameTrip='" + NameTrip + '\'' +
                ", SavedItem=" + SavedItem +
                '}';
    }
}
