package hr.bskracic.sizif.repository;

import hr.bskracic.sizif.repository.model.Network;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkRepository extends CrudRepository<Network, Long> {
}
