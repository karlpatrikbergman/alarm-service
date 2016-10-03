package com.infinera.metro.service.alarm.mapping;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import com.infinera.metro.service.alarm.controller.dto.NodeDTO;
import com.infinera.metro.service.alarm.service.domain.Node;

import java.util.List;

public enum NodeMapper {
    INSTANCE;

    private static final BoundMapperFacade<Node, NodeDTO> boundMapperFacade;
    private static final MapperFacade mapperFacade;

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Node.class, NodeDTO.class)
                .exclude("nodeConnection")
                .byDefault()
                .register();
        boundMapperFacade = mapperFactory.getMapperFacade(Node.class, NodeDTO.class);
        mapperFacade = mapperFactory.getMapperFacade();
    }

    public NodeDTO toNodeDTO(Node node) {
        return boundMapperFacade.map(node);
    }

    public Node toNode(NodeDTO nodeDTO) {
        return boundMapperFacade.mapReverse(nodeDTO);
    }

    public List<NodeDTO> toNodeDTOList(List<Node> nodeList) {
        return mapperFacade.mapAsList(nodeList, NodeDTO.class);
    }

    public List<Node> toNodeList(List<NodeDTO> nodeDTOList) {
        return mapperFacade.mapAsList(nodeDTOList, Node.class);
    }
}
