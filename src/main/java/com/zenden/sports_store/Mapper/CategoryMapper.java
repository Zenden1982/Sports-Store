package com.zenden.sports_store.Mapper;

import com.zenden.sports_store.Classes.Category;
import com.zenden.sports_store.Classes.DTO.CategoryDTO;

public abstract class CategoryMapper {

    public abstract CategoryDTO categoryToCategoryDTO(Category category);
    public abstract Category categoryDTOtoCategory(CategoryDTO categoryDTO);
}
