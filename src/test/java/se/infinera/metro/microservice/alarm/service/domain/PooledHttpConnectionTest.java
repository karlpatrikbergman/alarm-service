package se.infinera.metro.microservice.alarm.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import se.infinera.metro.microservice.alarm.Application;
import se.infinera.metro.microservice.alarm.HttpConfig;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, HttpConfig.class})
@WebIntegrationTest
@Slf4j
public class PooledHttpConnectionTest {

    @Autowired
    private RestTemplate restTemplate;
    private Node node;

    @Before
    public void setup() {
        node = Node.builder()
                .ipAddress("172.17.0.2")
                .port(80)
                .userName("root")
                .password("root")
                .build();
    }
    @Test
    public void connect() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getEnmLoginUri(), HttpMethod.GET, entity, String.class);

        String responseBody = responseEntity.getBody();
        HttpStatus httpStatus = responseEntity.getStatusCode();

        assertEquals(HttpStatus.OK, httpStatus);
        assertNotNull(responseBody);

        Optional<Integer> sessionId = Pattern.compile("\\R").splitAsStream(responseBody)
                .filter(s -> s.contains("sessionId"))
                .map(s -> s.replaceFirst(".*?(\\d+).*", "$1"))
                .map(Integer::parseInt)
                .findFirst();
        if(sessionId.isPresent())
            System.out.println(sessionId.get());
    }

    String getEnmLoginUri() {
        String loginPath = "/getLogin.asp?userName=" + node.getUserName() + "&password=" + node.getPassword() + "&oneTimeLogin=false";
        return "http://" + node.getIpAddress() + ":" + node.getPort() + loginPath;
    }
}
