package se.infinera.metro.microservice.alarm.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@ToString(exclude={"alarms"})
@Builder
@Entity
public class Node {
    @Id
    @GeneratedValue
    private Long id;
    private String ipAddress;
    private int port;
    private String userName;
    private String password;
    private String sessionId;

    @Transient
    @Autowired
    private RestTemplate restTemplate;

    @OneToMany(mappedBy = "node")
    private List<Alarm> alarms;

    public boolean originatesFrom(Alarm alarm) {
        return alarms.contains(alarm);
    }


}
