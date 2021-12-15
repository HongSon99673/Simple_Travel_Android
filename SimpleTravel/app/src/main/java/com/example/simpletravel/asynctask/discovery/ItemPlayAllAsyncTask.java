package com.example.simpletravel.asynctask.discovery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.adapter.ItemLocationAdapter;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdLocation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ItemPlayAllAsyncTask extends AsyncTask<Object, Void, ArrayList<Services>> {
    private List<Services> services;
    private Context context;
    private RecyclerView recyclerView;
    private ItemLocationAdapter itemLocationAdapter;
    private ProgressDialog progressDialog;

    //function create call data base
    private Connection connection;
    private JDBCControllers jdbcControllers;

    public ItemPlayAllAsyncTask(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
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
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("ItemPlay", "DataPlay");
            Statement statement = connection.createStatement();
            String sql = "select S.IdService,S.NameService, AVG(R.Ratings) as Ratings ,Count( R.IdService) as Quantity\n" +
                    "                            , S.Summary, S.Phone, S.URL, S.Address, St.NameStatus, St.TimeOpen, S.Latitude, S.Longitude,\n" +
                    "                            S.SuggestTime, S.Images\n" +
                    "                            from Services as S, Ratings as R, Status as St\n" +
                    "                            where S.IdService = R.IdService and S.IdStatus = St.IdStatus and S.IdTS = 2 and S.IdLocation =" + IdLocation.IdLocations + " \n" +
                    "                            group by S.IdService, S.NameService,S.Summary, S.URL, S.Latitude,S.Longitude, \n" +
                    "                            S.Phone, S.SuggestTime,S.Images,St.NameStatus, St.TimeOpen,S.Address";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                services.add(new Services(resultSet.getInt("IdService"), resultSet.getString("NameService"), resultSet.getInt("Ratings"),
                        resultSet.getInt("Quantity"), resultSet.getString("Summary"), resultSet.getString("Phone"),
                        resultSet.getString("URL"), resultSet.getString("Address"), resultSet.getString("NameStatus"),
                        resultSet.getString("TimeOpen"), resultSet.getInt("SuggestTime"), resultSet.getString("Images"), resultSet.getDouble("Latitude"),
                        resultSet.getDouble("Longitude")));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<Services>) services;//return list service

        } catch (Exception ex) {
            Log.e("ItemPlay", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Services> services) {
        super.onPostExecute(services);
        if (services != null) {
            progressDialog.dismiss();//close dialog
            itemLocationAdapter = new ItemLocationAdapter(services);
            recyclerView.setAdapter(itemLocationAdapter);
        }
    }
}

