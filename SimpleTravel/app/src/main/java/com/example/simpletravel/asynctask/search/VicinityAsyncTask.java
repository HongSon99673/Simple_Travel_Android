package com.example.simpletravel.asynctask.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.adapter.VinicityLocationAdapter;
import com.example.simpletravel.model.Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VicinityAsyncTask extends AsyncTask<Object, Void, ArrayList<Services>> {
    private List<Services> services;
    private Context context;
    private RecyclerView recyclerView;
    private VinicityLocationAdapter vinicityLocationAdapter;
    private ProgressDialog progressDialog;

    private Connection connection;
    private JDBCControllers jdbcControllers;

    public VicinityAsyncTask(Context context, RecyclerView recyclerView) {
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
            Log.e("Log", "True");
            Statement statement = connection.createStatement();
            String sql = "select top(4) S.IdService, S.NameService, S.Summary, St.NameStatus, AVG(R.Ratings) as Ratings ,Count( R.IdService) as Quantity, S.Images\n" +
                    "from Services as S, Ratings as R, Status as St\n" +
                    "where S.IdService = R.IdService and S.IdStatus = St.IdStatus and S.IdLocation = (select top(1) S.IdLocation\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\tfrom Services as S, HistoryServices as H\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\twhere S.IdService = H.IdService\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\torder by H.IdHS Desc)\n" +
                    "group by  S.IdService, S.NameService, S.Summary, St.NameStatus, S.Images";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                services.add(new Services(resultSet.getInt("IdService"), resultSet.getString("NameService"),
                        resultSet.getInt("Ratings"), resultSet.getInt("Quantity"), resultSet.getString("Summary"), "",
                        "", "", resultSet.getString("NameStatus"), "", 0, resultSet.getString("Images"),
                        1.1, 2.1));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<Services>) services;//return array list service

        } catch (Exception ex) {
            Log.e("Recently", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Services> services) {
        super.onPostExecute(services);
        if (services != null){
            progressDialog.dismiss();//close dialog

            vinicityLocationAdapter = new VinicityLocationAdapter(services);
            recyclerView.setAdapter(vinicityLocationAdapter);
        }
    }
}
