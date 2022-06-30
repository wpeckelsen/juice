package nl.wessel.juice.C.Repository;

import nl.wessel.juice.B.BusinessLogic.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
