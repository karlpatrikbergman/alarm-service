package se.infinera.metro.microservice.alarm.repository;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import se.infinera.metro.microservice.alarm.Application;
import se.infinera.metro.microservice.alarm.HttpConfig;
import se.infinera.metro.microservice.alarm.service.domain.Node;
import se.infinera.metro.microservice.alarm.util.JsonString;
import se.infinera.metro.microservice.alarm.util.NodeMockFactory;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, HttpConfig.class})
@WebAppConfiguration //Needed when using Swagger for some reason I don't know
@Slf4j
public class NodeRepositoryTest {
    @Autowired
    private NodeRepository nodeRepository;
    private long nodeId; //Node added in @Before, to be updated/deleted

    @Before
    public void setup() {
        nodeRepository.deleteAll();
        Node node = nodeRepository.save(NodeMockFactory.mockNode());
        nodeId = node.getId();
    }

    @Test
    public void addNode() {
        Node node = nodeRepository.save(NodeMockFactory.mockNode());
        assertNotNull(node);
        assertEquals(2, Lists.newArrayList(nodeRepository.findAll()).size());
        log.info("{}", new JsonString(node));
    }

    @Test
    public void deleteNode() {
        Node node = nodeRepository.findOne(nodeId);
        assertNotNull(node);
        log.info("{}", new JsonString(node));
        nodeRepository.delete(nodeId);;
        Node shouldBeNull = nodeRepository.findOne(nodeId);
        assertTrue(shouldBeNull == null);
    }

    @Test
    public void updateNode() {
        Node node = nodeRepository.findOne(nodeId);
        log.info("Node before update: {}", new JsonString(node));
        Node updatedNode = Node.builder()
                .id(node.getId())
                .ipAddress(node.getIpAddress())
                .port(3333)
                .userName("Elvis")
                .password("Hound-dog")
                .build();
        updatedNode = nodeRepository.save(updatedNode);
        assertNotNull(updatedNode);
        log.info("Node after update: {}", new JsonString(updatedNode));
    }
}
