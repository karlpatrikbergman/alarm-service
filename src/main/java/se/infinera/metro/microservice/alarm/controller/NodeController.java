package se.infinera.metro.microservice.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.infinera.metro.microservice.alarm.controller.dto.NodeDTO;
import se.infinera.metro.microservice.alarm.mapping.NodeMapper;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;
import se.infinera.metro.microservice.alarm.service.domain.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/nodes")
public class NodeController {

    //Maybe add NodeService later?
    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private NodeMapper nodeMapper;

    @RequestMapping(method = RequestMethod.POST)
    public NodeDTO addNode(@RequestBody NodeDTO nodeDTO) {
        Node node = nodeMapper.toNode(nodeDTO);
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
    public void deleteNode(@PathVariable long id) {
        nodeRepository.delete(id);
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