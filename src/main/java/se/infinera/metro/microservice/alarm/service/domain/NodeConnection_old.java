//package se.infinera.metro.microservice.alarm.service.domain;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.List;
//
//final class NodeConnection {
//    private RestTemplate restTemplate = new RestTemplate();
//    private String sessionId;
//    private final Node node;
//
//    NodeConnection(Node node) {
//        this.node = node;
//    }
//
//    List<Alarm> getAlarms() {
//        checkSession();
//        return restTemplate.exchange(
//                getAlarmsUri(), //Contains session-id
//                HttpMethod.GET,
//                getHttpEntity(), new ParameterizedTypeReference<List<Alarm>>(){}).getBody();
//    }
//
//    void checkSession() {
//        if(sessionId == null || sessionId.equals("0")) {
//            try {
//                sessionId = getSessionId(loginToNode());
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to login and get session id");
//            }
//        }
//    }
//
//    HttpResponse loginToNode() throws IOException {
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpGet request = new HttpGet(getEnmLoginUri());
//        HttpResponse response = client.execute(request);
//        if(response == null || response.getStatusLine().getStatusCode() != 200) {
//            throw new RuntimeException("Login to node failed!");
//        }
//        return response;
//    }
//
//    String getSessionId(HttpResponse httpResponse) throws IOException {
//        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
//        String line;
//        while ((line = rd.readLine()) != null) {
//            if(line.contains("sessionId")) {
//                //  .*? consumes initial non-digits(non-greedy)
//                //  (\\d+) Get the one or more available digits in a group!
//                //  .* Everything else (greedy).
//                String sessionId = line.replaceFirst(".*?(\\d+).*", "$1");
//                if (sessionId.equals("0")) {
//                    throw new RuntimeException("Retrieved session id = 0. Restart node!");
//                }
//                return sessionId;
//            }
//        }
//        throw new RuntimeException("No session id found in HttpResponse");
//    }
//
//    HttpEntity<String> getHttpEntity() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Cookie", "sessionId="+sessionId);
//        return new HttpEntity(headers);
//    }
//
//    String getAlarmsUri() {
//        String alarmsGetJsonPath = "/mib/alarm/current/get.json";
//        return "http://" + node.getIpAddress() + ":" + node.getPort() + alarmsGetJsonPath;
//    }
//
//    String getEnmLoginUri() {
//        String loginPath = "/getLogin.asp?userName=" + node.getUserName() + "&password=" + node.getPassword() + "&oneTimeLogin=false";
//        return "http://" + node.getIpAddress() + ":" + node.getPort() + loginPath;
//    }
//}
