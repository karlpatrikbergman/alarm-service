package com.infinera.metro.service.alarm.mapping;

import com.infinera.metro.service.alarm.service.domain.Node;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Before;
import org.junit.Test;
import com.infinera.metro.service.alarm.controller.dto.NodeDTO;
import com.infinera.metro.service.alarm.util.NodeMockFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NodeMappingTest {
    private MapperFacade mapperFacade; //One alternative
    private BoundMapperFacade<Node, NodeDTO> boundMapperFacade; //Another (faster) alternative
    private Node nodeSource;
    private NodeDTO nodeDTOSource;
    private List<Node> nodeListSource;
    private List<NodeDTO> nodeDTOListSource;

    @Before
    public void setup() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//        mapperFactory.classMap(Node.class, NodeDTO.class)
//                .exclude("alarmRepository")
//                .byDefault()
//                .register();
        mapperFacade = mapperFactory.getMapperFacade();
        boundMapperFacade = mapperFactory.getMapperFacade(Node.class, NodeDTO.class);
        nodeSource = NodeMockFactory.mockNode();
        nodeDTOSource = NodeMockFactory.mockNodeDTO();
        nodeListSource = NodeMockFactory.mockNodeList();
        nodeDTOListSource = NodeMockFactory.mockNodeDTOList();
    }

    @Test
    public void nodeToNodeDTO_UsingMapperFacade() {
        NodeDTO nodeDTO = mapperFacade.map(nodeSource, NodeDTO.class);
        assertNotNull(nodeDTO);
        assertEquals(nodeSource.getIpAddress(), nodeDTO.getIpAddress());
        assertEquals(nodeSource.getPort(), nodeDTO.getPort());
        assertEquals(nodeSource.getUserName(), nodeDTO.getUserName());
        assertEquals(nodeSource.getPassword(), nodeDTO.getPassword());
    }

    @Test
    public void nodeDTOToNode_UsingMapperFacade() {
        Node node = mapperFacade.map(nodeDTOSource, Node.class);
        assertNotNull(node);
        assertEquals(nodeDTOSource.getIpAddress(), node.getIpAddress());
        assertEquals(nodeDTOSource.getPort(), node.getPort());
        assertEquals(nodeDTOSource.getUserName(), node.getUserName());
    }

    @Test
    public void nodeListToNodeDTOList() {
        List<NodeDTO> nodeDTOList = mapperFacade.mapAsList(nodeListSource, NodeDTO.class);
        assertNotNull(nodeDTOList);
        assertEquals(nodeListSource.size(), nodeDTOList.size());
        assertEquals(nodeListSource.get(0).getIpAddress(), nodeDTOList.get(0).getIpAddress());
    }

    @Test
    public void nodeDTOListToNodeList() {
        List<Node> nodeList = mapperFacade.mapAsList(nodeDTOListSource, Node.class);
        assertNotNull(nodeList);
        assertEquals(nodeDTOListSource.size(), nodeList.size());
        assertEquals(nodeDTOListSource.get(0).getIpAddress(), nodeList.get(0).getIpAddress());
    }

}

