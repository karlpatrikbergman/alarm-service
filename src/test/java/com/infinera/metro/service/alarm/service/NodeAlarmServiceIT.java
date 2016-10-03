package com.infinera.metro.service.alarm.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import com.infinera.metro.service.alarm.service.domain.Alarm;
import com.infinera.metro.service.alarm.util.JsonString;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class NodeAlarmServiceIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private DataSource ds;
    private static boolean loadDataFixtures = true;
    @Autowired
    private NodeAlarmService nodeAlarmService;

    @Before
    public void loadDataFixtures() {
        if (loadDataFixtures) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/service/alarm_service_test.sql"));
            DatabasePopulatorUtils.execute(populator, ds);
            loadDataFixtures = false;
        }
    }

    @Test
    public void getAllNodesAlarms() {
        List<List<Alarm>> allNodesAlarms = nodeAlarmService.getAllNodesAlarms();
        assertNotNull(allNodesAlarms);
        allNodesAlarms.forEach(alarm -> log.info("{}", new JsonString(alarm))
        );
    }

    @Test
    public void getNodeAlarms() {
        List<Alarm> alarmList = nodeAlarmService.getAlarms("11.22.33.44"); //node inserted in alarm_repository_test.sql
        assertNotNull(alarmList);
    }
}
