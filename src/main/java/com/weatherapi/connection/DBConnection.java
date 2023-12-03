package com.weatherapi.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String db_url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String db_username = "postgres";
    private static final String db_password = "mysecretpassword";

    private static Connection connection;

    public static Connection getInstance() throws SQLException {
            return DriverManager.getConnection(db_url, db_username, db_password);
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
