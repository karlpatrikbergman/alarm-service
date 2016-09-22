package se.infinera.metro.service.alarm.mapping;

import org.junit.Before;
import org.junit.Test;
import se.infinera.metro.service.alarm.controller.dto.NodeDTO;
import se.infinera.metro.service.alarm.service.domain.Node;
import se.infinera.metro.service.alarm.util.NodeMockFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NodeMapperTest {
    private NodeMapper nodeMapper;
    private Node nodeSource;
    private NodeDTO nodeDTOSource;

    @Before
    public void setup() {
        nodeMapper = NodeMapper.INSTANCE;
        nodeSource = NodeMockFactory.mockNode();
        nodeDTOSource = NodeMockFactory.mockNodeDTO();
    }

    @Test
    public void nodeToNodeDTO() {
        NodeDTO nodeDTO = nodeMapper.toNodeDTO(nodeSource);
        assertNotNull(nodeDTO);
        assertEquals(nodeSource.getIpAddress(), nodeDTO.getIpAddress());
        assertEquals(nodeSource.getPort(), nodeDTO.getPort());
        assertEquals(nodeSource.getUserName(), nodeDTO.getUserName());
        assertEquals(nodeSource.getPassword(), nodeDTO.getPassword());
    }

    @Test
    public void nodeDTOToNode() {
        Node node = nodeMapper.toNode(nodeDTOSource);
        assertNotNull(node);
        assertEquals(nodeDTOSource.getIpAddress(), node.getIpAddress());
        assertEquals(nodeDTOSource.getPort(), node.getPort());
        assertEquals(nodeDTOSource.getUserName(), node.getUserName());
        assertEquals(nodeDTOSource.getPassword(), node.getPassword());
    }
}
