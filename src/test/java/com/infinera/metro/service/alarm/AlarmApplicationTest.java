package com.infinera.metro.service.alarm;

import com.infinera.metro.service.alarm.controller.dto.AlarmDTO;
import com.infinera.metro.service.alarm.controller.dto.NodeDTO;
import com.infinera.metro.service.alarm.repository.NodeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.*;

import static com.infinera.metro.service.alarm.util.NodeMockFactory.mockNodeDTO;
import static org.junit.Assert.*;

/**
 * Test fixture is loaded into db using "application_test.sql"
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AlarmApplication.class)
//@WebIntegrationTest("server.port:0")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlarmApplicationTest {

    //Set in application_test.sql
    private final String nodeIpAddress = "11.22.33.44";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private NodeRepository nodeRepository;
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
        assertEquals(2, nodeRepository.count());
    }

    /** Can we get a List of objects with getForObject()? **/
    @Test
    public void getNodes() {
        List<NodeDTO> nodeDTOList = restTemplate.exchange(
                getAlarmServiceNodesUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NodeDTO>>() {}
        ).getBody();
        assertNotNull(nodeDTOList);
        assertEquals(2, nodeDTOList.size());
        assertTrue(nodeDTOList.contains(SHOULD_EXIST_NODE));
    }

    @Test
    public void getNode() {
        Map<String, String> params = new HashMap<>();
        params.put("ipAddress", nodeIpAddress);
        NodeDTO nodeDTO = restTemplate.getForObject(
                getAlarmServiceNodesUri() + "/{ipAddress}",
                NodeDTO.class,
                params
        );
        assertEquals(SHOULD_EXIST_NODE, nodeDTO);
    }

    @Test
    public void addAndRemoveNode() {
        NodeDTO nodeToAdd = mockNodeDTO();
        HttpEntity<NodeDTO> request = new HttpEntity<>(nodeToAdd);
        NodeDTO addNodeResponse = restTemplate.postForObject(
                getAlarmServiceNodesUri(),
                request,
                NodeDTO.class);
        assertEquals(addNodeResponse, nodeToAdd);

        Map<String, String> params = new HashMap<>();
        params.put("ipAddress", addNodeResponse.getIpAddress());
        restTemplate.delete ("http://localhost:" + port + "/nodes/" + "{ipAddress}",  params);
    }

    @Test
    public void getAllNodesAlarms() {
        List<List<AlarmDTO>> nodesAlarmDTOLists = restTemplate.exchange(
                getAlarmServiceAlarmsUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<List<AlarmDTO>>>() {
        }).getBody();

        assertEquals(2, nodesAlarmDTOLists.size());
        Optional<AlarmDTO> alarmDTOOptional = nodesAlarmDTOLists.stream()
                .flatMap(Collection::stream)
                .filter(alarmDTO -> alarmDTO.getAlarmNeIpAddress().equals("44.33.22.11"))
                .findAny();
        assertTrue(alarmDTOOptional.isPresent());
        assertEquals("thin white duke", alarmDTOOptional.get().getAlarmNeName());
    }

    @Test
    public void getSpecificNodeAlarms() {
        Map<String, String> params = new HashMap<>();
        params.put("ipAddress", "11.22.33.44");
        List<AlarmDTO> nodeAlarmList = restTemplate.exchange(
                getAlarmServiceAlarmsUri() + "/{ipAddress}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AlarmDTO>>() {},
                params)
            .getBody();
        assertEquals(1, nodeAlarmList.size());
    }

    private String getAlarmServiceAlarmsUri() {
        return getAlarmServiceHostUri() + "/alarms";
    }

    private String getAlarmServiceNodesUri() {
        return getAlarmServiceHostUri() + "/nodes";
    }

    private String getAlarmServiceHostUri() {
        return "http://localhost:" + port;
    }
}