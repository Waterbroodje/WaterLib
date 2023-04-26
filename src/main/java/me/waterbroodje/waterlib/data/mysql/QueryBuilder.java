package me.waterbroodje.waterlib.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class QueryBuilder {
    private final DatabaseProvider databaseProvider;

    public QueryBuilder(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public <T> List<T> select(String query, RowMapper<T> rowMapper, Object... params) throws SQLException {
        List<T> results = new ArrayList<>();
        try (Connection connection = databaseProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setParams(statement, params);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(rowMapper.map(resultSet));
                }
            }
        }
        return results;
    }

    public int executeUpdate(String query, Object... params) throws SQLException {
        try (Connection connection = databaseProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setParams(statement, params);
            return statement.executeUpdate();
        }
    }

    public int[] executeBatchUpdate(String query, List<Object[]> batchParams) throws SQLException {
        try (Connection connection = databaseProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (Object[] params : batchParams) {
                setParams(statement, params);
                statement.addBatch();
            }
            return statement.executeBatch();
        }
    }

    public <T> CompletableFuture<List<T>> selectAsync(String query, RowMapper<T> rowMapper, Object... params) {
        return CompletableFuture.supplyAsync(() -> {
            List<T> results = new ArrayList<>();
            try (Connection connection = databaseProvider.getDataSource().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                setParams(statement, params);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(rowMapper.map(resultSet));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return results;
        });
    }

    public CompletableFuture<Integer> executeUpdateAsync(String query, Object... params) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = databaseProvider.getDataSource().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                setParams(statement, params);
                return statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<int[]> executeBatchUpdateAsync(String query, List<Object[]> batchParams) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = databaseProvider.getDataSource().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Object[] params : batchParams) {
                    setParams(statement, params);
                    statement.addBatch();
                }
                return statement.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private void setParams(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }

    public interface RowMapper<T> {
        T map(ResultSet resultSet) throws SQLException;
    }
}