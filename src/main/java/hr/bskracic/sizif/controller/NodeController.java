package hr.bskracic.sizif.controller;

import hr.bskracic.sizif.client.NodeWsClient;
import hr.bskracic.sizif.controller.dto.NodeDto;
import hr.bskracic.sizif.service.NodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/node")
@RequiredArgsConstructor
public class NodeController {

    private final NodeService nodeService;

    @GetMapping
    private ResponseEntity<List<NodeDto>> getNodes(@RequestParam String name) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(nodeService.retrieveNodes(name));
    }
}
