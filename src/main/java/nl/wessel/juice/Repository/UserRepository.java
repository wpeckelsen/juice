package nl.wessel.juice.Repository;

import nl.wessel.juice.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Customer, String> {
}
