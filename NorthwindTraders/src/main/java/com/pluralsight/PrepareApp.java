package com.pluralsight;

import java.sql.*;

public class PrepareApp {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "yearup";

        String query = "SELECT * FROM Products";
        try {
            // Establishing connection
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(query);

            // Executing query
            ResultSet results = statement.executeQuery();

            // Processing the result set
            while (results.next()) {
                // Replace with your column names and types
                System.out.println("Product ID: " + results.getString("ProductID"));
                System.out.println("Name: " + results.getString("ProductName"));
                double price = results.getDouble("UnitPrice");
                System.out.println("Price: $" + String.format("%.2f", price));
                System.out.println("Stock: " + results.getString("UnitsInStock"));
                System.out.println("--------------------");
            }

            // Closing resources
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}