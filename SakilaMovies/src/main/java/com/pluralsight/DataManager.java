package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;

/**
 * Basic DataManager class that serves as a simple middleman for getting data
 * from different tables. This demonstrates a rudimentary approach before
 * implementing proper DAO patterns.
 */
public class DataManager {
    private BasicDataSource dataSource;

    /**
     * Constructor that initializes the BasicDataSource with database connection parameters
     */
    public DataManager(String url, String username, String password) {
        this.dataSource = new BasicDataSource();
        this.dataSource.setUrl(url);
        this.dataSource.setUsername(username);
        this.dataSource.setPassword(password);
    }
    public void getAllActors() {
        String query = "SELECT actor_id, first_name, last_name FROM Actor";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            System.out.printf("%-10s %-35s %-12s", "ActorId", "First_Name", "Last_Name");
            System.out.println("------------------------------------------------------------------------");

            while (results.next()) {
                int actorId = results.getInt("actor_id");
                String firstName = results.getString("first_name");
                double lastName = results.getDouble("last_name");
                System.out.printf("%-10s %-35s %-12s", actorId, firstName, lastName);
            }
        } catch (SQLException e) {
            System.err.println("Error getting actors: " + e.getMessage());
        }
    } public void getAllActorsByLastName(String name) {
        String query = "SELECT actor_id, first_name, last_name FROM Actor WHERE last_name = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) { //TODO: FIX ERROR WITH PUTTING IN THE LAST NAME.

            statement.setString(1, name);

            System.out.printf("%-10s %-35s %-12s", "ActorId", "First_Name", "Last_Name");
            System.out.println("------------------------------------------------------------------------");

            while (results.next()) {
                int actorId = results.getInt("actor_id");
                String firstName = results.getString("first_name");
                double lastName = results.getDouble("last_name");
                System.out.printf("%-10s %-35s %-12s", actorId, firstName, lastName);
            }
        } catch (SQLException e) {
            System.err.println("Error getting actors: " + e.getMessage());
        }
    }
}
