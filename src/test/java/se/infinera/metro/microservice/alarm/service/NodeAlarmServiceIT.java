package se.infinera.metro.microservice.alarm.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import se.infinera.metro.microservice.alarm.Application;
import se.infinera.metro.microservice.alarm.HttpConfig;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;
import se.infinera.metro.microservice.alarm.service.domain.Alarm;
import se.infinera.metro.microservice.alarm.util.JsonString;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, HttpConfig.class})
@WebIntegrationTest
@Slf4j
public class NodeAlarmServiceIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private DataSource ds;
    private static boolean loadDataFixtures = true;
    @Autowired
    private NodeAlarmService nodeAlarmService;
    private final String NODE_IP_ADDRESS = "99.99.99.99"; //Node inserted in testdata.sql

    @Before
    public void loadDataFixtures() {
        if (loadDataFixtures) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/testdata.sql"));
            DatabasePopulatorUtils.execute(populator, ds);
            loadDataFixtures = false;
        }
    }

    @Test
    public void getAllNodesAlarms() {
        List<List<Alarm>> allNodesAlarms = nodeAlarmService.getAllNodesAlarms();
        assertNotNull(allNodesAlarms);
        allNodesAlarms.stream()
                .forEach(alarm -> log.info("{}", new JsonString(alarm))
        );
    }

    @Test
    public void getNodeAlarms() {
        List<Alarm> alarmList = nodeAlarmService.getAlarms(NODE_IP_ADDRESS);
    }
}
