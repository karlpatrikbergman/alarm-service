package se.infinera.metro.microservice.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.infinera.metro.microservice.alarm.entity.Alarm;
import se.infinera.metro.microservice.alarm.service.NodeAlarmService2;

@RestController
@RequestMapping("/alarms")
public class NodeAlarmController {

    @Autowired
    private NodeAlarmService2 nodeAlarmService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Alarm> getAllBooks() {
//        try {
//            return nodeAlarmService.getAlarmList().stream()
//                    .map(id -> Alarm.builder().idFromEnm(id).build())
//                    .collect(Collectors.toList());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
;