package com.infinera.metro.service.alarm;

import com.infinera.metro.service.alarm.service.domain.NodeConnections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import com.infinera.metro.service.alarm.repository.AlarmRepository;
import com.infinera.metro.service.alarm.service.domain.Alarm;

import java.util.List;

@Slf4j
public class PollNodesRunner implements CommandLineRunner {

    @Autowired
    private NodeConnections nodeConnections;
    @Autowired
    private AlarmRepository alarmRepository;

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void run() {
        List<Alarm> alarmList = nodeConnections.getAllNodesAlarms();
        alarmRepository.save(alarmList);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
