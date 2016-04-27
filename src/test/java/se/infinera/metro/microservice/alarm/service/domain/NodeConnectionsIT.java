package se.infinera.metro.microservice.alarm.service.domain;

import com.google.common.collect.Lists;
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
import se.infinera.metro.microservice.alarm.repository.AlarmRepository;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;
import se.infinera.metro.microservice.alarm.service.NodeAlarmService;
import se.infinera.metro.microservice.alarm.util.JsonString;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, HttpConfig.class})
@WebIntegrationTest
@Slf4j
public class NodeConnectionsIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private DataSource ds;
    private static boolean loadDataFixtures = true;
    @Autowired
    private NodeConnections nodeConnections;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private NodeAlarmService alarmService;


    @Before
    public void loadDataFixtures() {
        if (loadDataFixtures) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/insert_docker_nodes.sql"));
            DatabasePopulatorUtils.execute(populator, ds);
            loadDataFixtures = false;
        }
        assertEquals(2, nodeRepository.count());
    }

    @Test
    public void pollNodesForAlarms() {
        nodeConnections.addNodeConnections();
        nodeConnections.requestLoginAndSetSessionIdForAddedNodeConnections();
        List<Alarm> alarmsFromNodeConnections = nodeConnections.getAllNodesAlarms();
        alarmRepository.save(alarmsFromNodeConnections);
        assertTrue(alarmRepository.count() > 0);

        alarmsFromNodeConnections.stream()
                .map(alarm ->new JsonString(alarm))
                .forEach(System.out::println);
//
//        int nrOfAlarmsFromNodeConnections = alarmsFromNodeConnections.size();
//        int nrOfAlarmsInDatabase = alarmService.getAllNodesAlarms().stream()
//                .flatMap(list -> list.stream())
//                .collect(Collectors.toList()).size();
//        assertEquals(nrOfAlarmsFromNodeConnections, nrOfAlarmsInDatabase);

    }
}
