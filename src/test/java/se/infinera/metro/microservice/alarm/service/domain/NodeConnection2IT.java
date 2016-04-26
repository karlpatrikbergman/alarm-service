//package se.infinera.metro.microservice.alarm.service.domain;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.WebIntegrationTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import se.infinera.metro.microservice.alarm.Application;
//import se.infinera.metro.microservice.alarm.HttpConfig;
//
//import java.util.List;
//
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = {Application.class, HttpConfig.class})
//@WebIntegrationTest
//@Slf4j
//public class NodeConnection2IT {
//    @Autowired
//    private NodeConnection2 nodeConnection;
//
//    @Test
//    public void test() {
//    Node node = Node.builder()
//            .ipAddress("172.17.0.2")
//            .port(80)
//            .userName("root")
//            .password("root")
//            .build();
//
//        List<Alarm> alarmList = nodeConnection.getAlarms();
//        assertNotNull(alarmList);
//    }
//}
