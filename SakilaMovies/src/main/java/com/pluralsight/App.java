package com.pluralsight;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

import com.pluralsight.models.Actor;
import org.apache.commons.dbcp2.BasicDataSource;

public class App {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        if (url == null) {
            url = "jdbc:mysql://127.0.0.1:3306/sakila";
        }


        DataManager dataManager = new DataManager(url, user, password);
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setUrl(url);
//        dataSource.setUsername(user);
//        dataSource.setPassword(password);

        String lastNameQuery = "SELECT * FROM actor WHERE last_name LIKE ?";
        //SELECT a.first_name, a.last_name, f.title FROM film_actor
        //AS fa JOIN film AS f ON fa.film_id = f.film_id
        //JOIN actor AS a ON fa.actor_id = a.actor_id WHERE a.first_name LIKE 'sean' AND a.last_name LIKE 'williams';
        String firstAndLastNameQuery = "SELECT a.first_name, a.last_name, f.title FROM film_actor" +
                " AS fa JOIN film AS f ON fa.film_id = f.film_id" +
                " JOIN actor AS a ON fa.actor_id = a.actor_id WHERE a.first_name LIKE ? AND a.last_name LIKE ?";

        ResultSet results = null;

        try {
            ActorDAO actorDAO = new ActorDAO(dataManager);

            System.out.println("Select an option below: ");
            System.out.println("1) Display all actors by last name");
            System.out.println("2) Display all movies by actor's first and last name");
            System.out.println("3) Display all movies by the actor's id");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");
            int inputNum = input.nextInt();
            input.nextLine();

            switch (inputNum) {
                case 1: {
                    System.out.println("Please enter the last name to search by: ");
                    System.out.print("Input: ");
                    String lastNameInput = input.nextLine();
                    System.out.println(actorDAO.getAllActorsByLastName(lastNameInput));
//                    dataManager.getAllActorsByLastName(lastNameInput);
                    break;
                }
                case 2: {
                    System.out.println("Please enter the first name to search by: ");
                    System.out.print("Input: ");
                    String firstNameInput = input.nextLine();
                    System.out.println("Please enter the last name to search by: ");
                    System.out.print("Input: ");
                    String lastNameInput = input.nextLine();

                    dataManager.getAllFilmsByActorFullName(firstNameInput, lastNameInput);
                    break;
                }
                case 3: {

                    break;
                }
                case 0: {
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
