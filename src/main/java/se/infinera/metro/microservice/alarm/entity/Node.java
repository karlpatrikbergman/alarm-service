package se.infinera.metro.microservice.alarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
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

    @JsonIgnore
    @Transient
    private RestTemplate restTemplate = new RestTemplate();

    @Transient
    @JsonIgnore
    NodeConnection nodeConnection = new NodeConnection(this);

    @JsonIgnore
    public List<NodeAlarm> getAlarms() {
        nodeConnection.checkSession();
        return restTemplate.exchange(
                getAlarmsUri(),
                HttpMethod.GET,
                nodeConnection.getHttpEntity(),
                new ParameterizedTypeReference<List<NodeAlarm>>() {}).getBody();
    }

    String getAlarmsUri() {
        String alarmsGetJsonPath = "/mib/alarm/current/get.json";
        return "http://" + ipAddress + ":" + port + alarmsGetJsonPath;
    }

    String getEnmLoginUri() {
        String loginPath = "/getLogin.asp?userName=" + userName + "&password=" + password + "&oneTimeLogin=false";
        return "http://" + ipAddress + ":" + port + loginPath;
    }
}
