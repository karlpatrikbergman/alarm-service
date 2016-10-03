package com.infinera.metro.service.alarm.controller;

import com.infinera.metro.service.alarm.mapping.AlarmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.infinera.metro.service.alarm.controller.dto.AlarmDTO;
import com.infinera.metro.service.alarm.service.NodeAlarmService;

import java.util.List;

@RestController
@RequestMapping("/alarms")
public class AlarmController {

    private final AlarmMapper alarmMapper;
    private final NodeAlarmService nodeAlarmService;

    @Autowired
    public AlarmController(NodeAlarmService nodeAlarmService, AlarmMapper alarmMapper) {
        this.nodeAlarmService = nodeAlarmService;
        this.alarmMapper = alarmMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<List<AlarmDTO>> getAllNodesAlarms() {
        return alarmMapper.toAllNodesAlarmDTOs(nodeAlarmService.getAllNodesAlarms());
    }

    @RequestMapping(value = "/{ipAddress:.+}", method = RequestMethod.GET)
    public List<AlarmDTO> getNodeAlarms(@PathVariable String ipAddress) {
        return alarmMapper.toAlarmDTOList(nodeAlarmService.getAlarms(ipAddress));
    }
}