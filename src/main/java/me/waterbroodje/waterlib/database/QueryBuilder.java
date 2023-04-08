package me.waterbroodje.waterlib.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QueryBuilder {
    private final Connection connection;
    private final Map<String, PreparedStatement> cachedStatements = new ConcurrentHashMap<>();

    public QueryBuilder(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        PreparedStatement statement = cachedStatements.get(sql);
        if (statement == null) {
            statement = connection.prepareStatement(sql);
            cachedStatements.put(sql, statement);
        }
        return statement;
    }

    public ResultSet executeQuery(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    public int executeUpdate(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    public void close() {
        for (PreparedStatement statement : cachedStatements.values()) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        cachedStatements.clear();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

