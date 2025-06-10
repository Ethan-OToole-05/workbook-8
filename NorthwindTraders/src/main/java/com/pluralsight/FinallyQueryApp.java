package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class FinallyQueryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = args[0];
        String password = args[1];

        String productsQuery = "SELECT * FROM Products";
        String customersQuery = "SELECT * FROM Customers ORDER BY Country";
        ResultSet results = null;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establishing connection
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");
            int num = scanner.nextInt();
            scanner.nextLine();
            switch (num) {
                case 1: {
                    statement = connection.prepareStatement(productsQuery);

                    // Executing productsQuery
                    results = statement.executeQuery();

                    System.out.printf("%-10s %-35s %-12s %-15s%n", "ProductId", "ProductName", "UnitPrice", "UnitsInStock");
                    System.out.println("------------------------------------------------------------------------");

                    // Processing the result set
                    while (results.next()) {
                        int productId = results.getInt("ProductID");
                        String productName = results.getString("ProductName");
                        double unitPrice = results.getDouble("UnitPrice");
                        int unitsInStock = results.getInt("UnitsInStock");
                        System.out.printf("%-10s %-35s %-12s %-15s%n", productId, productName, unitPrice, unitsInStock);
                    }
                    break;
                }
                case 2: {
                    statement = connection.prepareStatement(customersQuery);

                    // Executing productsQuery
                    results = statement.executeQuery();

                    System.out.printf("%-20s | %-30s | %-15s | %-15s | %-20s%n", "ContactName", "CompanyName", "City", "Country", "Phone");
                    System.out.println("----------------------------------------------------------------------------------------------------");

                    // Processing the result set
                    while (results.next()) {
                        String contactName = results.getString("ContactName");
                        String companyName = results.getString("CompanyName");
                        String city = results.getString("City");
                        String country = results.getString("Country");
                        String phone = results.getString("Phone");
                        System.out.printf("%-20s | %-30s | %-15s | %-15s | %-20s%n", contactName, companyName, city, country, phone);
                    }
                    break;
                }
                case 0: {
                    System.exit(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
