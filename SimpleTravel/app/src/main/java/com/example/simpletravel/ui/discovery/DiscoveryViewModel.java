package com.example.simpletravel.ui.discovery;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.model.Temp.IdLocation;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DiscoveryViewModel extends ViewModel implements Runnable {

    private MutableLiveData<List<Services>> mServices;
    private List<Services> mlist;
    private Connection connection;
    private JDBCControllers jdbcControllers;

    private MutableLiveData<List<Location>> mLocations;
    private List<Location> mlistLocation;

    public DiscoveryViewModel() throws SQLException {
        mServices = new MutableLiveData<>();
        InitData();
        mLocations = new MutableLiveData<>();
        HotelData();
    }


    private void InitData() throws SQLException {
        getDataInit();
        mServices.setValue(mlist);
    }

    private void getDataInit() {
        try {
            mlist = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log", "True");
            Statement statement = connection.createStatement();
            String sql = " select top(4) S.IdService,S.NameService, AVG(R.Ratings) as Ratings ,Count( R.IdService) as Quantity\n" +
                    "                    , S.Summary, S.Phone, S.URL, S.Address, St.NameStatus, St.TimeOpen,S.SuggestTime, S.Images,\n" +
                    "                    S.Latitude, S.Longitude\n" +
                    "            from Services as S, Ratings as R, HistoryServices as H, Users as U, Status as St\n" +
                    "            where S.IdService = R.IdService and S.IdService = H.IdService\n" +
                    "            and S.IdStatus = St.IdStatus and H.IdUser = U.IdUser and U.IdUser = '" + IdUsers.IdUser + "'\n" +
                    "            group by S.IdService, S.NameService,S.Summary, S.URL,H.IdHS,S.Latitude, S.Longitude,\n" +
                    "                    S.Phone, S.SuggestTime,S.Images,St.NameStatus, St.TimeOpen,S.Address\n" +
                    "            order by H.IdHS DESC";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                mlist.add(new Services(resultSet.getInt("IdService"), resultSet.getString(2), resultSet.getInt(3),
                        resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12),
                        resultSet.getDouble(13), resultSet.getDouble(14)));
            }
            statement.close();
            connection.close();

        } catch (Exception ex) {
            Log.e("Log", ex.getMessage());
        }
    }

    public MutableLiveData<List<Services>> getServices() {
        return mServices;
    }

    private void HotelData() throws SQLException {
        getDataLocation();
        mLocations.setValue(mlistLocation);
    }

    private void getDataLocation() {
        try {
            mlistLocation = new ArrayList<>();
            jdbcControllers = new JDBCControllers();
            connection = jdbcControllers.ConnectionData();
            Log.e("Log", "True");
            Statement statement = connection.createStatement();
            String hotel = "SELECT * FROM Location, Country Where Location.IdCountry = Country.IdCountry ";
            ResultSet resultSet = statement.executeQuery(hotel);
            while (resultSet.next()) {
                mlistLocation.add(new Location(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(6), resultSet.getString(7), resultSet.getString(4)));
            }
            statement.close();
            connection.close();
        } catch (Exception ex) {
            Log.e("Log", ex.getMessage());
        }
    }
    public MutableLiveData<List<Location>> getLocations() {
        return mLocations;
    }


    @Override
    public void run() {
        getDataInit();
        getDataLocation();
    }
}