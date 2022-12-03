package nl.wessel.juice.Repository;

import nl.wessel.juice.Model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
}
