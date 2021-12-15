package com.example.simpletravel.model;

import java.io.Serializable;

public class Comment implements Serializable {

    private int IdRating;
    private int IdUser;
    private  String UserName;
    private String Introduce;
    private String Images;
    private int Contribute;
    private int Evaluate;
    private String Title;
    private String Time;
    private String Type;
    private String Summary;
    private int Like;

    public Comment() {
    }

    public Comment(int idRating, int idUser, String userName, String introduce, String images, int contribute, int evaluate,
                   String title, String time, String type, String summary, int like) {
        IdRating = idRating;
        IdUser = idUser;
        UserName = userName;
        Introduce = introduce;
        Images = images;
        Contribute = contribute;
        Evaluate = evaluate;
        Title = title;
        Time = time;
        Type = type;
        Summary = summary;
        Like = like;
    }

    public int getIdRating() {
        return IdRating;
    }

    public void setIdRating(int idRating) {
        IdRating = idRating;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getContribute() {
        return Contribute;
    }

    public void setContribute(int contribute) {
        Contribute = contribute;
    }

    public int getEvaluate() {
        return Evaluate;
    }

    public void setEvaluate(int evaluate) {
        Evaluate = evaluate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "IdRating=" + IdRating +
                ", UserName='" + UserName + '\'' +
                ", Contribute=" + Contribute +
                ", Evaluate=" + Evaluate +
                ", Title='" + Title + '\'' +
                ", Time='" + Time + '\'' +
                ", Type='" + Type + '\'' +
                ", Summary='" + Summary + '\'' +
                ", Like=" + Like +
                '}';
    }
}
