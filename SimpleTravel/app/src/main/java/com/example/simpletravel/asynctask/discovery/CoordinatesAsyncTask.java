package com.example.simpletravel.asynctask.discovery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesAsyncTask extends AsyncTask<Object, Void, ArrayList<Services>> {
    private List<Services> services;
    private Context context;
    private MapView mapView;
    private GoogleMap googleMap;
    private Marker marker;
    private ProgressDialog progressDialog;
    private Connection connection;
    private JDBCControllers jdbcControllers;

    public CoordinatesAsyncTask(Context context, GoogleMap googleMap, MapView mapView) {
        this.context = context;
        this.googleMap = googleMap;
        this.mapView = mapView;
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
            jdbcControllers = new JDBCControllers();//open connect
            connection = jdbcControllers.ConnectionData();
            Log.e("Get Coordinates", "true");
            Statement statement = connection.createStatement();
            String sql = "select IdService, NameService, Address, Latitude, Longitude\n" +
                    "from Services \n" +
                    "where IdLocation = '"+ IdLocation.IdLocations+"' and IdTS != 4\n" +
                    "group by  IdService, NameService, Address, Latitude, Longitude ";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                services.add(new Services(resultSet.getInt(1), resultSet.getString(2), 0,
                        0, "", "", "", resultSet.getString(3), "",
                        "", 0, "", resultSet.getDouble(4), resultSet.getDouble(5)));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<Services>) services;//return list service
        } catch (Exception ex){
            Log.e("Get Coordinates", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Services> services) {
        super.onPostExecute(services);
        if (services != null){
            progressDialog.dismiss();
            for (int i = 0; i < services.size(); i++) {
                Services s = (Services) services.get(i);
                //create marker
                marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(s.getLatitude(), s.getLongitude()))
                        .title(s.getName())
                        .snippet(s.getAddress())
                );
                //Zoom
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLatitude(), s.getLongitude()), 10));
            }
        }
    }
}
