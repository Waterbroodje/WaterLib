package me.waterbroodje.waterlib.data.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class DatabaseProvider {
    private HikariDataSource dataSource;
    private final String hostname;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    public static class Builder {
        private String hostname;
        private int port;
        private String database;
        private String username;
        private String password;

        public Builder hostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder database(String database) {
            this.database = database;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public DatabaseProvider build() {
            return new DatabaseProvider(this);
        }
    }

    private DatabaseProvider(Builder builder) {
        this.hostname = builder.hostname;
        this.port = builder.port;
        this.database = builder.database;
        this.username = builder.username;
        this.password = builder.password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public DatabaseProvider connect(Plugin plugin, DatabaseDefaultCredentials... databaseDefaultCredentials) {
        for (DatabaseDefaultCredentials databaseDefaultCredential : databaseDefaultCredentials) {
            if (isDefaultValue(databaseDefaultCredential)) {
                Bukkit.getLogger().warning("##########################################");
                Bukkit.getLogger().warning("#  Database not configured... disabling  #");
                Bukkit.getLogger().warning("##########################################");
                Bukkit.getPluginManager().disablePlugin(plugin);
                return this;
            }
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);

        return this;
    }

    public DatabaseProvider connect() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);

        return this;
    }

    public QueryBuilder toQueryBuilder() {
        return new QueryBuilder(this);
    }

    public void disconnect() {
        dataSource.close();
    }

    private boolean isDefaultValue(DatabaseDefaultCredentials databaseDefaultValue) {
        String value = String.valueOf(databaseDefaultValue.getDefaultValue());
        return switch (databaseDefaultValue) {
            case PORT -> value.equalsIgnoreCase(String.valueOf(port));
            case DATABASE -> value.equalsIgnoreCase(String.valueOf(database));
            case HOSTNAME -> value.equalsIgnoreCase(String.valueOf(hostname));
            case PASSWORD -> value.equalsIgnoreCase(String.valueOf(password));
            case USERNAME -> value.equalsIgnoreCase(String.valueOf(username));
            default -> false;
        };
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
