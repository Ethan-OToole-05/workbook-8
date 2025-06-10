package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

public class App {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String url = "jdbc:mysql://127.0.0.1:3306/Sakila";
        String user = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        String lastNameQuery = "SELECT * FROM actor WHERE last_name LIKE ?";
        String firstAndLastNameQuery = "SELECT * FROM actor WHERE first_name LIKE ? AND last_name LIKE ?";

        ResultSet results = null;

        try (Connection connection = dataSource.getConnection()) {

            System.out.println("Select an option below: ");
            System.out.println("1) Display all actors by last name");
            System.out.println("2) Display all actors by first and last name");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");
            int inputNum = input.nextInt();
            input.nextLine();

            switch (inputNum) {
                case 1: {
                    System.out.println("Please enter the last name to search by: ");
                    System.out.print("Input: ");
                    String lastNameInput = input.nextLine();
                    try (PreparedStatement statement = connection.prepareStatement(lastNameQuery)) {
                        // Executing productsQuery
                        statement.setString(1, lastNameInput);
                        results = statement.executeQuery();

                        if (results.next()) {
                            System.out.printf("%-10s %-35s %-12s", "ActorId", "First_Name", "Last_Name");
                            System.out.println();
                            do {
                                int actorId = results.getInt("actor_id");
                                String first_name = results.getString("first_name");
                                String last_name = results.getString("last_name");
                                System.out.printf("%-10s %-35s %-12s", actorId, first_name, last_name);
                                System.out.println();
                            } while (results.next());
                        } else {
                            System.out.println("No matches!");
                        }
                    }
                    break;
                }
                case 2: {
                    System.out.println("Please enter the first name to search by: ");
                    System.out.print("Input: ");
                    String firstNameInput = input.nextLine();
                    System.out.println("Please enter the last name to search by: ");
                    System.out.print("Input: ");
                    String lastNameInput = input.nextLine();
                    try (PreparedStatement nameStatement = connection.prepareStatement(firstAndLastNameQuery)) {
                        // Executing productsQuery
                        nameStatement.setString(1, firstNameInput);
                        nameStatement.setString(2, lastNameInput);
                        ResultSet nameResults = nameStatement.executeQuery();

                        if (nameResults.next()) {
                            System.out.printf("%-10s %-35s %-12s", "ActorId", "First_Name", "Last_Name");
                            System.out.println();
                            while (nameResults.next()) {
                                int actorId = nameResults.getInt("actor_id");
                                String first_name = nameResults.getString("first_name");
                                String last_name = nameResults.getString("last_name");
                                System.out.printf("%-10s %-35s %-12s", actorId, first_name, last_name);
                                System.out.println();
                            }
                        } else {
                            System.out.println("No matches!");
                        }
                    }
                    break;
                }
                case 0: {
                    System.exit(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
