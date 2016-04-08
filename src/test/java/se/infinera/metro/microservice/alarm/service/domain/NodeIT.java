package se.infinera.metro.microservice.alarm.service.domain;

import org.junit.Before;
import org.junit.Test;

/**
 * TODO:
 * At the moment a docker node must be running with ip 172.17.0.2 --> This is an integration test
 * Turn into unit test? Mock ENM?
 */
public class NodeIT {
    private Node node;
    private final String userName = "root";
    private final String password = "";
    private final String ipAddress = "172.17.0.2";
    private final int port = 80;

    @Before
    public void setup() {
        node = Node.builder()
                .ipAddress(ipAddress)
                .port(port)
                .userName("root")
                .password("root")
                .build();
    }

    @Test
    public void getNodeAlarms() {
        node.getAlarms().stream()
                .forEach(System.out::println);
    }
}