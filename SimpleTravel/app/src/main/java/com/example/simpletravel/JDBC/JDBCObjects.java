package com.example.simpletravel.JDBC;


import java.io.Serializable;

public class JDBCObjects implements Serializable {
    private String ServerName;
    private String UserId;
    private String Password;
    private String Database;
    private String Table;
    private String Port;

    public JDBCObjects() {
    }

    public JDBCObjects(String serverName, String userId,
                       String password, String database, String port) {
        this.ServerName = serverName;
        this.UserId = userId;
        this.Password = password;
        this.Database = database;
        this.Table = "net.sourceforge.jtds.jdbc.Driver";
        this.Port = port;
    }

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDatabase() {
        return Database;
    }

    public void setDatabase(String database) {
        Database = database;
    }


    public String getTable() {
        return Table;
    }

    public void setTable(String table) {
        Table = table;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }
}
