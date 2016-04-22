package se.infinera.metro.microservice.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.infinera.metro.microservice.alarm.controller.dto.AlarmDTO;
import se.infinera.metro.microservice.alarm.mapping.AlarmMapper;
import se.infinera.metro.microservice.alarm.service.NodeAlarmService;

import java.util.List;

@RestController
@RequestMapping("/alarms")
public class AlarmController {

    @Autowired
    AlarmMapper alarmMapper;

    @Autowired
    NodeAlarmService nodeAlarmService;

    @RequestMapping(method = RequestMethod.GET)
    public List<List<AlarmDTO>> getAllNodesAlarms() {
        return alarmMapper.toAllNodesAlarmDTOs(nodeAlarmService.getAllNodesAlarms());
    }
}