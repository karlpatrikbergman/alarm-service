package se.infinera.metro.service.alarm.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.infinera.metro.service.alarm.repository.AlarmRepository;
import se.infinera.metro.service.alarm.repository.NodeRepository;
import se.infinera.metro.service.alarm.service.domain.Alarm;
import se.infinera.metro.service.alarm.service.domain.Node;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NodeAlarmService {

    protected final NodeRepository nodeRepository;
    protected final AlarmRepository alarmRepository;

    @Autowired
    public NodeAlarmService(NodeRepository nodeRepository, AlarmRepository alarmRepository) {
        this.nodeRepository = nodeRepository;
        this.alarmRepository = alarmRepository;

    }

    /**
     * TODO:
     * - Make ENM REST api return non-reversed ip address in alarm
     * - I would like to move behaviour getAlarms into Node. See comment in Node class.
     */
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