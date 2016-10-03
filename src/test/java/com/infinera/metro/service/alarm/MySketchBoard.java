package com.infinera.metro.service.alarm;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MySketchBoard {

    @Test
    public void extremelyUgly() {
        Iterator<String> iterator =
                Pattern.compile("\\.")
                        .splitAsStream("99.88.77.66")
                        .collect(Collectors.toCollection(LinkedList::new))
                        .descendingIterator();
        Iterable<String> iterable = () -> iterator;
        String ipAddressRightORder = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.joining("."));
        System.out.println(ipAddressRightORder);
    }

    @Test
    public void stringUtils() {
        String ipNumber = "99.88.77.66";
        String ipNumberReversed =StringUtils.reverseDelimited(ipNumber, '.');
        System.out.println(ipNumberReversed);
    }
}
