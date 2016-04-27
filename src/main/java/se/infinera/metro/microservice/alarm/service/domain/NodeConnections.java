package se.infinera.metro.microservice.alarm.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.infinera.metro.microservice.alarm.repository.NodeRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class NodeConnections implements ApplicationListener<ContextRefreshedEvent>, InitializingBean, DisposableBean {
    private List<NodeConnection> nodeConnections;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NodeRepository nodeRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        addNodeConnections();
        requestLoginAndSetSessionIdForAddedNodeConnections();

    }

    void addNodeConnections() {
        nodeConnections = StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
                .map(this::createNodeConnection)
                .collect(Collectors.toList());
    }

    void requestLoginAndSetSessionIdForAddedNodeConnections() {
        nodeConnections.stream()
                .forEach(nodeConnection -> nodeConnection.requestLoginAndSetSessionId());
    }

    public List<Alarm> getAllNodesAlarms() {
        return nodeConnections.stream()
                .map(nodeConnection -> nodeConnection.getAlarms())
                .flatMap(l -> l.stream())
                .collect(Collectors.toList());
    }

    private NodeConnection createNodeConnection(Node node) {
        return applicationContext.getBean(NodeConnection.class, node);
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("ARE WE READY???");
        System.out.println("added nodes: " + nodeRepository.count());
    }

//    public void foo() {
//        List<Alarm> alarmList = StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
//                .forEach();
////                .map(node -> node.getIpAddress())
////                .map(ipAddress -> alarmRepository.findByAlarmNeIpAddress(ipAddress))
////                .flatMap(l -> l.stream())
////                .collect(Collectors.toList());
////        log.debug("All nodes alarms list size {}", alarmList.size());
//    }
}
