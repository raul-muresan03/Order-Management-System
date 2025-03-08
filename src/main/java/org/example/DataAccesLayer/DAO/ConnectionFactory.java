package org.example.DataAccesLayer.DAO;

import java.sql.*;
import java.util.logging.Logger;

public class ConnectionFactory {
    public static final Logger logger = Logger.getLogger(ConnectionFactory.class.getName());
    public static final String driver = "com.mysql.cj.jdbc.Driver";
    public static final String dburl = "jdbc:mysql://localhost:3306/schooldb";
    private static final String user = "root";
    private static final String pass = "Raulm123!";
    private static ConnectionFactory singleInst = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(dburl, user, pass);
    }

    public static Connection getConnection() throws SQLException {
        return singleInst.createConnection();
    }

    public static void close(Connection connection) throws SQLException {
        connection.close();
    }

    public static void close(Statement statement) throws SQLException {
        statement.close();
    }

    public static void close(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }
}
