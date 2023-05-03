package pl.warczynski.jedrzej.backend.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.warczynski.jedrzej.backend.services.interfaces.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String saveFile(MultipartFile file) {
        String filename = null;
        try {
            filename = addTimestamp(Objects.requireNonNull(file.getOriginalFilename()));
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_IMAGES_PATH, filename);
            Files.write(path, bytes);
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String addTimestamp(String originalFilename) {
        String[] parts = originalFilename.split("\\.");
        String extension = parts[parts.length - 1];
        String timestamp = Long.toString(System.currentTimeMillis());
        return parts[0] + "_" + timestamp + "." + extension;
    }
}
