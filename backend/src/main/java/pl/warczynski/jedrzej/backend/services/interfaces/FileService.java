package pl.warczynski.jedrzej.backend.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String UPLOADED_IMAGES_PATH = "uploaded-images";
    String saveFile(MultipartFile file);
}
