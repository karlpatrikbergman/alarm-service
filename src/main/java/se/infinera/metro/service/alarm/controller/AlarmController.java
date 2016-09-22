package se.infinera.metro.service.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.infinera.metro.service.alarm.controller.dto.AlarmDTO;
import se.infinera.metro.service.alarm.mapping.AlarmMapper;
import se.infinera.metro.service.alarm.service.NodeAlarmService;

import java.util.List;

@RestController
@RequestMapping("/alarms")
public class AlarmController {

    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired
    private NodeAlarmService nodeAlarmService;

    @RequestMapping(method = RequestMethod.GET)
    public List<List<AlarmDTO>> getAllNodesAlarms() {
        return alarmMapper.toAllNodesAlarmDTOs(nodeAlarmService.getAllNodesAlarms());
    }

    @RequestMapping(value = "/{ipAddress:.+}", method = RequestMethod.GET)
    public List<AlarmDTO> getNodeAlarms(@PathVariable String ipAddress) {
        return alarmMapper.toAlarmDTOList(nodeAlarmService.getAlarms(ipAddress));
    }
}