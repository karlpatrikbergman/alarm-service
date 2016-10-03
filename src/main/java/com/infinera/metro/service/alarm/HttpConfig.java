package com.infinera.metro.service.alarm;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfig {
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public RestTemplate restTemplate() {
        return  new RestTemplate(httpRequestFactory());
    }

    @Bean
    public HttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        HttpClient defaultHttpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).build();
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
        return defaultHttpClient;
    }

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


        //My personal note: Another example of what the code above is doing?
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        factory.setReadTimeout(0);
//        factory.setConnectTimeout(0);
//        return new RestTemplate(factory);
//    }



    /**
     * * My personal note:
     * I can't recall the difference between the code above and the code below. At a quick look they seem to do the
     * same. I'll recap on this later.
     *
     * By default RestTemplate uses SimpleClientHttpRequestFactory which depends on default configuration of
     * HttpURLConnection. If you want to have pooling use HttpComponentsClientHttpRequestFactory - it has a
     * connection pooling configuration which SimpleClientHttpRequestFactory does not have.
     *
     * http://stackoverflow.com/questions/25698072/simpleclienthttprequestfactory-vs-httpcomponentsclienthttprequestfactory-for-htt
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
}
