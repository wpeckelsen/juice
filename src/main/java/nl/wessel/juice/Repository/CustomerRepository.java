package nl.wessel.juice.Repository;

import nl.wessel.juice.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
