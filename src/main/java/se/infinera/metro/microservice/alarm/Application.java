package se.infinera.metro.microservice.alarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.infinera.metro.microservice.alarm.mapping.AlarmMapper;
import se.infinera.metro.microservice.alarm.mapping.NodeMapper;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    /**
     * By default RestTemplate uses SimpleClientHttpRequestFactory which depends on default configuration of
     * HttpURLConnection.
     * If you want to use HttpComponentsClientHttpRequestFactory - it has a connection pooling configuration which
     * SimpleClientHttpRequestFactory does not have.
     *
     * http://stackoverflow.com/questions/25698072/simpleclienthttprequestfactory-vs-httpcomponentsclienthttprequestfactory-for-htt
     *
     * http://vincentdevillers.blogspot.se/2013/02/configure-best-spring-resttemplate.html
     */
//    @Bean
//    public RestTemplate restTemplate() {
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        requestFactory.setReadTimeout(5000);
//        requestFactory.setConnectTimeout(5000);
//
//        return new RestTemplate(requestFactory);
//    }

    /**
     * MKT LOVANDE!
     * http://stackoverflow.com/questions/29959772/spring-configurable-high-performance-metered-http-client-instances
     *
     * @return
     */
//    @Bean
//    public RestTemplate restTemplate() throws Exception {
//        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
//        SSLConnectionSocketFactory sslConnectionSocketFactory =
//                new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
//        HttpClient httpClient = HttpClientBuilder.create(). useSystemProperties().setSSLSocketFactory(sslConnectionSocketFactory).build();
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        factory.setReadTimeout(0);
//        factory.setConnectTimeout(0);
//        return new RestTemplate(factory);
//    }

    @Bean
    public PollNodesRunner schedulePollNodesRunner() {
        return new PollNodesRunner();
    }

    @Bean
    public AlarmMapper alarmMapper() {
        return AlarmMapper.INSTANCE;
    }

    @Bean
    public NodeMapper nodeMapper() {
        return NodeMapper.INSTANCE;
    }
}
