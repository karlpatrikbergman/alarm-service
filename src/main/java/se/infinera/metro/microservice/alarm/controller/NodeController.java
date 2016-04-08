package se.infinera.metro.microservice.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.infinera.metro.microservice.alarm.controller.dto.NodeDTO;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/nodes")
public class NodeController {

    //Maybe add NodeService later?
    @Autowired
    NodeRepository nodeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<NodeDTO> getNodes() {
        //TODO: map Node to NodeDTO
        return Arrays.asList(
                NodeDTO.builder()
                        .ipAddress("172.17.0.2")
                        .port(80)
                        .userName("root")
                        .password("root")
                        .build(),
                NodeDTO.builder()
                        .ipAddress("172.17.0.3")
                        .port(80)
                        .userName("root")
                        .password("root")
                        .build()
        );
//        return StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
//                .collect(Collectors.toList());
    }

    /**
     * http://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
     *
     * @param ipAddress
     * @return
     */
    @RequestMapping(value = "/{ipAddress:.+}", method = RequestMethod.GET)
    public NodeDTO getNodeByIpAddress(@PathVariable String ipAddress) {
        //TODO: map Node to NodeDTO
        return NodeDTO.builder()
                .ipAddress("172.17.0.3")
                .port(80)
                .userName("root")
                .password("root")
                .build();
//        return StreamSupport.stream(nodeRepository.findByIpAddress(ipAddress).spliterator(), false)
//                .collect(Collectors.toList());
    }

}