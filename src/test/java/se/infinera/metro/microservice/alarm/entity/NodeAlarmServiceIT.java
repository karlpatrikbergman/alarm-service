package se.infinera.metro.microservice.alarm.entity;


import org.junit.Before;
import se.infinera.metro.microservice.alarm.service.NodeAlarmService;

public class NodeAlarmServiceIT {
    private NodeAlarmService nodeAlarmService;

    @Before
    public void setup() {
        nodeAlarmService = new NodeAlarmService();
    }
}
