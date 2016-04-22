package se.infinera.metro.microservice.alarm.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import se.infinera.metro.microservice.alarm.util.JsonString;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * TODO:
 * At the moment a docker node must be running with ip 172.17.0.2 --> This is an integration test
 * Turn into unit test? Mock ENM?
 */
@Slf4j
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
        List<Alarm> alarms = node.getAlarms();
        assertNotNull(alarms);
        node.getAlarms().stream()
                .forEach(alarm -> log.info("{}", new JsonString(alarm)));
    }
}