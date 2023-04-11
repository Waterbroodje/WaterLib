package me.waterbroodje.waterlib.data.mysql;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerProfile {
    private final Player player;
    private final Map<String, Object> data = new HashMap<>();

    public PlayerProfile(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void addData(String key, Object value) {
        data.put(key, value);
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public void removeData(String key) {
        data.remove(key);
    }
}
