package se.infinera.metro.microservice.alarm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import se.infinera.metro.microservice.alarm.entity.Node;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;

import java.util.stream.StreamSupport;

@Slf4j
public class PollNodesRunner implements CommandLineRunner {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private NodeRepository nodeRepository;

    @Scheduled(initialDelay = 10000, fixedRate = 10000)
    public void run() {
        StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
                .forEach(Node::getIpAddress);

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
