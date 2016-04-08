package se.infinera.metro.microservice.alarm;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import se.infinera.metro.microservice.alarm.service.domain.Node;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;

import java.util.Arrays;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 15)
public class PopulateDatabaseRunner implements CommandLineRunner {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private NodeRepository nodeRepository;

    @Override
    public void run(String... args) throws Exception {
        nodeRepository.save(
                Arrays.asList(
                        Node.builder()
                                .ipAddress("172.17.0.2")
                                .port(80)
                                .userName("root")
                                .password("root")
                                .build(),
                        Node.builder()
                                .ipAddress("172.17.0.3")
                                .port(80)
                                .userName("root")
                                .password("root")
                                .build()
                )
        );

    }
}
