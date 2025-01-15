package com.zoho.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/billing_db";
    private static final String DB_USER = "adithya-tt0632";
    private static final String DB_PASSWORD = "Adithyasent@2002";
    private static final String DRIVER_CLASS = "org.postgresql.Driver";

    private static DBUtil instance;

    // Private constructor to prevent direct instantiation
    private DBUtil() {
        initializeDriver();
    }

    // Initialize the PostgreSQL driver
    private void initializeDriver() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            String errorMessage = "PostgreSQL JDBC Driver not found. Include it in your library path!";
            System.err.println(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }

    // Thread-safe singleton instance
    public static synchronized DBUtil getInstance() {
        if (instance == null) {
            instance = new DBUtil();
        }
        return instance;
    }

    // Get database connection
    public Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            if (connection == null) {
                throw new SQLException("Failed to establish database connection.");
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Database Connection Error: " + e.getMessage());
            throw e;
        }
    }

    // Close database connection safely
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    // Test database connection
    public boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }
}