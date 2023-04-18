package me.waterbroodje.waterlib.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class JsonFile {
    private final Gson gson;
    private final JavaPlugin plugin;

    public JsonFile(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void save(Object object, String filename) throws IOException {
        File file = getFile(filename);
        FileWriter writer = new FileWriter(file);
        gson.toJson(object, writer);
        writer.close();
    }

    public <T> T load(Class<T> clazz, String filename) throws IOException {
        File file = getFile(filename);
        FileReader reader = new FileReader(file);
        T object = gson.fromJson(reader, clazz);
        reader.close();
        return object;
    }

    public Object get(String fileName, String key) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            return null;
        }

        try (Reader reader = new FileReader(file)) {
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            return json.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private File getFile(String filename) {
        return new File(plugin.getDataFolder(), filename);
    }
}
