package se.infinera.metro.service.alarm.util;

import org.apache.commons.math3.random.RandomDataGenerator;
import se.infinera.metro.service.alarm.controller.dto.NodeDTO;
import se.infinera.metro.service.alarm.service.domain.Node;

import java.util.Arrays;
import java.util.List;

public final class NodeMockFactory {
    private static final RandomIpGenerator ipGenerator = RandomIpGenerator.INSTANCE;
    public static Node mockNode() {
        return Node.builder()
                .ipAddress(ipGenerator.getRandomIpAddress())
                .port(new RandomDataGenerator().nextInt(0,9999))
                .userName("test-username")
                .password("test-password")
                .build();
    }

    public static NodeDTO mockNodeDTO() {
        return NodeDTO.builder()
                .ipAddress(ipGenerator.getRandomIpAddress())
                .port(new RandomDataGenerator().nextInt(0,9999))
                .userName("test-username")
                .password("test-password")
                .build();
    }

    public static List<Node> mockNodeList() {
        return Arrays.asList(mockNode());
    }

    public static List<NodeDTO> mockNodeDTOList() {
        return Arrays.asList(mockNodeDTO());
    }
}
