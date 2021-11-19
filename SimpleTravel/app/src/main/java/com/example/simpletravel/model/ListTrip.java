package com.example.simpletravel.model;

import java.io.Serializable;

public class ListTrip implements Serializable {
    private int IdTrip;
    private String NameTrip;
    private boolean isChecked;

    public ListTrip() {
    }

    public ListTrip(int idTrip, String nameTrip, boolean isChecked) {
        IdTrip = idTrip;
        NameTrip = nameTrip;
        this.isChecked = isChecked;

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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "ListTrip{" +
                "IdTrip=" + IdTrip +
                ", NameTrip='" + NameTrip + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
