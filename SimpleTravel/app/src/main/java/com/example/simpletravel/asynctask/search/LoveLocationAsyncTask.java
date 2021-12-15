package com.example.simpletravel.asynctask.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.adapter.LocationAdapter;
import com.example.simpletravel.model.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoveLocationAsyncTask extends AsyncTask<Object, Void, ArrayList<Location>> {
    private List<Location> locations;
    private Context context;
    private GridView gridView;
    private LocationAdapter locationAdapter;
    private ProgressDialog progressDialog;

    private Connection connection;
    private JDBCControllers jdbcControllers;

    public LoveLocationAsyncTask(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
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
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("LoveLocation", "true");
            Statement statement = connection.createStatement();
            String sql = "select top 4 S.IdService,S.NameService, S.Address ,S.Images, COUNT(R.IdService) as NORating, AVG(R.Ratings) as AVGRating\n" +
                    "from Ratings as R, Services as S\n" +
                    "where S.IdService = R.IdService and S.IdTS = 4\n" +
                    "group by S.IdService,S.NameService, S.Address, S.Images\n" +
                    "having AVG(R.Ratings) >= 4\n" +
                    "order by NORating desc";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                locations.add(new Location(resultSet.getInt("IdService"), resultSet.getString("NameService"), "", "",
                        resultSet.getString("Images")));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<Location>) locations; //return list location
        } catch (Exception ex) {
            Log.e("LoveLocation", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Location> locations) {
        super.onPostExecute(locations);
        if (locations != null){
            progressDialog.dismiss();//close dialog

            locationAdapter = new LocationAdapter(locations);
            gridView.setAdapter(locationAdapter);
        }
    }
}
