package se.infinera.metro.microservice.alarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.infinera.metro.microservice.alarm.repository.AlarmRepository;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;
import se.infinera.metro.microservice.alarm.service.domain.Alarm;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NodeAlarmService {

    @Autowired
    protected NodeRepository nodeRepository;

    @Autowired
    protected AlarmRepository alarmRepository;

    public List<List<Alarm>> getAllNodesAlarms() {
        return StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
                .map(node -> node.getIpAddress())
                .map(ipAddress -> alarmRepository.findByAlarmNeIpAddress(ipAddress))
                .collect(Collectors.toList());
    }

    public List<Alarm> getAlarms(String nodeIpAddress) {
        return alarmRepository.findByAlarmNeIpAddress(nodeIpAddress);
    }
}