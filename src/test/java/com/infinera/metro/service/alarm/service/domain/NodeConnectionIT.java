package com.infinera.metro.service.alarm.service.domain;

import com.infinera.metro.service.alarm.util.JsonString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class NodeConnectionIT {
    private NodeConnection nodeConnection;

    @Before
    public void setup() {
        Node node = Node.builder()
                .ipAddress("172.18.0.2")
                .port(80)
                .userName("root")
                .password("root")
                .build();
        nodeConnection = new NodeConnection(node);
        nodeConnection.restTemplate = new RestTemplate();
    }

//    @Test
    public void getAlarms() {
        nodeConnection.requestLoginAndSetSessionId();
        List<Alarm> alarmList = nodeConnection.getAlarms();
        assertNotNull(alarmList);
        alarmList.forEach(alarm -> log.info(new JsonString(alarm).toString()));
    }
}
