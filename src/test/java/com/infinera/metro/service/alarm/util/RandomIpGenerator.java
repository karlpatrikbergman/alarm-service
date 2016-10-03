package com.infinera.metro.service.alarm.util;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RandomIpGenerator {
    INSTANCE;

    public String getRandomIpAddress() {
        return Stream.of(0,1,2,3)
                .map(i -> getIpAddressBlock())
                .collect(Collectors.joining("."));
    }

    private String getIpAddressBlock() {
        return String.valueOf(new RandomDataGenerator().nextInt(0,255));
    }
}
