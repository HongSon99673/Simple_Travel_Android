package com.example.simpletravel.asynctask.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.HistoryAdapter;
import com.example.simpletravel.adapter.RecentlyRearchAdapter;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdServices;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.ui.search.DetailsSearchFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecentlyAsyncTask extends AsyncTask<Object, Void, ArrayList<Services>> {
    private List<Services> services;
    private Context context;
    private ListView listView;
    private RecentlyRearchAdapter recentlyRearchAdapter;
    private ProgressDialog progressDialog;

    private Connection connection;
    private JDBCControllers jdbcControllers;

    public RecentlyAsyncTask(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
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
            Log.e("Recently", "True");
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
            Log.e("Recently", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Services> services) {
        super.onPostExecute(services);
        if (services != null) {
            progressDialog.dismiss();//close dialog

            recentlyRearchAdapter = new RecentlyRearchAdapter(context, R.layout.item_evaluate_listview, services);
            listView.setAdapter(recentlyRearchAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    IdServices.IdService = recentlyRearchAdapter.getItem(i).getID();//set Idservice

                    FragmentActivity activity = (FragmentActivity) view.getContext();
                    Fragment myFragment = new DetailsSearchFragment();
                    activity.getSupportFragmentManager().beginTransaction().add(
                            R.id.frameLayout_search, myFragment).addToBackStack(DetailsSearchFragment.TAG1).commit();

                }
            });;

        }
    }
}
