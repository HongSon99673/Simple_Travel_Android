package com.example.simpletravel.ui.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel implements Runnable {

    private MutableLiveData<List<Services>> mServices;
    private List<Services> mlist;

    private MutableLiveData<List<Location>> mLocation;
    private List<Location> mlocation;

    private Connection connection;
    private JDBCControllers jdbcControllers;

    //    private Statement statement;
    public SearchViewModel() throws SQLException {
        mServices = new MutableLiveData<>();
        InitData();

        mLocation = new MutableLiveData<>();
        MListLocation();
    }

    //get data
    private void MListLocation() {
        mlocation = new ArrayList<>();
        jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
        connection = jdbcControllers.ConnectionData();
        Log.e("Log", "get list 4 item Location");
        //        new Thread(new Runnable() {
//            @Override
//            public void run() {
        try {
            Statement statement = connection.createStatement();
            String sql = "select top 4 S.IdService,S.NameService, S.Address ,S.Images, COUNT(R.IdService) as NORating, AVG(R.Ratings) as AVGRating\n" +
                    "from Ratings as R, Services as S\n" +
                    "where S.IdService = R.IdService and S.IdTS = 4\n" +
                    "group by S.IdService,S.NameService, S.Address, S.Images\n" +
                    "having AVG(R.Ratings) >= 4";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                mlocation.add(new Location(resultSet.getInt("IdService"), resultSet.getString("NameService"), "", "",
                        resultSet.getString("Images")));
            }
        } catch (Exception ex) {
            Log.e("Log", ex.getMessage());
        }
//            }
//        }).start();
        mLocation.setValue(mlocation);
    }

    public MutableLiveData<List<Location>> getLocation() {
        return mLocation;
    }

    private void InitData() throws SQLException {
        mlist = new ArrayList<>();
        jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
        connection = jdbcControllers.ConnectionData();
        Log.e("Log", "True");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement statement = connection.createStatement();
                    String sql = "select top(4) S.IdService,S.NameService, AVG(R.Ratings) as Ratings ,Count( R.IdService) as Quantity\n" +
                            ", S.Summary, S.Phone, S.URL, S.Address, St.NameStatus, St.TimeOpen, S.SuggestTime, S.Images\n" +
                            "from Services as S, Ratings as R, Status as St\n" +
                            "where S.IdService = R.IdService and S.IdStatus = St.IdStatus \n" +
                            "and S.IdLocation = (select top(1) S.IdLocation \n" +
                            "from HistoryServices as H, Services as S \n" +
                            "where S.IdService = H.IdService\n" +
                            "order by H.IdHS DESC)\n" +
                            "group by S.IdService, S.NameService,S.Summary, S.URL,S.Phone, S.SuggestTime,S.Images,St.NameStatus, St.TimeOpen, S.Address";
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()) {

                        mlist.add(new Services(resultSet.getInt("IdService"), resultSet.getString(2), resultSet.getInt(3),
                                resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                                resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12)));
                    }
                } catch (Exception ex) {
                    Log.e("Log", ex.getMessage());
                }
            }
        }).start();
        mServices.setValue(mlist);

    }

    public MutableLiveData<List<Services>> getServices() {
        return mServices;
    }

    @Override
    public void run() {

    }
}