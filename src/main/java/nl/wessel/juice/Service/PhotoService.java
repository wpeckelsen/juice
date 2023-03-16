package nl.wessel.juice.Service;

import nl.wessel.juice.Model.Photo;
import nl.wessel.juice.Repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;


    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }


    public Collection<Photo> findAllPhotos() {
        return photoRepository.findAll();
    }

    public Photo UploadSinglePhoto(MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Photo photo = new Photo();
        photo.setFileName(name);
        photo.setDocFile(file.getBytes());
        photoRepository.save(photo);
        return photo;
    }

    @Transactional
    public ResponseEntity<byte[]> DownloadSinglePhoto(String fileName, HttpServletRequest request) {
        Photo photo = photoRepository.findPhotoByFileName(fileName);

        String mimeType = request.getServletContext().getMimeType(photo.getFileName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + photo.getFileName())
                .body(photo.getDocFile());
    }

    public Resource downloadDatabasePhoto(String filename) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(filename).toUriString();
        Resource resource;

        try {
            resource = new UrlResource(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }

    }


}
