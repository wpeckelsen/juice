package nl.wessel.juice.C.Repository;

import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainRepo extends JpaRepository<Domain, Long> {

}
