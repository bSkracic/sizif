package hr.bskracic.sizif.controller;

import hr.bskracic.sizif.controller.dto.NetworkDto;
import hr.bskracic.sizif.controller.dto.ValidationType;
import hr.bskracic.sizif.service.NetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/network")
@RequiredArgsConstructor
public class NetworkController {

    private final NetworkService networkService;

    @PostMapping("/xsd")
    ResponseEntity<Object> createNetworkWithXsd(@RequestParam("xmlFile") MultipartFile xmlFile) throws JAXBException, IOException, SAXException {
        NetworkDto body = networkService.createNetwork(xmlFile, ValidationType.XSD);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/rng")
    ResponseEntity<NetworkDto> createNetworkWithRng(@RequestParam("xmlFile") MultipartFile xmlFile) throws JAXBException, IOException, SAXException {
        NetworkDto body = networkService.createNetwork(xmlFile, ValidationType.RNG);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
