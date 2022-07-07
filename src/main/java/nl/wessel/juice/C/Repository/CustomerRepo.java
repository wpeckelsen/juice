package nl.wessel.juice.C.Repository;
import nl.wessel.juice.B.BusinessLogic.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, String> {
}
