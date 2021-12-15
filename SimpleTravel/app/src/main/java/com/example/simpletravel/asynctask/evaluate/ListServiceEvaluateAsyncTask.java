package com.example.simpletravel.asynctask.evaluate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.ItemEvaluateAdapter;
import com.example.simpletravel.adapter.ItemSearchAdapter;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdServices;
import com.example.simpletravel.model.Temp.IdUsers;
import com.example.simpletravel.ui.evaluate.EvaluateActivity;
import com.example.simpletravel.ui.search.DetailsSearchFragment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListServiceEvaluateAsyncTask extends AsyncTask<Object, Void, ArrayList<Services>> {
    private List<Services> services;
    private Context context;
    private AutoCompleteTextView autoCompleteTextView;
    private ItemEvaluateAdapter itemEvaluateAdapter;
    private ProgressDialog progressDialog;

    private Connection connection;
    private JDBCControllers jdbcControllers;

    public ListServiceEvaluateAsyncTask(Context context, AutoCompleteTextView autoCompleteTextView) {
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

            itemEvaluateAdapter = new ItemEvaluateAdapter(context, R.layout.item_evaluate_listview, services);
            autoCompleteTextView.setAdapter(itemEvaluateAdapter);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    IdServices.IdService = itemEvaluateAdapter.getItem(i).getID();//set Id Service
                    Intent intent = new Intent(context, EvaluateActivity.class);
                    IdUsers.services = itemEvaluateAdapter.getItem(i);
                    context.startActivity(intent);
                }
            });
        }
    }
}
