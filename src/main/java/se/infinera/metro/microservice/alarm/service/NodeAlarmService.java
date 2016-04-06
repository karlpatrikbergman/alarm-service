package se.infinera.metro.microservice.alarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.infinera.metro.microservice.alarm.entity.Node;
import se.infinera.metro.microservice.alarm.entity.NodeAlarm;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NodeAlarmService {

    @Autowired
    protected NodeRepository nodeRepository;

    public List<List<NodeAlarm>> getAllNodesAlarms() {
        return StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
                .map(Node::getAlarms)
                .collect(Collectors.toList());
    }
}