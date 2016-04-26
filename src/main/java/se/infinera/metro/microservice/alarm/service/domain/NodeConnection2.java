package se.infinera.metro.microservice.alarm.service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

//@Component
public class NodeConnection2 {
    @Autowired
    private RestTemplate restTemplate;
    private int sessionId;
    private final Node node;

    public NodeConnection2(Node node) {
        this.node = node;
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
            ResponseEntity<String> loginResponse = loginToNode();
            this.sessionId = getSessionId(loginResponse.getBody());
        }
    }

    public ResponseEntity<String> loginToNode() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> loginResponse = restTemplate.exchange(getEnmLoginUri(), HttpMethod.GET, entity, String.class);
        if(loginResponse == null || loginResponse.getStatusCode() != HttpStatus.OK || loginResponse.getBody() == null) {
            throw new RuntimeException("Login to node failed. Node: " + node);
        }
        return loginResponse;
    }

    int getSessionId(String responseBody) {
        Optional<Integer> sessionId = Pattern.compile("\\R").splitAsStream(responseBody)
                .filter(s -> s.contains("sessionId"))
                .map(s -> s.replaceFirst(".*?(\\d+).*", "$1"))
                .map(Integer::parseInt)
                .findFirst();
        if (sessionId.isPresent())
            return sessionId.get();
        else {
            throw new RuntimeException("Failed to get session id from login response");
        }
    }

    HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "sessionId="+sessionId);
        return new HttpEntity(headers);
    }

    String getEnmLoginUri() {
        String loginPath = "/getLogin.asp?userName=" + node.getUserName() + "&password=" + node.getPassword() + "&oneTimeLogin=false";
        return "http://" + node.getIpAddress() + ":" + node.getPort() + loginPath;
    }

    String getAlarmsUri() {
        String alarmsGetJsonPath = "/mib/alarm/current/get.json";
        return "http://" + node.getIpAddress() + ":" + node.getPort() + alarmsGetJsonPath;
    }
}
