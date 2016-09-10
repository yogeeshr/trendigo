package com.hackday.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yogeesh.rajendra on 9/10/16.
 */
public class MySQLDao {

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to create DB Connection
     *
     * @throws SQLException
     */
    private static void createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/trendigo?useSSL=false", "root",
                "q1w2e3r4");
    }

    /**
     * Method to close connection
     *
     * @throws SQLException
     */
    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Method to get all firesales within 2 kms
     * @param lat
     * @param lng
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getAllRowsOfFireSalesWithinMile(double lat, double lng, int mile) throws Exception {

        String dbQuery = "SELECT * from firesales where ( 3959 * acos ( cos(radians(lat)) * cos(radians("+lat+") ) * " +
                "cos (radians("+lng+")-radians(lng) ) + sin(radians(lat)) * sin(radians("+lat+")) ) ) < "+mile+";";

        List<Map<String, String>> rows = executeAndGetColumnsOutput(dbQuery);
        return rows;

    }

    /**
     *
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getAllRowsOfFireSales() throws Exception {
        String dbQuery = "Select * from firesales where;";
        List<Map<String, String>> rows = executeAndGetColumnsOutput(dbQuery);
        return rows;
    }

    /**
     * Given a query return result
     *
     * @param dbQuery
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<Map<String, String>> executeAndGetColumnsOutput(String dbQuery) throws Exception {

        createConnection();

        String statement = dbQuery;
        PreparedStatement pStatement = connection.prepareStatement(statement);
        ResultSet rs = pStatement.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> eachLine;

        while (rs.next()) { // process results one row at a time
            eachLine = new HashMap<>();
            for (int i = 1; i <= numberOfColumns; i++) {
                eachLine.put(rsmd.getColumnName(i), rs.getString(i));
            }
            data.add(eachLine);
        }

        closeConnection();

        return data == null ? null : data;
    }

}
