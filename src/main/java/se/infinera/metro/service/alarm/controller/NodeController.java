package se.infinera.metro.service.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.infinera.metro.service.alarm.controller.dto.NodeDTO;
import se.infinera.metro.service.alarm.mapping.NodeMapper;
import se.infinera.metro.service.alarm.repository.NodeRepository;
import se.infinera.metro.service.alarm.service.domain.Node;
import se.infinera.metro.service.alarm.service.domain.NodeConnections;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/nodes")
public class NodeController {

    //Maybe add NodeService later?
    private final NodeRepository nodeRepository;
    private final NodeConnections nodeConnections;
    private final NodeMapper nodeMapper;

    @Autowired
    public NodeController(NodeRepository nodeRepository, NodeConnections nodeConnections, NodeMapper nodeMapper) {
        this.nodeRepository = nodeRepository;
        this.nodeConnections = nodeConnections;
        this.nodeMapper = nodeMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    public NodeDTO addNode(@RequestBody NodeDTO nodeDTO) {
        Node node = nodeMapper.toNode(nodeDTO);
        nodeConnections.addNodeConnection(node);
        node = nodeRepository.save(node);
        return nodeMapper.toNodeDTO(node);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public NodeDTO updateNode(@RequestBody NodeDTO nodeDTO) {
        Node node = nodeMapper.toNode(nodeDTO);
        node = nodeRepository.save(node);
        return nodeMapper.toNodeDTO(node);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<NodeDTO> getNodes() {
        List<Node> nodeList = StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return nodeMapper.toNodeDTOList(nodeList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteNode(@PathVariable String ipAddress) {
        nodeRepository.delete(ipAddress);
    }

    /**
     * http://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
     * @param ipAddress
     * @return
     */
    @RequestMapping(value = "/{ipAddress:.+}", method = RequestMethod.GET)
    public NodeDTO getNodeByIpAddress(@PathVariable String ipAddress) throws NotFoundException {
        Optional<Node> node = StreamSupport.stream(nodeRepository.findByIpAddress(ipAddress).spliterator(), false)
                .findFirst();
        if(node.isPresent()) {
            return nodeMapper.toNodeDTO(node.get());
        } else {
            throw new NotFoundException("Failed to find node with ip-address" + ipAddress);
        }
    }
}