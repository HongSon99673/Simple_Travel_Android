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
    private Statement statement;


    public SearchViewModel() throws SQLException {
        mServices = new MutableLiveData<>();
        InitData();

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
}