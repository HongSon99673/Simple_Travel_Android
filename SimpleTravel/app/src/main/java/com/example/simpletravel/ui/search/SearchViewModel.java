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

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Services>> mServices;
    private List<Services> mlist;
    private Connection connection;
    private JDBCControllers jdbcControllers;
//    private Statement statement;


    public SearchViewModel() throws SQLException {
        mServices = new MutableLiveData<>();
        InitData();

    }

    private void InitData() throws SQLException {
        mlist = new ArrayList<>();
        jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
        connection = jdbcControllers.ConnectionData();
        Log.e("Log","True");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                try{
                    Statement  statement = connection.createStatement();
                    String sql = "select Services.IdService,Services.NameService, AVG(Ratings.Ratings) as Ratings ,Count( Ratings.IdService) as Quantity \n" +
                            "                 , Services.Summary, Services.Phone, Services.URL, Services.Address, Status.NameStatus, Status.TimeOpen, \n" +
                            "                 Services.SuggestTime, Services.Images\n" +
                            "                 from Services, Ratings, Status\n" +
                            "                 where Services.IdService = Ratings.IdService \n" +
                            "                 and Services.IdStatus = Status.IdStatus \n" +
                            "                 group by Services.IdService, Services.NameService,Services.Summary, Services.URL,\n" +
                            "                 Services.Phone, Services.SuggestTime,Services.Images,Status.NameStatus, Status.TimeOpen,Services.Address";
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){

                        mlist.add(new Services(resultSet.getInt("IdService"),resultSet.getString(2),resultSet.getInt(3),
                                resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6), resultSet.getString(7),
                                resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12)));
                    }
                } catch (Exception ex){
                    Log.e("Log", ex.getMessage());
                }
//            }
//        }).start();
        mServices.setValue(mlist);

    }

    public MutableLiveData<List<Services>> getServices() {
        return mServices;
    }
}