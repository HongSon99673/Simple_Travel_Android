package com.example.simpletravel.JDBC;


import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@SuppressLint("NewApi")
public class JDBCModel {

    public Connection getConnectionOf(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection objConn = null;
        String ConnURL = null;

        JDBCObjects jdbcObjects = new JDBCObjects("113.163.166.22","sa","123",
                "SimpleTravel","1433");

        try {
            Class.forName(jdbcObjects.getTable());
            ConnURL = "jdbc:jtds:sqlserver://"
                    + jdbcObjects.getServerName() + ":" + jdbcObjects.getPort() + ";"
                    + "databaseName=" + jdbcObjects.getDatabase()
                    + ";user=" + jdbcObjects.getUserId()
                    + ";password=" +jdbcObjects.getPassword() + ";";
            objConn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }

        return objConn;
    }
}
