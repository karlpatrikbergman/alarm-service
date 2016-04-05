//package se.infinera.metro.microservice.alarm.service;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.List;
//
//@Service
//public class NodeAlarmService {
//
//    @Autowired
//    protected RestTemplate restTemplate;
//
//
//
//    private final String userName = "root";
//    private final String password = "";
//    private final String nodeHost = "172.17.0.2";
//    private final int port = 80;
//    private final String loginPath = "/getLogin.asp?userName=" + userName + "&password=" + password + "&oneTimeLogin=false";
//    private final String alarmsPath = "/mib/alarm/current/list.json";
//    private final String loginUrl = "http://" + nodeHost + ":" + port + loginPath;
//    private final String alarmsUrl = "http://" + nodeHost + ":" + port + alarmsPath;
//    private String sessionId;
//
//    public List<String> getAlarmList() throws IOException {
//        if(sessionId == null) {
//            sessionId = getSessionId(loginToNode());
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Cookie", "sessionId="+sessionId);
//        HttpEntity<String> entity = new HttpEntity(headers);
//        return restTemplate.exchange(
//            alarmsUrl,
//            HttpMethod.GET,
//            entity,
//            new ParameterizedTypeReference<List<String>>() {}).getBody();
//    }
//
//    private HttpResponse loginToNode() throws IOException {
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpGet request = new HttpGet(loginUrl);
//        HttpResponse response = client.execute(request);
//        if(response == null || response.getStatusLine().getStatusCode() != 200) {
//            throw new RuntimeException("Login to node failed!");
//        }
//        return response;
//    }
//
//    private static String getSessionId(HttpResponse httpResponse) throws IOException {
//        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
//        StringBuffer result = new StringBuffer();
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
//        throw new RuntimeException("No session id found");
//    }
//}
