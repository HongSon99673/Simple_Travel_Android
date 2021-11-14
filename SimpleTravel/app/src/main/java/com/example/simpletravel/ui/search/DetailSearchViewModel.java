package com.example.simpletravel.ui.search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.model.Comment;
import com.example.simpletravel.model.IdServices;
import com.example.simpletravel.model.IdUsers;
import com.example.simpletravel.model.Location;
import com.example.simpletravel.model.Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetailSearchViewModel extends ViewModel {

    private MutableLiveData<List<Services>> mServices;
    private List<Services> mlist;
    private Connection connection;
    private JDBCControllers jdbcControllers;
    private Statement statement;


    public DetailSearchViewModel() throws SQLException {
        mServices = new MutableLiveData<>();
        InitData();

        mConmment = new MutableLiveData<>();
        ListComment();


    }

    //create variable IdService
    private int IdService;

    private void InitData() throws SQLException {

        IdService = IdServices.IdService;
        mlist = new ArrayList<>();
        jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
        connection = jdbcControllers.ConnectionData();
        Log.e("Log","True");
        statement = connection.createStatement();

        String sql = "select Services.IdService,Services.NameService, AVG(Ratings.Ratings) as Ratings ,Count( Ratings.IdService) as Quantity \n" +
                "                                 , Services.Summary, Services.Phone, Services.URL, Services.Address, Status.NameStatus, Status.TimeOpen, \n" +
                "                                Services.SuggestTime, Services.Images\n" +
                "                                 from Services, Ratings, Status\n" +
                "                                 where Services.IdService = Ratings.IdService and Services.IdStatus = Status.IdStatus and Services.IdService = '"+ IdService +"'\n" +
                "                                 group by Services.IdService, Services.NameService,Services.Summary, Services.URL,\n" +
                "                                 Services.Phone, Services.SuggestTime,Services.Images,Status.NameStatus, Status.TimeOpen,Services.Address";
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


    //create variable
    private MutableLiveData<List<Comment>> mConmment;
    private List<Comment> mlistComment;

    private void ListComment() throws SQLException{

        mlistComment = new ArrayList<>();
        jdbcControllers = new JDBCControllers();
        connection = jdbcControllers.ConnectionData();
        Log.e("Log", "True");
        statement = connection.createStatement();

        String comment = "select Ratings.IdRating, Users.Name,COUNT (Ratings.IdUser ) as Contribute, AVG(Ratings.Ratings) as Evaluate, Ratings.Title, Ratings.Time, Ratings.Type, Ratings.Summary, Ratings.Likes\n" +
                "from Users, Ratings, Services where Users.IdUser = Ratings.IdUser and Services.IdService = Ratings.IdService and Services.IdService = '"+ IdService + "'\n" +
                "group by Ratings.IdRating, Users.Name, Ratings.Title, Ratings.Time, Ratings.Type, Ratings.Summary, Ratings.Likes, Ratings.IdUser" ;
        ResultSet resultSet = statement.executeQuery(comment);
        while (resultSet.next()){
            mlistComment.add(new Comment(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4),resultSet.getString(5),
                    resultSet.getString(6), resultSet.getString(7), resultSet.getString(8),resultSet.getInt(9)));
        }

        mConmment.setValue(mlistComment);


    }

    public MutableLiveData<List<Comment>> getConmment(){
        return mConmment;
    }
}
