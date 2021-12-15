package com.example.simpletravel.asynctask.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.ItemSearchAdapter;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdServices;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.ui.search.DetailsSearchFragment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListServiceAsyncTask extends AsyncTask<Object, Void, ArrayList<Services>> {
    private List<Services> services;
    private Context context;
    private AutoCompleteTextView autoCompleteTextView;
    private ItemSearchAdapter itemSearchAdapter;
    private ProgressDialog progressDialog;

    private Connection connection;
    private JDBCControllers jdbcControllers;

    public ListServiceAsyncTask(Context context, AutoCompleteTextView autoCompleteTextView) {
        this.context = context;
        this.autoCompleteTextView = autoCompleteTextView;
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
            Log.e("ListService", "True");
            Statement statement = connection.createStatement();
            String sql = "select IdService, NameService, Address, Images from Services";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                services.add(new Services(resultSet.getInt("IdService"), resultSet.getString("NameService"), 0,
                        0, "", "", "", resultSet.getString("Address"), "", "",
                        0, resultSet.getString("Images"),1.1, 1.1));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return (ArrayList<Services>) services;//return list services

        } catch (Exception ex) {
            Log.e("ListService", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Services> services) {
        super.onPostExecute(services);
        if (services != null){
            progressDialog.dismiss();//close dialog
            itemSearchAdapter = new ItemSearchAdapter(context, R.layout.item_fragment_item_search, services);
            autoCompleteTextView.setAdapter(itemSearchAdapter);

            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //send id service to Fragment Details
                    int idService = itemSearchAdapter.getItem(i).getID();
                    IdServices.IdService = idService;
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                                connection = jdbcControllers.ConnectionData();
                                Log.e("Log", "Connect true");
                                String sql = "Insert into HistoryServices (IdService, IdUser) values " +
                                        "('"+ idService + "','"+ IdUsers.IdUser+"')";
                                PreparedStatement preparedStatement = connection
                                        .prepareStatement(sql);
                                preparedStatement.executeUpdate();
                                preparedStatement.close();
                            } catch (Exception ex) {
                                Log.e("Log", ex.toString());
                            }
                        }
                    });
                    thread.start();

                    FragmentActivity activity = (FragmentActivity) view.getContext();
                    Fragment myFragment = new DetailsSearchFragment();
                    activity.getSupportFragmentManager().beginTransaction().add(
                            R.id.frameLayout_search, myFragment).addToBackStack(DetailsSearchFragment.TAG1).commit();
                }

            });
        }
    }
}
