package nl.wessel.juice.C.Repository;

import nl.wessel.juice.B.BusinessLogic.Model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepo extends JpaRepository<Bid, Long> {

}
