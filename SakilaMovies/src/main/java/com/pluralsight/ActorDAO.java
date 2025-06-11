package com.pluralsight;

import com.pluralsight.models.Actor;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO {
    private BasicDataSource dataSource;

    public ActorDAO(DataManager dataManager) {
        this.dataSource = dataManager.getDataSource();
    }

    public List<Actor> getAllActors() {
        List<Actor> actors = new ArrayList<>();
        String query = "SELECT actor_id, first_name, last_name FROM Actor";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                Actor actor = createActorFromResultSet(results);
                actors.add(actor);
            }
        } catch (SQLException e) {
            System.err.println("Error getting actors: " + e.getMessage());
        }

        return actors;
    }

    public List<Actor> getAllActorsByLastName(String lastName) {
        List<Actor> actors = new ArrayList<>();
        String query = "SELECT actor_id, first_name, last_name FROM Actor WHERE last_name = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, lastName);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    Actor actor = createActorFromResultSet(results);
                    actors.add(actor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting actors: " + e.getMessage());
        }
        return actors;
    }

    private Actor createActorFromResultSet(ResultSet results) throws SQLException {
        Actor actor = new Actor();
        actor.setActorId(results.getInt("actor_id"));
        actor.setFirstName(results.getString("first_name"));
        actor.setLastName(results.getString("last_name"));

        return actor;
    }

}
