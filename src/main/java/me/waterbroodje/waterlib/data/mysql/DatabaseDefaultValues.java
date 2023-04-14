package me.waterbroodje.waterlib.data.mysql;

public enum DatabaseDefaultValues {
    HOSTNAME("hostname"),
    PORT("3306"),
    DATABASE("database"),
    USERNAME("username"),
    PASSWORD("password");

    private String defaultValue;

    private DatabaseDefaultValues(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
