package com.example.simpletravel.asynctask.planning;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.adapter.TripAdapter;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.model.Trip;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TripAsyncTask extends AsyncTask<Object, Void, ArrayList<Trip>> {
    private List<Trip> trips;
    private Context context;
    private JDBCControllers jdbcControllers;
    private Connection connection;
    private Statement statement;
    private ProgressDialog progressDialog;
    private TripAdapter tripAdapter;
    private RecyclerView recyclerView;

    public TripAsyncTask(Context context, RecyclerView recyclerView) {
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
    protected ArrayList<Trip> doInBackground(Object... objects) {
        try {
            trips = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Trip","True");
            statement = connection.createStatement();

            String sql = "select Planning.IdPlan, Planning.NamePlan, COUNT (DetailsPlanning.IdPlan) as SaveItem \n" +
                    "from Planning, DetailsPlanning \n" +
                    "where Planning.IdPlan = DetailsPlanning.IdPlan and Planning.IdUser = '"+IdUsers.IdUser +"' \n" +
                    "group by Planning.IdPlan, Planning.NamePlan";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                trips.add(new Trip(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3)));
            }
            //close connect to data base
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<Trip>) trips; //return list trip
        } catch (Exception ex){
            Log.e("Trip", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Trip> trips) {
        super.onPostExecute(trips);
        if (trips != null){
            progressDialog.dismiss();
            tripAdapter = new TripAdapter(trips);
            recyclerView.setAdapter(tripAdapter);
        }
    }
}
