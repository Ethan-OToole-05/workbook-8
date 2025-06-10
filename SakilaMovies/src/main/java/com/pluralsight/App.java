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
        //SELECT a.first_name, a.last_name, f.title FROM film_actor
        //AS fa JOIN film AS f ON fa.film_id = f.film_id
        //JOIN actor AS a ON fa.actor_id = a.actor_id WHERE a.first_name LIKE 'sean' AND a.last_name LIKE 'williams';
        String firstAndLastNameQuery = "SELECT a.first_name, a.last_name, f.title FROM film_actor" +
                " AS fa JOIN film AS f ON fa.film_id = f.film_id" +
                " JOIN actor AS a ON fa.actor_id = a.actor_id WHERE a.first_name LIKE ? AND a.last_name LIKE ?";

        ResultSet results = null;

        try (Connection connection = dataSource.getConnection()) {

            System.out.println("Select an option below: ");
            System.out.println("1) Display all actors by last name");
            System.out.println("2) Display all movies by actor's first and last name");
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
                        nameStatement.setString(1, firstNameInput);
                        nameStatement.setString(2, lastNameInput);
                        ResultSet nameResults = nameStatement.executeQuery();

                        if(!nameResults.next()) {
                            System.out.println("No movies match.");
                        }
                        System.out.printf("%-35s %-12s %-15s", "First_Name", "Last_Name", "Movie_Title");
                        System.out.println();
                        while (nameResults.next()) {

                            String first_name = nameResults.getString("first_name");
                            String last_name = nameResults.getString("last_name");
                            String title = nameResults.getString("title");
                            System.out.printf("%-35s %-12s %-15s", first_name, last_name, title);
                            System.out.println();
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
