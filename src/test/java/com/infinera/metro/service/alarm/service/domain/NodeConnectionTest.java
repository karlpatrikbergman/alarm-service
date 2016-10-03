package com.infinera.metro.service.alarm.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class NodeConnectionTest {
    private NodeConnection nodeConnection;
    private Node node;
    private final String IP_ADDRESS = "99.99.99.99";
    private final int PORT = 80;
    private final String USER_NAME = "root";
    private final String PASSWORD = "root";
    private final String ALARMS_PATH = "/mib/alarm/current/get.json";
    private final String EXPECTED_LOGIN_URL = "http://"+IP_ADDRESS+":"+PORT+"/getLogin.asp?userName="+USER_NAME+"&password="+PASSWORD+"&oneTimeLogin=false";

    @Before
    public void setup() {
        Node node = Node.builder()
                .ipAddress(IP_ADDRESS)
                .port(PORT)
                .userName(USER_NAME)
                .password(PASSWORD)
                .build();
        nodeConnection = new NodeConnection(node);
    }

    @Test
    public void testGetLoginUrl() {
        String loginUrl = nodeConnection.getEnmLoginUri();
        assertEquals(EXPECTED_LOGIN_URL, loginUrl);
        log.info(loginUrl);
    }

    @Test
    public void testGetAlarmsUrl() {
        String getAlarmsUrl = nodeConnection.getAlarmsUri();
        String EXPECTED_GET_ALARMS_URL = "http://" + IP_ADDRESS + ":" + PORT + ALARMS_PATH;
        assertEquals(EXPECTED_GET_ALARMS_URL, getAlarmsUrl);
        log.info(getAlarmsUrl);
    }
}
