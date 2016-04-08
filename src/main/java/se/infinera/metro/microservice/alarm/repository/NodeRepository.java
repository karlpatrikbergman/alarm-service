package se.infinera.metro.microservice.alarm.repository;

import org.springframework.data.repository.CrudRepository;
import se.infinera.metro.microservice.alarm.service.domain.Node;

import java.util.List;

public interface NodeRepository extends CrudRepository<Node, Long> {
    List<Node> findByIpAddress(String ipAddress);
}
