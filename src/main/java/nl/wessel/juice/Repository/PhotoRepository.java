package nl.wessel.juice.Repository;

import nl.wessel.juice.Model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo findPhotoByFileName(String fileName);
}
