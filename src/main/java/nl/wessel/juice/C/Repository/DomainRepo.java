package nl.wessel.juice.C.Repository;

import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepo extends JpaRepository<Domain, Long> {

}
