package nl.wessel.juice.C.Repository;
import nl.wessel.juice.B.BusinessLogic.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepo extends JpaRepository<Owner, String> {
}
