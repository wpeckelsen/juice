package nl.wessel.juice.Repository;

import nl.wessel.juice.Model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Long> {

}
