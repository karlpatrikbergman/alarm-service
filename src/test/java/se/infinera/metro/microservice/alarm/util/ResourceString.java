package se.infinera.metro.microservice.alarm.util;

public class ResourceString {
    private final String value;

    public ResourceString(String path) {
        this.value = FileHelper.INSTANCE.readFileAsString(path);
    }

    @Override
    public String toString() {
        return value;
    }
}
