package com.example.simpletravel.model;

import java.io.Serializable;

public class Users implements Serializable {
    private int IdUser;
    private String UserName;
    private String Email;
    private String Password;
    private String Address;
    private String Introduce;
    private String Phone;

    public Users(int idUser, String userName, String email, String password, String address, String introduce, String phone) {
        IdUser = idUser;
        UserName = userName;
        Email = email;
        Password = password;
        Address = address;
        Introduce = introduce;
        Phone = phone;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    @Override
    public String toString() {
        return "Users{" +
                "IdUser=" + IdUser +
                ", UserName='" + UserName + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Address='" + Address + '\'' +
                ", Introduce='" + Introduce + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }
}
