package se.infinera.metro.microservice.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.infinera.metro.microservice.alarm.entity.Node;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/nodes")
public class NodeController {

    //Maybe add NodeService later?
    @Autowired
    NodeRepository nodeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Node> getNodes() {
        return StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * http://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
     *
     * @param ipAddress
     * @return
     */
    @RequestMapping(value = "/{ipAddress:.+}", method = RequestMethod.GET)
    public List<Node> getNodeByIpAddress(@PathVariable String ipAddress) {
        return StreamSupport.stream(nodeRepository.findByIpAddress(ipAddress).spliterator(), false)
                .collect(Collectors.toList());
    }

}