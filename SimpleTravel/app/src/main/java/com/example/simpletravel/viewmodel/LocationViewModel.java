package com.example.simpletravel.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdLocation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationViewModel extends ViewModel implements Runnable {

    private Connection connection;
    private JDBCControllers jdbcControllers;

//    private MutableLiveData<List<Services>> mPlay;
//    private List<Services> mplay;
//
//    private MutableLiveData<List<Services>> mStay;
//    private List<Services> mstay;
//
//    private MutableLiveData<List<Services>> mFood;
//    private List<Services> mfood;

    private MutableLiveData<List<Services>> mCoordinates;
    private List<Services> mcoordinates;

    public LocationViewModel() {
//        mPlay = new MutableLiveData<>();
//
//        mStay = new MutableLiveData<>();
//
//        mFood = new MutableLiveData<>();

        mCoordinates = new MutableLiveData<>();
        DataCoordinates();

    }
    //get coordinate list service show in gg map
    private void DataCoordinates() {
        getDataCoordinates();
        mCoordinates.setValue(mcoordinates);
    }

    private void getDataCoordinates() {
        try {
            mcoordinates = new ArrayList<>();
            jdbcControllers = new JDBCControllers();//open connect
            connection = jdbcControllers.ConnectionData();
            Statement statement = connection.createStatement();
            String sql = "select IdService, NameService, Address, Latitude, Longitude\n" +
                    "from Services \n" +
                    "where IdLocation = '"+IdLocation.IdLocations+"' and IdTS != 4\n" +
                    "group by  IdService, NameService, Address, Latitude, Longitude ";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                mcoordinates.add(new Services(resultSet.getInt(1), resultSet.getString(2), 0,
                        0, "", "", "", resultSet.getString(3), "",
                        "", 0, "", resultSet.getDouble(4), resultSet.getDouble(5)));
            }
            resultSet.close();
            statement.close();
            connection.close();


        } catch (Exception ex){
            Log.e("Get Coordinates", ex.getMessage());
        }
    }

    public MutableLiveData<List<Services>> getCoordinates(){
        return mCoordinates;
    }

