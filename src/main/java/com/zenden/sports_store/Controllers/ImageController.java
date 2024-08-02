package com.zenden.sports_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zenden.sports_store.Classes.Image;
import com.zenden.sports_store.Filters.Image.ImageFilter;
import com.zenden.sports_store.Repositories.ProductRepository;
import com.zenden.sports_store.Services.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/{id}")
    public ResponseEntity<?> create(@PathVariable Long id, @RequestPart("image") MultipartFile image) {
        Image image2 = new Image();
        imageService.uploadImage(image);
        image2.setImage(image.getOriginalFilename());
        image2.setProduct(productRepository.findById(id).get());
        return ResponseEntity.status(201).body(imageService.create(image2));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.status(200).body(imageService.getImage(imageService.read(id).getImage()));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "") Long id) {
                ImageFilter filter = new ImageFilter();
                filter.setProductId(id);
        return ResponseEntity.status(200).body(imageService.readAll(page,size,sort, filter));
    }
    

}
