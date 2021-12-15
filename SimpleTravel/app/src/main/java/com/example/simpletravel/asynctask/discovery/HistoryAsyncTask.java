package com.example.simpletravel.asynctask.discovery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.HistoryAdapter;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdUsers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HistoryAsyncTask extends AsyncTask<Object, Void, ArrayList<Services>> {
    private List<Services> services;
    private Context context;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private ProgressDialog progressDialog;

    private Connection connection;
    private JDBCControllers jdbcControllers;

    public HistoryAsyncTask(Context context, RecyclerView recyclerView) {
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
            Log.e("HistoryView", "True");
            Statement statement = connection.createStatement();
            String sql = " select top(4) S.IdService,S.NameService, AVG(R.Ratings) as Ratings ,Count( R.IdService) as Quantity\n" +
                    "                    , S.Summary, S.Phone, S.URL, S.Address, St.NameStatus, St.TimeOpen,S.SuggestTime, S.Images,\n" +
                    "                    S.Latitude, S.Longitude\n" +
                    "            from Services as S, Ratings as R, HistoryServices as H, Users as U, Status as St\n" +
                    "            where S.IdService = R.IdService and S.IdService = H.IdService\n" +
                    "            and S.IdStatus = St.IdStatus and H.IdUser = U.IdUser and U.IdUser = '" + IdUsers.IdUser + "'\n" +
                    "            group by S.IdService, S.NameService,S.Summary, S.URL,H.IdHS,S.Latitude, S.Longitude,\n" +
                    "                    S.Phone, S.SuggestTime,S.Images,St.NameStatus, St.TimeOpen,S.Address\n" +
                    "            order by H.IdHS DESC";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                services.add(new Services(resultSet.getInt("IdService"), resultSet.getString(2), resultSet.getInt(3),
                        resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12),
                        resultSet.getDouble(13), resultSet.getDouble(14)));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<Services>) services;

        } catch (Exception ex) {
            Log.e("HistoryView", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Services> services) {
        super.onPostExecute(services);
        if (services != null) {
            progressDialog.dismiss();//close dialog
            historyAdapter = new HistoryAdapter(context, services);
            recyclerView.setAdapter(historyAdapter);

        }
    }
}
