package com.example.simpletravel.ui.discovery;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.JDBC.JDBCModel;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DiscoveryViewModel extends ViewModel {


    private MutableLiveData<List<Services>> mServices;
    private List<Services> mlist;
    private Connection connection;
    private JDBCControllers jdbcControllers;
    private Statement statement;

    private MutableLiveData<List<Location> > mLocations;
    private List<Location> mlistLocation;


    public DiscoveryViewModel() throws SQLException {
        mServices = new MutableLiveData<>();
        InitData();

        mLocations = new MutableLiveData<>();
        HotelData();
    }

    private void InitData() throws SQLException {
        mlist = new ArrayList<>();
        jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
        connection = jdbcControllers.ConnectionData();
        Log.e("Log","True");
        statement = connection.createStatement();

         String sql = "select  IdService, NameService, Summary, Status, Contact, Address, Images from Services ";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){

            mlist.add(new Services(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5),resultSet.getString(6), resultSet.getInt(7)));
        }
        mServices.setValue(mlist);

    }

    public MutableLiveData<List<Services>> getServices() {
        return mServices;
    }

    private void HotelData() throws SQLException{
        mlistLocation = new ArrayList<>();
        jdbcControllers = new JDBCControllers();
        connection = jdbcControllers.ConnectionData();
        Log.e("Log", "True");
        statement = connection.createStatement();

        String hotel = "SELECT * FROM Location, Country " ;
        ResultSet resultSet = statement.executeQuery(hotel);
        while (resultSet.next()){
            mlistLocation.add(new Location(resultSet.getInt(1), resultSet.getString(3),resultSet.getString(6),resultSet.getString(7), resultSet.getInt(4)));
        }

        mLocations.setValue(mlistLocation);


    }

    public MutableLiveData<List<Location>> getLocations() {
        return mLocations;
    }
    //    private MutableLiveData<List<Services>> mHistory;
//    private List<History> mlist;
//
//    public DiscoveryViewModel(){
//        mHistory = new MutableLiveData<>();
//        InitData();
//    }
//
//    private void InitData() {
//        mlist = new ArrayList<>();
//        mlist.add(new History(R.drawable.img,"Hang nhat","Nam Phuong"));
//        mlist.add(new History(R.drawable.fb,"Hang nhat","Nam Phuong"));
//        mlist.add(new History(R.drawable.fb,"Hang nhat","Nam Phuong"));
//        mlist.add(new History(R.drawable.fb,"Hang nhat","Nam Phuong"));
//        mlist.add(new History(R.drawable.fb,"Hang nhat","Nam Phuong"));
//
//        mHistory.setValue(mlist);
//    }
//
//    public MutableLiveData<List<History>> getHistory() {
//        return mHistory;
//    }
}