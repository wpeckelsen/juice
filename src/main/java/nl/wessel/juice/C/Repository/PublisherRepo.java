package nl.wessel.juice.C.Repository;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepo extends JpaRepository<Publisher, String> {
}
