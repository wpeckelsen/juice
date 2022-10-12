package nl.wessel.juice.C.Repository;

import nl.wessel.juice.B.BusinessLogic.Model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepo extends JpaRepository<Deal, Long> {

}
