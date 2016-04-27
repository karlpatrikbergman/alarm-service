package se.infinera.metro.microservice.alarm.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.infinera.metro.microservice.alarm.repository.AlarmRepository;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;
import se.infinera.metro.microservice.alarm.service.domain.Alarm;
import se.infinera.metro.microservice.alarm.service.domain.Node;

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
                .map(Node::getIpAddress)
                .map(this::reverseIpAddress)
                .map(alarmRepository::findByAlarmNeIpAddress)
                .collect(Collectors.toList());
    }

    public List<Alarm> getAlarms(String nodeIpAddress) {
        return alarmRepository.findByAlarmNeIpAddress(reverseIpAddress(nodeIpAddress));
    }

    /**
     * Ip-address in alarms retrieved from Nodes ('Alarm.alarmNeIpAddress') is reversed, i.e the groups are
     * in reversed order. To find alarms in AlarmRepository given a Nodes ip-address we must reverse it.
     * @param ipAddress Ip-address from Node (127.0.0.1)
     * @return Ip-address with reversed order of groups (1.0.0.127)
     */
    private String reverseIpAddress(String ipAddress) {
        return StringUtils.reverseDelimited(ipAddress, '.');
    }
}