package com.example.simpletravel.ui.evaluate;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.model.Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EvaluateViewModel extends ViewModel implements Runnable {

    private MutableLiveData<List<Services>> mSearch;
    private List<Services> msearch;

    private Connection connection;
    private JDBCControllers jdbcControllers;


    public EvaluateViewModel() {
        mSearch = new MutableLiveData<>();
        DataSearch();
    }

    private void DataSearch() {
        run();
        mSearch.setValue(msearch);
    }
    public MutableLiveData<List<Services>> getSearch(){
        return mSearch;
    }

    private void getDataSearch() {
        try {
            msearch = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log", "True");
            Statement statement = connection.createStatement();
            String sql = "select IdService, NameService, Address, Images from Services ";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                msearch.add(new Services(resultSet.getInt("IdService"), resultSet.getString("NameService"), 0,
                        0, "", "","", resultSet.getString("Address"), "", "",
                        0, resultSet.getString("Images"),1.1,1.1));
            }
            connection.close();
            resultSet.close();
            statement.close();
        } catch (Exception ex) {
            Log.e("Log", ex.getMessage());
        }
    }

    @Override
    public void run() {
        getDataSearch();
    }
}