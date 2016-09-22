package se.infinera.metro.service.alarm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//@PropertySource("classpath:/tomcat.https.properties")
//@EnableConfigurationProperties(WebConfiguration.TomcatSslConnectorProperties.class)
public class WebConfiguration extends WebMvcConfigurerAdapter {

    /**
     * ATTENTION!
     * For production environment, you probably don’t want to expose the entire
     * classpath as a static resource!
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/internal/**")
                .addResourceLocations("classpath:/");
    }

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    /** HTTPS related **/

//    @Bean
//    public EmbeddedServletContainerFactory servletContainer(TomcatSslConnectorProperties properties) {
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//        tomcat.addAdditionalTomcatConnectors(createSslConnector(properties));
//        return tomcat;
//    }
//
//    private Connector createSslConnector(TomcatSslConnectorProperties properties) {
//        Connector connector = new Connector();
//        properties.configureConnector(connector);
//        return connector;
//    }
//
//    @Data
//    @ConfigurationProperties(prefix = "custom.tomcat.https")
//    public static class TomcatSslConnectorProperties {
//        private Integer port;
//        private Boolean ssl = true;
//        private Boolean secure = true;
//        private String scheme = "https";
//        private File keystore;
//        private String keystorePassword;
//
//
//        public void configureConnector(Connector connector) {
//            if (port != null)
//                connector.setPort(port);
//            if (secure != null)
//                connector.setSecure(secure);
//            if (scheme != null)
//                connector.setScheme(scheme);
//            if (ssl != null)
//                connector.setProperty("SSLEnabled", ssl.toString());
//            if (keystore != null && keystore.exists()) {
//                connector.setProperty("keystoreFile",
//                        keystore.getAbsolutePath());
//                connector.setProperty("keystorePassword", keystorePassword);
//            }
//        }
//    }
}
