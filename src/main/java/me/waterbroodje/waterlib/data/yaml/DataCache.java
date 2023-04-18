package me.waterbroodje.waterlib.data.yaml;

import java.util.HashMap;
import java.util.Map;

public class DataCache {
    private final Map<String, Object> data = new HashMap<>();

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
