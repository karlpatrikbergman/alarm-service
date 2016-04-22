package se.infinera.metro.microservice.alarm.util;

public final class ResourceProperty {
    private final String value;
    public ResourceProperty(String pathToPropertiesFile, String key) {
        this.value = FileHelper.INSTANCE.getProperties(pathToPropertiesFile).getProperty(key);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
