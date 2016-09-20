package se.infinera.metro.microservice.alarm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.infinera.metro.microservice.alarm.mapping.AlarmMapper;
import se.infinera.metro.microservice.alarm.mapping.NodeMapper;
import se.infinera.metro.microservice.alarm.service.domain.Node;
import se.infinera.metro.microservice.alarm.service.domain.NodeConnection;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class AlarmApplication {

    public static void main(String args[]) {
        SpringApplication.run(AlarmApplication.class);
    }

    @Bean
    @Scope("prototype")
    public NodeConnection nodeConnection(Node node) {
        return new NodeConnection(node);
    }

    @Bean
    public PollNodesRunner schedulePollNodesRunner() {
        return new PollNodesRunner();
    }

    @Bean
    public AlarmMapper alarmMapper() {
        return AlarmMapper.INSTANCE;
    }

    @Bean
    public NodeMapper nodeMapper() {
        return NodeMapper.INSTANCE;
    }
}
