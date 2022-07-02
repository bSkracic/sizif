package hr.bskracic.sizif.service;

import hr.bskracic.sizif.client.NodeWsClient;
import hr.bskracic.sizif.controller.dto.NodeDto;
import hr.bskracic.sizif.wsdl.GetNodesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final NodeWsClient nodeWsClient;

    public List<NodeDto> retrieveNodes(String name) throws Exception {

        GetNodesResponse response = nodeWsClient.getNodes();

        JAXBContext jaxbContext = JAXBContext.newInstance(GetNodesResponse.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.marshal(response, sw);

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(sw.toString());
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "//nodes/node[name = '" + name + "']";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        List<NodeDto> nodeDtos = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeDto nodeDto = new NodeDto();
            nodeDto.setId(Long.valueOf(node.getFirstChild().getNodeValue()));
            nodeDto.setName(node.getChildNodes().item(1).getNodeValue());
            nodeDto.setImage(node.getLastChild().getNodeValue());
            nodeDtos.add(nodeDto);
        }
        return nodeDtos;
    }



}
