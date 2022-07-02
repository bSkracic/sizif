package hr.bskracic.sizif.service;

import hr.bskracic.sizif.client.NodeWsClient;
import hr.bskracic.sizif.controller.dto.NodeDto;
import hr.bskracic.sizif.wsdl.GetNodesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NodeService {

    private final NodeWsClient nodeWsClient;

    public List<NodeDto> retrieveNodes(String name) {

        GetNodesResponse response = nodeWsClient.getNodes();

        JAXBContext jaxbContext = null;
        Marshaller marshaller = null;
        try {
            jaxbContext = JAXBContext.newInstance(GetNodesResponse.class);
            marshaller = jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            log.error(e.getMessage());
        }

        String fileName = LocalDateTime.now().toString();
        File tempFile = null;
        try {
            tempFile = File.createTempFile(fileName, ".xml");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(response, tempFile);
        } catch (JAXBException e) {
            log.error(e.getMessage());
        }


        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error(e.getMessage());
        }
        Document xmlDocument = null;
        try {
            xmlDocument = builder.parse(new FileInputStream(tempFile));
        } catch (SAXException | IOException ex) {
            log.error(ex.getMessage());
        }

        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "//getNodesResponse/node[name = '" + name + "']";
        NodeList nodeList = null;
        try {
            nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            log.error(e.getMessage());
        }

        List<NodeDto> nodeDtos = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList nodelist1 = node.getChildNodes();
            NodeDto nodeDto = new NodeDto();
            for (int j = 0; j < nodelist1.getLength(); j++) {
                Node child = nodelist1.item(j);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    switch (child.getNodeName()) {
                        case "id":
                            nodeDto.setId(child.getFirstChild().getTextContent() == null ? null : Long.valueOf(child.getTextContent()));
                            break;
                        case "name":
                            nodeDto.setName(child.getTextContent());
                            break;
                        case "image":
                            nodeDto.setImage(child.getTextContent());
                            break;
                        case "containerId":
                            nodeDto.setContainerId(child.getTextContent());
                            break;
                    }
                }
            }
            nodeDtos.add(nodeDto);
        }
        return nodeDtos;

//        return response.getNodeList().stream().map(r -> {
//            NodeDto nodeDto = new NodeDto();
//            nodeDto.setId(r.getId());
//            nodeDto.setName(r.getName());
//            nodeDto.setImage(r.getImage());
////            nodeDto.setContainerId(r.getContainerId());
//            return nodeDto;
//        }).collect(Collectors.toList());
    }

}
