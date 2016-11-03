package com.infinera.metro.service.alarm.service.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.stringtemplate.v4.ST;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Component
@Scope("prototype")
public class NodeConnection {
    @Getter(AccessLevel.NONE)
    @Autowired
    RestTemplate restTemplate;
    private int sessionId;
    private final Node node;

    public NodeConnection(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void requestLoginAndSetSessionId() {
        ResponseEntity<String> loginResponse = requestLogin();
        this.sessionId = getSessionId(loginResponse.getBody());
    }

    public List<Alarm> getAlarms() {
        checkSessionId();
        return restTemplate.exchange(
                getAlarmsUri(), //Contains session-id
                HttpMethod.GET,
                getHttpEntity(), new ParameterizedTypeReference<List<Alarm>>(){}).getBody();
    }

    void checkSessionId() {
        if(sessionId == 0) {
            requestLoginAndSetSessionId();
        } else {
            log.info("NodeConnection session-id {}", sessionId);
        }
    }

    ResponseEntity<String> requestLogin() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> loginResponse = restTemplate.exchange(getEnmLoginUri(), HttpMethod.GET, entity, String.class);
        if(loginResponse == null || loginResponse.getStatusCode() != HttpStatus.OK || loginResponse.getBody() == null) {
            throw new RuntimeException("Login to node failed. Login url: " + getEnmLoginUri());
        }
        return loginResponse;
    }

    int getSessionId(String responseBody) {
        Optional<Integer> sessionId = Pattern.compile("\\R").splitAsStream(responseBody)
                .filter(s -> s.contains("sessionId"))
                .map(s -> s.replaceFirst(".*?(\\d+).*", "$1"))
                .map(Integer::parseInt)
                .findFirst();
        if (sessionId.isPresent() && sessionId.get() != 0){
            return sessionId.get();
        } else {
            String errorMessage;
            if(sessionId.isPresent()) {
                errorMessage = "Login response from Node contained sessionId=0. Restart node.";
            } else  {
                errorMessage = "Login response from Node did not contain any sessionId";
            }
            throw new RuntimeException(errorMessage);        }
    }

    HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "sessionId="+sessionId);
        return new HttpEntity<String>(headers);
    }

    String getEnmLoginUri() {
        ST loginUrl = new ST("http://<ipaddress>:<port>/getLogin.asp?userName=<username>&password=<password>&oneTimeLogin=false");
        loginUrl.add("ipaddress", node.getIpAddress());
        loginUrl.add("port", node.getPort());
        loginUrl.add("username", node.getUserName());
        loginUrl.add("password", node.getPassword());
        return loginUrl.render();
    }

    String getAlarmsUri() {
        ST alarmsUrl = new ST("http://<ipaddress>:<port><alarmspath>");
        alarmsUrl.add("ipaddress", node.getIpAddress());
        alarmsUrl.add("port", node.getPort());
        alarmsUrl.add("alarmspath", "/mib/alarm/current/get.json");
        return alarmsUrl.render();
    }
}
