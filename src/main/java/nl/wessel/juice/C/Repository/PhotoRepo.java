package nl.wessel.juice.C.Repository;

import nl.wessel.juice.B.BusinessLogic.Model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo, Long> {
    Photo findPhotoByFileName(String fileName);
}
