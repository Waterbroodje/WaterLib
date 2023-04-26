package me.waterbroodje.waterlib;

import me.waterbroodje.waterlib.data.mysql.DatabaseProvider;
import me.waterbroodje.waterlib.data.mysql.QueryBuilder;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ExampleDatabase {
    public final DatabaseProvider databaseProvider;

    public ExampleDatabase() {
        this.databaseProvider = DatabaseProvider.builder()
                .database("database")
                .hostname("localhost")
                .password("password")
                .username("username")
                .port(3306)
                .build();
    }

    @Test
    public void test() {
        QueryBuilder queryBuilder = new QueryBuilder(databaseProvider);

        // Example select query
        try {
            List<String> names = queryBuilder.select("SELECT name FROM users WHERE age > ?", resultSet -> resultSet.getString("name"), 18);
            System.out.println(names);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Example update query
        try {
            int updatedRows = queryBuilder.executeUpdate("UPDATE users SET age = ? WHERE name = ?", 20, "John");
            System.out.println("Updated rows: " + updatedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Example insert query
        try {
            queryBuilder.executeUpdate("INSERT INTO users (name, age) VALUES (?, ?)", "John", 20);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAsync() {
        QueryBuilder queryBuilder = new QueryBuilder(databaseProvider);

        // Example async select query
        CompletableFuture<List<String>> futureNames = queryBuilder.selectAsync("SELECT name FROM users WHERE age > ?", resultSet -> resultSet.getString("name"), 18);
        futureNames.thenAccept(System.out::println);

        // Example async update query
        CompletableFuture<Integer> futureUpdatedRows = queryBuilder.executeUpdateAsync("UPDATE users SET age = ? WHERE name = ?", 20, "John");
        futureUpdatedRows.thenAccept(updatedRows -> System.out.println("Updated rows: " + updatedRows));
    }
}
