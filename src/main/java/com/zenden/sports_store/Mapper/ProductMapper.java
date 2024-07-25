package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zenden.sports_store.Classes.Category;
import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Classes.DTO.ProductCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.ProductReadDTO;
import com.zenden.sports_store.Repositories.CategoryRepository;
import com.zenden.sports_store.Services.ImageService;

@Mapper(componentModel = "spring")
@Component
public abstract class ProductMapper {

    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    ImageService imageService;
    

    @Mapping(source="category", target = "categoryDTO")
    @Mapping(source="image", target = "image", qualifiedByName = "StringToImage")
    public abstract ProductReadDTO productToProductReadDTO(Product product);


    @Mapping(source="categoryId", target = "category", qualifiedByName = "categoryId")
    @Mapping(source="image", target = "image", qualifiedByName = "ImageToString")
    public abstract Product productCreateUpdateDTOtoProduct(ProductCreateUpdateDTO productDTO);

    @Named("categoryId")
    public Category findCategoryById(long id) {
        return categoryRepository.findById(id).orElse(null);
        
    }

    @Named("StringToImage")
    public byte[] getImage(String image) {
        return imageService.getImage(image).orElse(null);
    }

    @Named("ImageToString")
    public String getImage(MultipartFile image) {
        imageService.UploadImage(image);
        return image.getOriginalFilename();
    }
}
