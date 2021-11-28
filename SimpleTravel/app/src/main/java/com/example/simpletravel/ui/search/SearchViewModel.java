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

    private MutableLiveData<List<Services>> mRecently;
    private List<Services> mrecently;

    private MutableLiveData<List<Location>> mLocation;
    private List<Location> mlocation;

    private Connection connection;
    private JDBCControllers jdbcControllers;

    //    private Statement statement;
    public SearchViewModel() throws SQLException {
        mServices = new MutableLiveData<>();
        InitData();

        mRecently = new MutableLiveData<>();
        DataRecently();

        mLocation = new MutableLiveData<>();
        MListLocation();
    }

    private void DataRecently() {
        run();
        mRecently.setValue(mrecently);
    }
    public MutableLiveData<List<Services>> getRecently(){
        return mRecently;
    }

    private void getDataRecently() {
        try {
            mrecently = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log", "True");
            Statement statement = connection.createStatement();
            String sql = "select S.IdService, S.NameService, S.Summary, St.NameStatus, AVG(R.Ratings) as Ratings ,Count( R.IdService) as Quantity, S.Images\n" +
                    "from Services as S, Ratings as R, Status as St\n" +
                    "where S.IdService = R.IdService and S.IdStatus = St.IdStatus and S.IdLocation = (select top(1) S.IdLocation\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\tfrom Services as S, HistoryServices as H\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\twhere S.IdService = H.IdService\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\torder by H.IdHS Desc)\n" +
                    "group by  S.IdService, S.NameService, S.Summary, St.NameStatus, S.Images";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                mrecently.add(new Services(resultSet.getInt("IdService"), resultSet.getString("NameService"),
                        resultSet.getInt("Ratings"), resultSet.getInt("Quantity"), resultSet.getString("Summary"), "",
                        "", "", resultSet.getString("NameStatus"), "", 0, resultSet.getString("Images"),
                        1.1, 2.1));
            }
        } catch (Exception ex) {
            Log.e("Log", ex.getMessage());
        }
    }

    //get data
    private void MListLocation() {
        try {
            mlocation = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log", "get list 4 item Location");
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

        mLocation.setValue(mlocation);
    }

    public MutableLiveData<List<Location>> getLocation() {
        return mLocation;
    }

    private void InitData() throws SQLException {
        try {
            mlist = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log", "True");
            Statement statement = connection.createStatement();
            String sql = "select IdService, NameService, Address, Images from Services";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                mlist.add(new Services(resultSet.getInt("IdService"), resultSet.getString("NameService"), 0,
                        0, "", "", "", resultSet.getString("Address"), "", "",
                        0, resultSet.getString("Images"),1.1, 1.1));
            }
        } catch (Exception ex) {
            Log.e("Log", ex.getMessage());
        }

        mServices.setValue(mlist);

    }

    public MutableLiveData<List<Services>> getServices() {
        return mServices;
    }

    @Override
    public void run() {
        getDataRecently();

    }
}