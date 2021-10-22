package com.example.simpletravel.JDBC;

import java.sql.Connection;

public class JDBCControllers {

    JDBCModel jdbcModel = new JDBCModel();

    public Connection ConnectionData(){
        return jdbcModel.getConnectionOf();
    }
}
