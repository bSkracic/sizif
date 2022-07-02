package hr.bskracic.sizif.repository.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "network")
@Data
public class Network {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
