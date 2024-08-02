package com.zenden.sports_store.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zenden.sports_store.Classes.Image;
import com.zenden.sports_store.Filters.Image.ImageFilter;
import com.zenden.sports_store.Filters.Image.ImageSpecification;
import com.zenden.sports_store.Repositories.ImageRepository;

@Service
@Component
public class ImageService{

    @Autowired
    private ImageRepository imageRepository;
    
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


    public Image create(Image entity) {
        try {
            return imageRepository.save(entity);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating image" + entity.getId());
        }
    }

    public Image read(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Error reading image" + id));
    }

public Page<byte[]> readAll(int page, int size, String sort, ImageFilter filter) {
    try {
        Specification<Image> spec = Specification.where(null);

        if (filter != null) {
            spec = spec.and(filter.getProductId() != null ? ImageSpecification.filterByProductId(filter.getProductId()) : null);
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort));
        Page<Image> imagePage = imageRepository.findAll(spec, pageRequest);

        List<byte[]> imageBytes = imagePage.stream()
                .flatMap(image -> getImage(image.getImage()).stream())
                .collect(Collectors.toList());

        return new PageImpl<>(imageBytes, pageRequest, imagePage.getTotalElements());
    } catch (DataAccessException e) {
        throw new RuntimeException("Database error while reading images", e);
    } catch (Exception e) {
        throw new RuntimeException("Unexpected error while reading images", e);
    }
}

    public Image update(Long id, Image entity) {
        return imageRepository.findById(id).map(image -> {
            image.setImage(entity.getImage());
            image.setProduct(entity.getProduct());
            return imageRepository.save(image);
        }).orElseThrow(() -> {
            throw new RuntimeException("Error updating image" + id);
        });
    }

    public void delete(Long id) {
        try {
            imageRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting image" + id);
        }
    }
}
