package me.waterbroodje.waterlib.utilities.bukkit;

import me.waterbroodje.waterlib.data.mysql.DatabaseProvider;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigFile {
    private final FileConfiguration fileConfiguration;

    public ConfigFile(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public DatabaseProvider getDatabaseProvider() {
        return new DatabaseProvider.Builder()
                .hostname(fileConfiguration.getString("database.hostname"))
                .port(fileConfiguration.getInt("database.port"))
                .database(fileConfiguration.getString("database.database"))
                .username(fileConfiguration.getString("database.username"))
                .password(fileConfiguration.getString("database.password"))
                .build();
    }
}