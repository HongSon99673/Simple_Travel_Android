package com.example.simpletravel.ui.planning;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.model.Temp.IdTrip;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.ListTrip;
import com.example.simpletravel.model.SavedItem;
import com.example.simpletravel.model.Trip;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlanningViewModel extends ViewModel implements Runnable {

    //create variable
    private Connection connection;
    private JDBCControllers jdbcControllers;
    private Statement statement;
    //create variable IdService
    private int IdUser;

    private MutableLiveData<List<Trip>> mTrip;
    private List<Trip> mlist;

    private MutableLiveData<List<SavedItem>> mSavedItem;
    private List<SavedItem> savedItemList;

    private MutableLiveData<List<ListTrip>> mListTrip;
    private List<ListTrip> listTrip;

    private MutableLiveData<List<SavedItem>> mItemList;
    private List<SavedItem> listItem;

    public PlanningViewModel() throws SQLException {

        mTrip = new MutableLiveData<>();
        InitData();

        mSavedItem = new MutableLiveData<>();
        SavedItemData();

        mListTrip = new MutableLiveData<>();
        ListPlan();

        mItemList = new MutableLiveData<>();
        ListItem();

    }

    //create data list item
    private void ListItem() {
        run();
        mItemList.setValue(listItem);
    }

    public MutableLiveData<List<SavedItem>> getListItem (){
        return mItemList;
    }

    //call data from SQL server
    private void SavedItemData(){
        run();
        mSavedItem.setValue(savedItemList);

    }
    public MutableLiveData<List<SavedItem>> getSavedItem(){
        return mSavedItem;
    }

    private void InitData() throws SQLException {
        run();
        mTrip.setValue(mlist);

    }

   public MutableLiveData<List<Trip>> getTrip(){
        return mTrip;
   }

    private void ListPlan() throws SQLException {
        run();
        mListTrip.setValue(listTrip);

    }

    public MutableLiveData<List<ListTrip>> getListTrip(){
        return mListTrip;
    }
    @Override
    public void run() {
        getDataItem();
        getDataInit();//get all trip
        getDataListPlan();
        getDataItemList();//get item list details
    }

    private void getDataItemList() {
        try {
            IdUser = IdUsers.IdUser;
            listItem = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log","True");
            statement = connection.createStatement();

            String sql = "select a.IdService,c.TypeName, b.NameService, AVG(g.Ratings) as Ratings ,b.Summary, d.TimeOpen, e.NamePlan\n" +
                    "from DetailsPlanning a , Services b, TypeService c, Status d , Planning e, Ratings g\n" +
                    "where a.IdService = b.IdService and b.IdTS = c.IdTS  and b.IdStatus = d.IdStatus and b.IdService = g.IdService\n" +
                    "and a.IdPlan = e.IdPlan and e.IdPlan = '"+ IdTrip.IdTrips +"' and e.IdUser = '"+ IdUser +"'\n" +
                    "group by  a.IdService,c.TypeName, b.NameService ,b.Summary, d.TimeOpen, e.NamePlan";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){

                listItem.add(new SavedItem(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),
                        resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getInt(1)));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception ex){
            Log.e("Log", ex.getMessage());
        }
    }

    private void getDataListPlan() {
        try {
            IdUser = IdUsers.IdUser;
            listTrip = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log","True");
            statement = connection.createStatement();
            String sql = "select IdPlan, NamePlan from Planning\n" +
                    "where IdUser = '"+ IdUser +"'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){

                listTrip.add(new ListTrip(resultSet.getInt(1),resultSet.getString(2),false));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception ex){
            Log.e("Log", ex.getMessage());
        }

    }

    private void getDataInit() {
        try {
            IdUser = IdUsers.IdUser;
            mlist = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log","True");
            statement = connection.createStatement();

            String sql = "select Planning.IdPlan, Planning.NamePlan, COUNT (DetailsPlanning.IdPlan) as SaveItem \n" +
                    "from Planning, DetailsPlanning \n" +
                    "where Planning.IdPlan = DetailsPlanning.IdPlan and Planning.IdUser = '"+ IdUser +"' \n" +
                    "group by Planning.IdPlan, Planning.NamePlan";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                mlist.add(new Trip(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3)));
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception ex){
            Log.e("Log", ex.getMessage());
        }
    }

    private void getDataItem() {
        try {
            IdUser = IdUsers.IdUser;
            savedItemList = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log","True");
            statement = connection.createStatement();

            String sql = "select a.IdService,c.TypeName, b.NameService, AVG(g.Ratings) as Ratings ,b.Summary, d.TimeOpen, e.NamePlan\n" +
                    "from DetailsPlanning a , Services b, TypeService c, Status d , Planning e, Ratings g\n" +
                    "where a.IdService = b.IdService and b.IdTS = c.IdTS  and b.IdStatus = d.IdStatus and b.IdService = g.IdService\n" +
                    "and a.IdPlan = e.IdPlan and e.IdUser = '"+ IdUser +"'\n" +
                    "group by  a.IdService,c.TypeName, b.NameService ,b.Summary, d.TimeOpen, e.NamePlan";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){

                savedItemList.add(new SavedItem(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),
                        resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getInt(1)));
            }

        } catch (Exception ex){
            Log.e("Log", ex.getMessage());
        }

    }
}