package com.infinera.metro.service.alarm.controller;

import com.infinera.metro.service.alarm.mapping.NodeMapper;
import com.infinera.metro.service.alarm.repository.NodeRepository;
import com.infinera.metro.service.alarm.service.domain.Node;
import com.infinera.metro.service.alarm.service.domain.NodeConnections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.infinera.metro.service.alarm.controller.dto.NodeDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
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

    /**
     * On how to have path variables containing dots (as ip address)
     *   http://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
     *
     * On what to return after delete and after request to delete something that does not exist:
     *   "The DELETE method is idempotent. This implies that the server must return response code 200 (OK) even if the
     *    server deleted the resource in a previous request. But in practice, implementing DELETE as an idempotent
     *    operation requires the server to keep track of all deleted resources.
     *    Otherwise, it can return a 404 (Not Found)."
     *    https://books.google.com/books?id=LDuzpQlVuG4C&pg=PA11&sa=X&ved=0ahUKEwi6x4-W9NnNAhXLCCwKHeL0Db8Q6AEIHDAA#v=onepage&q&f=false
     *
     * @param ipAddress
     */
    @RequestMapping(value = "/{ipAddress:.+}", method = RequestMethod.DELETE)
    public void removeNode(@PathVariable String ipAddress) {
        nodeRepository.delete(ipAddress);
        nodeConnections.deleteNodeConnection(ipAddress);

        log.debug("Deleted node with ip address {} (if existing)", ipAddress);
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