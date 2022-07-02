package hr.bskracic.sizif.service;


import com.thaiopensource.validate.SchemaReader;
import com.thaiopensource.validate.ValidationDriver;
import com.thaiopensource.validate.auto.AutoSchemaReader;
import hr.bskracic.sizif.controller.dto.NetworkDto;
import hr.bskracic.sizif.controller.dto.NodeDto;
import hr.bskracic.sizif.controller.dto.ValidationType;
import hr.bskracic.sizif.controller.dto.XmlValidationException;
import hr.bskracic.sizif.repository.NetworkRepository;
import hr.bskracic.sizif.repository.NodeRepository;
import hr.bskracic.sizif.repository.model.Network;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.validation.XmlValidatorFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NetworkService {
    private final NodeRepository nodeRepository;
    private final NetworkRepository networkRepository;

    public NetworkDto createNetwork(MultipartFile xmlFile, ValidationType validationType) throws JAXBException, SAXException, IOException {
        NetworkDto networkDto = new NetworkDto();
        switch (validationType){
            case XSD:
                networkDto = validateXsd(xmlFile);
                break;
            case RNG:
                networkDto = validateWithRng(xmlFile);
        }
        Network networkToSave = new Network();
        networkToSave.setName(networkDto.getName());

        networkDto.setId(networkRepository.save(networkToSave).getId());
        networkDto.setNodes(networkDto.getNodes().stream().map(n -> {
            n.setId(nodeRepository.save(NodeDto.toNode(n)).getId());
            return n;
        }).collect(Collectors.toList()));

        return networkDto;
    }

    private NetworkDto validateXsd(MultipartFile xmlFile) throws SAXException, IOException, XmlValidationException {
        JAXBContext jaxbContext;

        //Get JAXBContext
        try {
            jaxbContext = JAXBContext.newInstance(NetworkDto.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        // Create Unmarshaller
        Unmarshaller jaxbUnmarshaller = null;
        try {
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema networkSchema = sf.newSchema(new ClassPathResource("network.xsd").getURL());
        jaxbUnmarshaller.setSchema(networkSchema);

//        Unmarshal xml file
        NetworkDto value = null;
        try {
            value = (NetworkDto) jaxbUnmarshaller.unmarshal(xmlFile.getInputStream());
        } catch (JAXBException e) {
            throw new XmlValidationException(e.getLinkedException().getMessage());
        }

        return value;
    }

    private NetworkDto validateWithRng(MultipartFile xmlFile) throws IOException {

        SchemaReader sr = new AutoSchemaReader();
        ValidationDriver driver = new ValidationDriver(sr);
        InputSource inRng = ValidationDriver.fileInputSource(new File(new ClassPathResource("network.xsd").getURI()));
        inRng.setEncoding("UTF-8");
        try {
            driver.loadSchema(inRng);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        try {
            driver.validate(new InputSource(xmlFile.getInputStream()));
        } catch (SAXException e) {
            throw new XmlValidationException(e.getMessage());
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(NetworkDto.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (NetworkDto) jaxbUnmarshaller.unmarshal(xmlFile.getInputStream());
        } catch (JAXBException e) {
            throw new XmlValidationException(e.getMessage());
        }
    }

}
