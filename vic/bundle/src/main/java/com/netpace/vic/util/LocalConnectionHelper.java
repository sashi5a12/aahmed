package com.netpace.vic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalConnectionHelper {

    private static final String url="jdbc:mysql://localhost:3306/vic";
    private static LocalConnectionHelper instance;

    private LocalConnectionHelper() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (instance == null) {
            instance = new LocalConnectionHelper();
        }
        try {
            return DriverManager.getConnection(LocalConnectionHelper.url, "root", "root");
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
