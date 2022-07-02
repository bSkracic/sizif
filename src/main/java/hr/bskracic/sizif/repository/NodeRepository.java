package hr.bskracic.sizif.repository;

import hr.bskracic.sizif.repository.model.Node;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends CrudRepository<Node, Long> {
}
