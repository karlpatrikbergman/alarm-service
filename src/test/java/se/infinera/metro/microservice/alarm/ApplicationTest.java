package se.infinera.metro.microservice.alarm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import se.infinera.metro.microservice.alarm.controller.dto.NodeDTO;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;
import se.infinera.metro.microservice.alarm.service.domain.Node;

import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port:0")
public class ApplicationTest {

    //Set in testdata.sql
    private final String nodeIpAddress = "99.99.99.99";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private NodeRepository repository;
    @Autowired
    private DataSource ds;

    @Value("${local.server.port}")
    private int port;

    private MockMvc mockMvc;
    private RestTemplate restTemplate = new TestRestTemplate();
    private static boolean loadDataFixtures = true;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Before
    public void loadDataFixtures() {
        if (loadDataFixtures) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/testdata.sql"));
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
        List<NodeDTO> nodes = restTemplate.exchange("http://localhost:" + port + "/nodes", HttpMethod.GET, null, new ParameterizedTypeReference<List<NodeDTO>>(){}).getBody();
        assertNotNull(nodes);
        assertEquals(2, nodes.size());
    }

    @Test
    public void getNode() {
        NodeDTO node = restTemplate.exchange("http://localhost:" + port + "/nodes/" + nodeIpAddress, HttpMethod.GET, null, new ParameterizedTypeReference<NodeDTO>(){}).getBody();
        assertNotNull(node);
        System.out.println(node);
    }

    @Test
    public void getNode2() throws Exception {
        mockMvc.perform(get("/nodes/" + nodeIpAddress))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(content().string(containsString(nodeIpAddress)));
//                .andExpect(jsonPath("$.port").value(80));
    }
}