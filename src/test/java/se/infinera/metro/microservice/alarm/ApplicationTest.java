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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import se.infinera.metro.microservice.alarm.entity.Node;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;

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

    private final String nodeIpAddress = "172.17.0.2";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private NodeRepository repository;

    @Value("${local.server.port}")
    private int port;

    private MockMvc mockMvc;
    private RestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void contextLoads() {
        assertEquals(2, repository.count());
    }

    @Test
    public void getNodes() {
        List<Node> nodes = restTemplate.exchange("http://localhost:" + port + "/nodes", HttpMethod.GET, null, new ParameterizedTypeReference<List<Node>>(){}).getBody();
        assertNotNull(nodes);
        assertEquals(2, nodes.size());
    }

    @Test
    public void getNode() {
        List<Node> nodes = restTemplate.exchange("http://localhost:" + port + "/nodes/" + nodeIpAddress, HttpMethod.GET, null, new ParameterizedTypeReference<List<Node>>(){}).getBody();
        assertNotNull(nodes);
        System.out.println(nodes.get(0));
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