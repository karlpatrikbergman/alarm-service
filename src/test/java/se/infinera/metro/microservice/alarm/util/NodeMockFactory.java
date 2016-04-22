package se.infinera.metro.microservice.alarm.util;

import se.infinera.metro.microservice.alarm.controller.dto.NodeDTO;
import se.infinera.metro.microservice.alarm.service.domain.Node;

import java.util.Arrays;
import java.util.List;

public final class NodeMockFactory {
    public static Node mockNode() {
        return Node.builder()
                .ipAddress("node-ipaddress")
                .port(8080)
                .userName("node-username")
                .password("node-password")
                .build();

    }

    public static NodeDTO mockNodeDTO() {
        return NodeDTO.builder()
                .ipAddress("node-ipaddress")
                .port(8080)
                .userName("node-username")
                .password("node-password")
                .build();
    }

    public static List<Node> mockNodeList() {
        return Arrays.asList(mockNode());
    }

    public static List<NodeDTO> mockNodeDTOList() {
        return Arrays.asList(mockNodeDTO());
    }
}
