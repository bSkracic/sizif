package hr.bskracic.sizif.config;

import hr.bskracic.sizif.client.NodeWsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class NodeWsConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("hr.bskracic.sizif.wsdl");
        return marshaller;
    }

    @Bean
    public NodeWsClient nodeClient(Jaxb2Marshaller marshaller) {
        NodeWsClient client = new NodeWsClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
