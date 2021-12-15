package com.example.simpletravel.asynctask.planning;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.adapter.SavedItemAdapter;
import com.example.simpletravel.model.SavedItem;
import com.example.simpletravel.model.Temp.IdTrip;
import com.example.simpletravel.model.Temp.IdUsers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetailTripAsyncTask extends AsyncTask<Object, Void, ArrayList<SavedItem>> {
    private List<SavedItem> savedItemList;
    private Context context;
    private JDBCControllers jdbcControllers;
    private Connection connection;
    private Statement statement;
    private ProgressDialog progressDialog;
    private SavedItemAdapter savedItemAdapter;
    private RecyclerView recyclerView;

    public DetailTripAsyncTask(Context context, RecyclerView recyclerView) {
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
    protected ArrayList<SavedItem> doInBackground(Object... objects) {
        try {
            savedItemList = new ArrayList<>();
            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
            connection = jdbcControllers.ConnectionData();
            Log.e("Log","True");
            statement = connection.createStatement();

            String sql = "select a.IdService,c.TypeName, b.NameService, AVG(g.Ratings) as Ratings ,b.Summary, d.TimeOpen, e.NamePlan,b.Latitude, b.Longitude, b.Images\n" +
                    "from DetailsPlanning a , Services b, TypeService c, Status d , Planning e, Ratings g\n" +
                    "where a.IdService = b.IdService and b.IdTS = c.IdTS  and b.IdStatus = d.IdStatus and b.IdService = g.IdService\n" +
                    "and a.IdPlan = e.IdPlan and e.IdPlan = '"+ IdTrip.IdTrips +"' and e.IdUser = '"+IdUsers.IdUser+"'\n" +
                    "group by  a.IdService,c.TypeName, b.NameService ,b.Summary, d.TimeOpen, e.NamePlan, b.Latitude, b.Longitude, b.Images";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){

                savedItemList.add(new SavedItem(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),
                        resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getInt(1),
                        resultSet.getDouble(8), resultSet.getDouble(9), resultSet.getString(10) ));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<SavedItem>) savedItemList; //return all service in trip this
        } catch (Exception ex){
            Log.e("Log", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<SavedItem> savedItems) {
        super.onPostExecute(savedItems);
        if (savedItems != null){
            progressDialog.dismiss();
            savedItemAdapter = new SavedItemAdapter(savedItems);
            recyclerView.setAdapter(savedItemAdapter);
        }
    }
}
