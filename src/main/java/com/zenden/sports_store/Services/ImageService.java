package com.zenden.sports_store.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Component
public class ImageService {

    public String BUCKET = "src\\main\\resources\\images";

    public void uploadImage(MultipartFile image) {
        Optional<String> originalFilename = Optional.ofNullable(image.getOriginalFilename());
        if (originalFilename.isPresent()) {
            @SuppressWarnings("null")
            Path fullPath = Path.of(BUCKET, image.getOriginalFilename().replace("/", "\\"));


            try {
                Files.createDirectories(fullPath.getParent());
                Files.write(fullPath, image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Optional<byte[]> getImage(String name) {
        Optional<Path> fullPath = Optional.ofNullable(Path.of(BUCKET, name.replace("/", "\\")));
        if (fullPath.isPresent() && Files.exists(fullPath.get())) {
            try {
                return Optional.of(Files.readAllBytes(fullPath.get()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }
}
