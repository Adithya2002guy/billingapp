package com.zoho.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/billing_db";
    private static final String DB_USER = "adithya-tt0632";
    private static final String DB_PASSWORD = "Adithyasent@2002";
    private static DBUtil instance;
    private DBUtil() {}

    public static synchronized DBUtil getInstance(){
        if(instance == null){
            instance = new DBUtil();
        }
        return instance;
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing the connection" + e.getMessage());
            }
        }
    }
}
