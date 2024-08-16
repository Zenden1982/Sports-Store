package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.zenden.sports_store.Classes.Category;
import com.zenden.sports_store.Classes.DTO.ProductCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.ProductReadDTO;
import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Repositories.CategoryRepository;
import com.zenden.sports_store.Services.ImageService;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public abstract class ProductMapper {

    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    ImageService imageService;
    
    @Mapping(source="category", target = "categoryDTO")
    public abstract ProductReadDTO productToProductReadDTO(Product product);


    @Mapping(source="categoryId", target = "category", qualifiedByName = "categoryId")
    public abstract Product productCreateUpdateDTOToProduct(ProductCreateUpdateDTO productDTO);

    @Named("categoryId")
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
        
    }

    // @Named("StringToImage")
    // public byte[] getImage(String image) {
    //     return imageService.getImage(image).orElse(null);
    // }

    // @Named("ImageToString")
    // public String getImage(MultipartFile image) {
    //     imageService.uploadImage(image);
    //     return image.getOriginalFilename();
    // }
}

