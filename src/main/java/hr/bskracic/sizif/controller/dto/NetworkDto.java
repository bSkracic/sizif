package hr.bskracic.sizif.controller.dto;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "networkDto")
@XmlAccessorType (XmlAccessType.FIELD)
public class NetworkDto {
    private Long id;
    private String name;
    @XmlElementWrapper(name="nodes")
    @XmlElement(name="node")
    private List<NodeDto> nodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDto> nodes) {
        this.nodes = nodes;
    }
}
