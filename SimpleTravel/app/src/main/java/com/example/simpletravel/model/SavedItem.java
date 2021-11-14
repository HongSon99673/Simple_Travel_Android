package com.example.simpletravel.model;

import java.io.Serializable;

public class SavedItem implements Serializable {
    private int IdService;
    private String NameType;
    private String NameService;
    private int Rating;
    private String Summary;
    private String TimeOpen;
    private String NamePlan;
    private int Choose;

    public SavedItem() {
    }

    public SavedItem(int idService, String nameType, String nameService,int rating,
                     String summary, String timeOpen, String namePlan, int choose) {
        IdService = idService;
        NameType = nameType;
        NameService = nameService;
        Rating = rating;
        Summary = summary;
        TimeOpen = timeOpen;
        NamePlan = namePlan;
        Choose = choose;
    }

    public int getIdService() {
        return IdService;
    }

    public void setIdService(int idService) {
        IdService = idService;
    }

    public String getNameType() {
        return NameType;
    }

    public void setNameType(String nameType) {
        NameType = nameType;
    }

    public String getNameService() {
        return NameService;
    }

    public void setNameService(String nameService) {
        NameService = nameService;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getTimeOpen() {
        return TimeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        TimeOpen = timeOpen;
    }

    public String getNamePlan() {
        return NamePlan;
    }

    public void setNamePlan(String namePlan) {
        NamePlan = namePlan;
    }

    public int getChoose() {
        return Choose;
    }

    public void setChoose(int choose) {
        Choose = choose;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    @Override
    public String toString() {
        return "SavedItem{" +
                "IdService=" + IdService +
                ", NameType='" + NameType + '\'' +
                ", NameService='" + NameService + '\'' +
                ", Summary='" + Summary + '\'' +
                ", TimeOpen='" + TimeOpen + '\'' +
                ", NamePlan='" + NamePlan + '\'' +
                ", Choose=" + Choose +
                '}';
    }
}
