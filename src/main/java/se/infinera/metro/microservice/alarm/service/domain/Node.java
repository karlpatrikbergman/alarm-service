package se.infinera.metro.microservice.alarm.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.NonFinal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * TODO: Make ip address and port key in node table. Skip id.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
@Builder
@Entity
public class Node {
    @NonFinal
    @Id
    @GeneratedValue
    private long id;
    private String ipAddress;
    private int port;
    private String userName;
    private String password;

    @Transient
    @JsonIgnore
    private final NodeConnection nodeConnection = new NodeConnection(this);

    @JsonIgnore
    public List<Alarm> getAlarms() {
        return nodeConnection.getAlarms();
    }
}
