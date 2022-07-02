package hr.bskracic.sizif.config;

import hr.bskracic.sizif.client.SvarogRpcClient;
import org.apache.xmlrpc.XmlRpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class SvarogRpcConfig {
    @Value("${hr.bskracic.sizif.rpc.url}")
    private String rpcUrl;


    @Bean
    public SvarogRpcClient svarogRpcClient() throws MalformedURLException {
        XmlRpcClient xmlRpcClient = new XmlRpcClient(rpcUrl);
        return new SvarogRpcClient(xmlRpcClient);
    }
}
