package hr.bskracic.sizif.controller;

import hr.bskracic.sizif.client.NodeWsClient;
import hr.bskracic.sizif.client.SvarogRpcClient;
import hr.bskracic.sizif.controller.dto.NodeDto;
import hr.bskracic.sizif.wsdl.Node;
import hr.bskracic.sizif.service.NodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/node")
@RequiredArgsConstructor
public class NodeController {

    private final NodeService nodeService;
    private final SvarogRpcClient svarogRpcClient;

    private final NodeWsClient nodeWsClient;

    @GetMapping
    private ResponseEntity<Object> getNodes(@RequestParam String name)  {

        return ResponseEntity.status(HttpStatus.OK).body(nodeService.retrieveNodes(name));
//        return ResponseEntity.status(HttpStatus.OK).body(nodeWsClient.getNodes());
    }

    @PostMapping
    private ResponseEntity<Object> createNodeContainer() {
        return ResponseEntity.status(HttpStatus.OK).body(svarogRpcClient.createContainer(1L, "novo", "busybox"));
    }
}
