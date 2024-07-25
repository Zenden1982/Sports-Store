package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.zenden.sports_store.Classes.Category;
import com.zenden.sports_store.Classes.DTO.CategoryDTO;

@Mapper(componentModel = "spring")
@Component
public abstract class CategoryMapper {

    public abstract CategoryDTO categoryToCategoryDTO(Category category);
    public abstract Category categoryDTOtoCategory(CategoryDTO categoryDTO);
}
