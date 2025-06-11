package com.pluralsight;

import com.pluralsight.models.Film;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    private BasicDataSource dataSource;

    public FilmDAO(DataManager dataManager) {
        this.dataSource = dataManager.getDataSource();
    }

    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        String query = "SELECT film_id, title, description, release_year, length FROM Film";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                Film film = createFilmFromResultSet(results);
                films.add(film);
            }
        } catch (SQLException e) {
            System.err.println("Error getting films: " + e.getMessage());
        }

        return films;
    }

    public List<Film> getAllFilmsByActorId(int actorId) {
        List<Film> films = new ArrayList<>();
        String query = "SELECT a.first_name, a.last_name, f.title FROM film_actor" +
                " AS fa JOIN film AS f ON fa.film_id = f.film_id" +
                " JOIN actor AS a ON fa.actor_id = a.actor_id WHERE a.actor_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, actorId);
            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    Film film = createFilmFromResultSet(results);
                    films.add(film);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting films: " + e.getMessage());
        }

        return films;
    }


    private Film createFilmFromResultSet(ResultSet results) throws SQLException {
        Film film = new Film();
        film.setFilmId(results.getInt("film_id"));
        film.setTitle(results.getString("title"));
        film.setDescription(results.getString("description"));
        film.setReleaseYear(results.getInt("release_year "));
        film.setLength(results.getInt("film_id"));

        return film;
    }


}
