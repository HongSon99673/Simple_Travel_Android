package com.example.simpletravel.asynctask.discovery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.adapter.ViewTravelAdapter;
import com.example.simpletravel.model.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationAsyncTask extends AsyncTask<Object,Void, ArrayList<Location>> {
    private List<Location> locations;
    private Context context;
    private RecyclerView recyclerView;
    private ViewTravelAdapter viewTravelAdapter;
    private ProgressDialog progressDialog;

    private Connection connection;
    private JDBCControllers jdbcControllers;

    public LocationAsyncTask(Context context, RecyclerView recyclerView) {
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
    protected ArrayList<Location> doInBackground(Object... objects) {
        try {
            locations = new ArrayList<>();
            jdbcControllers = new JDBCControllers();
            connection = jdbcControllers.ConnectionData();
            Log.e("ViewTravel", "True");
            Statement statement = connection.createStatement();
            String hotel = "SELECT * FROM Location, Country Where Location.IdCountry = Country.IdCountry ";
            ResultSet resultSet = statement.executeQuery(hotel);
            while (resultSet.next()) {
                locations.add(new Location(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(6), resultSet.getString(7), resultSet.getString(4)));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<Location>) locations;
        } catch (Exception ex) {
            Log.e("ViewTravel", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Location> locations) {
        super.onPostExecute(locations);
        if (locations != null){
            progressDialog.dismiss();
            viewTravelAdapter = new ViewTravelAdapter(locations);
            recyclerView.setAdapter(viewTravelAdapter);
        }

    }
}
