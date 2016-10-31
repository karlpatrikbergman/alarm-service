package com.infinera.metro.service.alarm.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.infinera.metro.service.alarm.repository.NodeRepository;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class NodeConnections implements ApplicationListener<ContextRefreshedEvent>, InitializingBean, DisposableBean {
    private CopyOnWriteArrayList<NodeConnection> nodeConnections = new CopyOnWriteArrayList<>();
    @Autowired private ApplicationContext applicationContext;
    @Autowired private NodeRepository nodeRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
                .forEach(node -> log.debug((node.toString())));
        addNodeConnections();
        requestLoginAndSetSessionIdForAddedNodeConnections();

    }

    void addNodeConnections() {
        nodeConnections = StreamSupport.stream(nodeRepository.findAll().spliterator(), false)
                .map(this::createNodeConnection)
                .collect((Collectors.toCollection(CopyOnWriteArrayList::new)));
    }

    public void addNodeConnection(Node node) {
        NodeConnection nodeConnection = createNodeConnection(node);
        if(!nodeConnections.contains(nodeConnection)) {
            nodeConnections.add(createNodeConnection(node));
            log.debug("Added connection for node {}", node.toString());
        }
    }

    public void deleteNodeConnection(String ipAddress) {
        throw new RuntimeException("TODO: Implement this!!");
    }

    void requestLoginAndSetSessionIdForAddedNodeConnections() {
        nodeConnections.forEach(NodeConnection::requestLoginAndSetSessionId);
    }

    public List<Alarm> getAllNodesAlarms() {
        return nodeConnections.stream()
                .map(NodeConnection::getAlarms)
                .flatMap(Collection::stream)
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
        log.debug("added nodes: " + nodeRepository.count());
    }
}
