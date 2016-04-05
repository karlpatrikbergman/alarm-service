package se.infinera.metro.microservice.alarm.repository;

import org.springframework.data.repository.CrudRepository;
import se.infinera.metro.microservice.alarm.entity.Node;

public interface NodeRepository extends CrudRepository<Node, Long> {
    Node findNodeByIpAddress(String ipAddress);
}