//    private void StayData() {
//        run();
//        mStay.setValue(mstay);
//    }
//
//    private void getDataStay() {
//        try {
//            mstay = new ArrayList<>();
//            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
//            connection = jdbcControllers.ConnectionData();
//            Log.e("Log", "DataStay");
//            Statement statement = connection.createStatement();
//            String sql = "select top(4) S.IdService,S.NameService, AVG(R.Ratings) as Ratings ,Count( R.IdService) as Quantity\n" +
//                    "                            , S.Summary, S.Phone, S.URL, S.Address, St.NameStatus, St.TimeOpen,\n" +
//                    "                            S.SuggestTime, S.Images\n" +
//                    "                            from Services as S, Ratings as R, Status as St\n" +
//                    "                            where S.IdService = R.IdService and S.IdStatus = St.IdStatus and S.IdTS = 3 and S.IdLocation ="+ IdLocation.IdLocations +"\n" +
//                    "                            group by S.IdService, S.NameService,S.Summary, S.URL,\n" +
//                    "                            S.Phone, S.SuggestTime,S.Images,St.NameStatus, St.TimeOpen,S.Address ";
//
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                mstay.add(new Services(resultSet.getInt("IdService"), resultSet.getString("NameService"), resultSet.getInt("Ratings"),
//                        resultSet.getInt("Quantity"), resultSet.getString("Summary"), resultSet.getString("Phone"),
//                        resultSet.getString("URL"), resultSet.getString("Address"), resultSet.getString("NameStatus"),
//                        resultSet.getString("TimeOpen"), resultSet.getInt("SuggestTime"), resultSet.getString("Images"),
//                        0123.1,0456.1));
//            }
//            statement.close();
//            connection.close();
//
//        } catch (Exception ex) {
//            Log.e("Log", ex.getMessage());
//        }
//    }
//
//    public MutableLiveData<List<Services>> getStay() {
//        return mStay;
//    }
//
//    private void FoodData() {
//        run();
//        mFood.setValue(mfood);
//    }
//
//    private void getDataFood() {
//        try {
//            mfood = new ArrayList<>();
//            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
//            connection = jdbcControllers.ConnectionData();
//            Log.e("Log", "DataFood");
//            Statement statement = connection.createStatement();
//            String sql = "select top(4) S.IdService,S.NameService, AVG(R.Ratings) as Ratings ,Count( R.IdService) as Quantity\n" +
//                    "                            , S.Summary, S.Phone, S.URL, S.Address, St.NameStatus, St.TimeOpen,  S.Latitude, S.Longitude,\n" +
//                    "                            S.SuggestTime, S.Images\n" +
//                    "                            from Services as S, Ratings as R, Status as St\n" +
//                    "                            where S.IdService = R.IdService and S.IdStatus = St.IdStatus and S.IdTS = 1 and S.IdLocation ="+ IdLocation.IdLocations +"\n" +
//                    "                            group by S.IdService, S.NameService,S.Summary, S.URL,\n" +
//                    "                            S.Phone, S.SuggestTime,S.Images,St.NameStatus, St.TimeOpen,S.Address, S.Latitude, S.Longitude";
//
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                mfood.add(new Services(resultSet.getInt("IdService"), resultSet.getString("NameService"), resultSet.getInt("Ratings"),
//                        resultSet.getInt("Quantity"), resultSet.getString("Summary"), resultSet.getString("Phone"),
//                        resultSet.getString("URL"), resultSet.getString("Address"), resultSet.getString("NameStatus"),
//                        resultSet.getString("TimeOpen"), resultSet.getInt("SuggestTime"), resultSet.getString("Images"),
//                        resultSet.getDouble("Latitude"),resultSet.getDouble("Longitude")));
//            }
//            statement.close();
//            connection.close();
//
//        } catch (Exception ex) {
//            Log.e("Log", ex.getMessage());
//        }
//    }
//
//    public MutableLiveData<List<Services>> getFood() {
//        return mFood;
//    }
//
//    private void PlayData() {
//        run();
//        mPlay.setValue(mplay);
//    }
//
//    private void getDataPlay() {
//        try {
//            mplay = new ArrayList<>();
//            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
//            connection = jdbcControllers.ConnectionData();
//            Log.e("Log", "DataPlay");
//            Statement statement = connection.createStatement();
//            String sql = "select top(4) S.IdService,S.NameService, AVG(R.Ratings) as Ratings ,Count( R.IdService) as Quantity\n" +
//                    "                            , S.Summary, S.Phone, S.URL, S.Address, St.NameStatus, St.TimeOpen, S.Latitude, S.Longitude,\n" +
//                    "                            S.SuggestTime, S.Images\n" +
//                    "                            from Services as S, Ratings as R, Status as St\n" +
//                    "                            where S.IdService = R.IdService and S.IdStatus = St.IdStatus and S.IdTS = 2 and S.IdLocation =" + IdLocation.IdLocations + " \n" +
//                    "                            group by S.IdService, S.NameService,S.Summary, S.URL, S.Latitude,S.Longitude, \n" +
//                    "                            S.Phone, S.SuggestTime,S.Images,St.NameStatus, St.TimeOpen,S.Address";
//
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                mplay.add(new Services(resultSet.getInt("IdService"), resultSet.getString("NameService"), resultSet.getInt("Ratings"),
//                        resultSet.getInt("Quantity"), resultSet.getString("Summary"), resultSet.getString("Phone"),
//                        resultSet.getString("URL"), resultSet.getString("Address"), resultSet.getString("NameStatus"),
//                        resultSet.getString("TimeOpen"), resultSet.getInt("SuggestTime"), resultSet.getString("Images"), resultSet.getDouble("Latitude"),
//                        resultSet.getDouble("Longitude")));
//            }
//            statement.close();
//            connection.close();
//
//        } catch (Exception ex) {
//            Log.e("Log", ex.getMessage());
//        }
//
//    }
//
//    public MutableLiveData<List<Services>> getPlay() {
//        return mPlay;
//    }

    @Override
    public void run() {
        getDataCoordinates();
//        getDataPlay();
//        getDataStay();
//        getDataFood();
    }
}
