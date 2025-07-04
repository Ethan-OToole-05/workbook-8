package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class CategoriesApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        String productsQuery = "SELECT * FROM Products";
        String customersQuery = "SELECT * FROM Customers ORDER BY Country";
        String categoriesQuery = "SELECT * FROM Categories";
        ResultSet results = null;
//        Connection connection = null;
//        PreparedStatement statement = null;

        try (Connection connection = dataSource.getConnection()) {
            // Establishing connection
            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("3) Display all categories");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");
            int num = scanner.nextInt();
            scanner.nextLine();
            switch (num) {
                case 1: {
                    try (PreparedStatement statement = connection.prepareStatement(productsQuery)) {
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
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2: {
                    try (PreparedStatement statement = connection.prepareStatement(customersQuery)) {

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
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 3: {
                    try (PreparedStatement statement = connection.prepareStatement(categoriesQuery)) {
                        // Executing productsQuery
                        results = statement.executeQuery();

                        System.out.printf("%-10s | %-20s", "CategoryID", "CategoryName");
                        System.out.println();

                        // Processing the result set
                        while (results.next()) {
                            int categoryID = results.getInt("CategoryID");
                            String categoryName = results.getString("CategoryName");
                            System.out.printf("%-10s | %-15s", categoryID, categoryName);
                            System.out.println();
                        }

                        System.out.println("Select a category based on ID: ");
                        int input = scanner.nextInt();
                        scanner.nextLine();
                        String selectionQuery = "SELECT * FROM Products WHERE CategoryID LIKE ?";
                        try (PreparedStatement selection = connection.prepareStatement(selectionQuery)) {
                            selection.setInt(1, input);
                            ResultSet output = selection.executeQuery();

                            System.out.printf("%-10s %-35s %-12s %-15s%n", "ProductId", "ProductName", "UnitPrice", "UnitsInStock");
                            System.out.println("------------------------------------------------------------------------");

                            while (output.next()) {
                                int productId = output.getInt("ProductId");
                                String productName = output.getString("ProductName");
                                double unitPrice = output.getDouble("UnitPrice");
                                int unitsInStock = output.getInt("UnitsInStock");
                                System.out.printf("%-10s %-35s %-12s %-15s%n", productId, productName, unitPrice, unitsInStock);
                            }
                        }
                        break;
                    }
                }
                case 0: {
                    System.exit(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        } finally {
//            if (results != null) {
//                try {
//                    results.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
