package se.infinera.metro.microservice.alarm;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import se.infinera.metro.microservice.alarm.controller.dto.AlarmDTO;
import se.infinera.metro.microservice.alarm.controller.dto.NodeDTO;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test fixture is loaded into db using "application_test.sql"
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlarmApplication.class)
@WebIntegrationTest("server.port:0")
public class AlarmApplicationTest {

    //Set in application_test.sql
    private final String nodeIpAddress = "11.22.33.44";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private NodeRepository repository;
    @Autowired
    private DataSource ds;
    @Value("${local.server.port}")
    private int port;
    @Autowired
    private RestTemplate restTemplate;
    private static boolean loadDataFixtures = true;
    private static final NodeDTO SHOULD_EXIST_NODE = NodeDTO.builder()
            .ipAddress("11.22.33.44")
            .port(80)
            .userName("david")
            .password("bowie")
            .build();

    @Before
    public void loadDataFixtures() {
        if (loadDataFixtures) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/application_test.sql"));
            DatabasePopulatorUtils.execute(populator, ds);
            loadDataFixtures = false;
        }
    }

    @Test
    public void contextLoads() {
        assertEquals(2, repository.count());
    }

    @Test
    public void getNodes() {
        List<NodeDTO> nodeDTOList = restTemplate.exchange("http://localhost:" + port + "/nodes", HttpMethod.GET, null, new ParameterizedTypeReference<List<NodeDTO>>() {
        }).getBody();
        assertNotNull(nodeDTOList);
        assertEquals(2, nodeDTOList.size());
        assertTrue(nodeDTOList.contains(SHOULD_EXIST_NODE));

    }

    @Test
    public void getNode() {
        NodeDTO nodeDTO = restTemplate.exchange("http://localhost:" + port + "/nodes/" + nodeIpAddress, HttpMethod.GET, null, new ParameterizedTypeReference<NodeDTO>() {
        }).getBody();
        assertEquals(SHOULD_EXIST_NODE, nodeDTO);
    }

    @Test
    public void getAllNodesAlarms() {
        List<List<AlarmDTO>> nodesAlarmDTOLists = restTemplate.exchange("http://localhost:" + port + "/alarms", HttpMethod.GET, null, new ParameterizedTypeReference<List<List<AlarmDTO>>>() {
        }).getBody();
        assertEquals(2, nodesAlarmDTOLists.size());
        Optional<AlarmDTO> alarmDTOOptional = nodesAlarmDTOLists.stream()
                .flatMap(list -> list.stream())
                .filter(alarmDTO -> alarmDTO.getAlarmNeIpAddress().equals("44.33.22.11"))
                .findAny();
        assertNotNull(alarmDTOOptional.get());
        assertEquals("thin white duke", alarmDTOOptional.get().getAlarmNeName());
    }

    @Test
    public void getSpecificNodeAlarms() {
        List<AlarmDTO> nodeAlarmList = restTemplate.exchange("http://localhost:" + port + "/alarms/11.22.33.44", HttpMethod.GET, null, new ParameterizedTypeReference<List<AlarmDTO>>() {
        }).getBody();
        assertEquals(1, nodeAlarmList.size());
    }
}