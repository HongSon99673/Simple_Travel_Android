package com.example.simpletravel.JDBC;

import java.sql.Connection;

public class UserModel {

    private JDBCControllers jdbcControllers ;
    private Connection connection;

    public UserModel(Connection connection) {
        this.connection = connection;
    }
}
