package com.example.simpletravel.asynctask.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdServices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetailsInforAsyncTask extends AsyncTask<Object, Void, ArrayList<Services>> {
    private List<Services> services;
    private Context context;
    private ProgressDialog progressDialog;
    private Connection connection;
    private JDBCControllers jdbcControllers;
    private  Statement statement;

    public DetailsInforAsyncTask(List<Services> services, Context context) {
        this.services = services;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Vui lòng chờ");
        progressDialog.setMessage("Đang tải dữ liệu ....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }



    @Override
    protected ArrayList<Services> doInBackground(Object... objects) {
        try {
           services = new ArrayList<>();
           jdbcControllers = new JDBCControllers();
           connection = jdbcControllers.ConnectionData();
            Log.e("Recently","True");
            statement = connection.createStatement();
            String sql = "select Services.IdService,Services.NameService, AVG(Ratings.Ratings) as Ratings ,Count( Ratings.IdService) as Quantity \n" +
                    "                                 , Services.Summary, Services.Phone, Services.URL, Services.Address, Status.NameStatus, Status.TimeOpen, \n" +
                    "                                Services.SuggestTime, Services.Images, Services.Latitude,Services.Longitude \n" +
                    "                                 from Services, Ratings, Status\n" +
                    "                                 where Services.IdService = Ratings.IdService and Services.IdStatus = Status.IdStatus and Services.IdService = '"+IdServices.IdService+"'\n" +
                    "                                 group by Services.IdService, Services.NameService,Services.Summary, Services.URL,Services.Latitude,Services.Longitude,\n" +
                    "                                 Services.Phone, Services.SuggestTime,Services.Images,Status.NameStatus, Status.TimeOpen,Services.Address";
           ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){

                services.add(new Services(resultSet.getInt("IdService"),resultSet.getString(2),resultSet.getInt(3),
                        resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12),
                        resultSet.getDouble(13), resultSet.getDouble(14)));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<Services>) services;// return all service

        } catch (Exception ex){
            Log.e("Detail", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Services> services) {
        super.onPostExecute(services);
        if (services != null) {
            progressDialog.dismiss();//close dialog
            this.services = services;
        }
    }
}
