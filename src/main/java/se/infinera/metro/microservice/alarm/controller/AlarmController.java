package se.infinera.metro.microservice.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.infinera.metro.microservice.alarm.entity.NodeAlarm;
import se.infinera.metro.microservice.alarm.service.NodeAlarmService;

import java.util.List;

@RestController
@RequestMapping("/alarms")
public class AlarmController {

    @Autowired
    NodeAlarmService nodeAlarmService;

    @RequestMapping(method = RequestMethod.GET)
    public List<List<NodeAlarm>> getAllNodesAlarms() {
        return nodeAlarmService.getAllNodesAlarms();
    }
}