package hr.bskracic.sizif.controller.dto;

import hr.bskracic.sizif.repository.model.Node;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "nodes")
@XmlAccessorType (XmlAccessType.FIELD)
public class NodeDto {
    private Long id;
    private String name;
    private String image;
    private String containerId;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public static Node toNode(NodeDto nodeDto) {
        Node node = new Node();
        node.setName(nodeDto.getName());
        node.setImage(nodeDto.getImage());
        return node;
    }
}
