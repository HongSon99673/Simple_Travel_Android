package com.example.simpletravel.ui.discovery;



import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.activity.LoginEmailActivity;
import com.example.simpletravel.model.IdUsers;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Users;

import java.sql.Connection;

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
    private Users users;
    private LoginEmailActivity loginEmailActivity;


    private MutableLiveData<List<Location> > mLocations;
    private List<Location> mlistLocation;


    public DiscoveryViewModel() throws SQLException {
        mServices = new MutableLiveData<>();
        InitData();
        mLocations = new MutableLiveData<>();
        HotelData();
    }

    //
    private int IdUser;

    private void InitData() throws SQLException {

        IdUser = IdUsers.IdUser; // transmit logged in user
        mlist = new ArrayList<>();
        jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
        connection = jdbcControllers.ConnectionData();
        Log.e("Log","True");
        statement = connection.createStatement();

         String sql = "select Services.IdService,Services.NameService, AVG(Ratings.Ratings) as Ratings ,Count( Ratings.IdService) as Quantity\n" +
                 ", Services.Summary, Services.Phone, Services.URL, Services.Address, Status.NameStatus, Status.TimeOpen,\n" +
                 "Services.SuggestTime, Services.Images\n" +
                 "from Services, Ratings, HistoryServices, Users, Status\n" +
                 "where Services.IdService = Ratings.IdService and Services.IdService = HistoryServices.IdService \n" +
                 "and Services.IdStatus = Status.IdStatus and HistoryServices.IdUser = Users.IdUser and Users.IdUser = '"+ IdUser + "' \n" +
                 "group by Services.IdService, Services.NameService,Services.Summary, Services.URL,\n" +
                 "Services.Phone, Services.SuggestTime,Services.Images,Status.NameStatus, Status.TimeOpen,Services.Address";

        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            mlist.add(new Services(resultSet.getInt("IdService"),resultSet.getString(2),resultSet.getInt(3),
                    resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6), resultSet.getString(7),
                    resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12)));
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

        String hotel = "SELECT * FROM Location, Country Where Location.IdCountry = Country.IdCountry " ;
        ResultSet resultSet = statement.executeQuery(hotel);
        while (resultSet.next()){
            mlistLocation.add(new Location(resultSet.getInt(1), resultSet.getString(3),resultSet.getString(6),resultSet.getString(7), resultSet.getString(4)));
        }

        mLocations.setValue(mlistLocation);


    }

    public MutableLiveData<List<Location>> getLocations() {
        return mLocations;
    }
}