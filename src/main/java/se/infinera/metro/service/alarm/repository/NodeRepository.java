package se.infinera.metro.service.alarm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import se.infinera.metro.service.alarm.service.domain.Node;

import java.util.List;

@RepositoryRestResource
public interface NodeRepository extends CrudRepository<Node, String> {
    List<Node> findByIpAddress(String ipAddress);
}
