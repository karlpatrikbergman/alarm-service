package com.infinera.metro.service.alarm.util;

import java.util.Properties;

public final class ResourceProperties {
    private final Properties properties;

    public ResourceProperties(String pathToPropertiesFile) {
        this.properties = FileHelper.INSTANCE.getProperties(pathToPropertiesFile);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
