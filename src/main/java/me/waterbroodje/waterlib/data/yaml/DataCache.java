package me.waterbroodje.waterlib.data.yaml;

import java.util.HashMap;
import java.util.Map;

public class DataCache {
    private final Map<Object, Object> data = new HashMap<>();

    public Map<Object, Object> getData() {
        return data;
    }

    public void addData(Object key, Object value) {
        data.put(key, value);
    }

    public Object getData(Object key) {
        return data.get(key);
    }

    public void removeData(Object key) {
        data.remove(key);
    }
}