package se.infinera.metro.microservice.alarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import se.infinera.metro.microservice.alarm.mapping.AlarmMapper;
import se.infinera.metro.microservice.alarm.mapping.NodeMapper;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
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
