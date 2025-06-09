package com.pluralsight;

import java.sql.*;
import javax.sql.DataSource;

public class App {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //Loading the MySQL Driver.
        Class.forName("com.mysql.cj.jdbc.Driver");

        //Getting connected to localdb
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                "root",
                "yearup");

        //Creating our statement.
        Statement statement = connection.createStatement();

        String query  = "SELECT * FROM products";

        ResultSet results = statement.executeQuery(query);

        while(results.next()) {
            String product = results.getString("ProductName");
            System.out.println(product);
        }
        connection.close();
    }

}
