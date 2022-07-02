package hr.bskracic.sizif.client;

import hr.bskracic.sizif.wsdl.GetNodesRequest;
import hr.bskracic.sizif.wsdl.GetNodesResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class NodeWsClient extends WebServiceGatewaySupport {
    public GetNodesResponse getNodes() {
        GetNodesRequest request = new GetNodesRequest();
        GetNodesResponse response = (GetNodesResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/nodes", request,
                        new SoapActionCallback(
                                "http://spring.io/guides/gs-producing-web-service/getNodesRequest"));
        return response;
    }

}
