package com.zenden.sports_store.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.Category;
import com.zenden.sports_store.Classes.DTO.CategoryDTO;
import com.zenden.sports_store.Filters.Category.CategoryFilter;
import com.zenden.sports_store.Filters.Category.CategorySpecification;
import com.zenden.sports_store.Interfaces.BaseService;
import com.zenden.sports_store.Mapper.CategoryMapper;
import com.zenden.sports_store.Repositories.CategoryRepository;

@Service
@Component
public class CategoryService implements BaseService<CategoryDTO, CategoryFilter> {

    @Autowired
    private CategoryMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO create(CategoryDTO entity) {
        try {
            return mapper.categoryToCategoryDTO(categoryRepository.saveAndFlush(mapper.categoryDTOtoCategory(entity)));
        } catch (Exception e) {
            throw new RuntimeException("Error creating category" + entity.getCategoryName());
        }
    }

    @Override
    public CategoryDTO read(Long id) {
        return mapper.categoryToCategoryDTO(categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Error reading category" + id)));
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO entity) {
        return categoryRepository.findById(id).map(category -> {
            category.setCategoryName(entity.getCategoryName());
            category.setCategoryDescription(entity.getCategoryDescription());
            return mapper.categoryToCategoryDTO(categoryRepository.saveAndFlush(category));
        })
        .orElseThrow(() -> new RuntimeException("Error updating category" + id));
    }

    @Override
    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting category" + id);
        }
    }

    @Override
    public Page<CategoryDTO> readAll(int page, int size, String sort, CategoryFilter filter) {
        Specification<Category> spec = Specification.where(null);
        if (filter != null) {
            if (filter.getName() != null && !filter.getName().isEmpty()) {
                spec = CategorySpecification.nameLike(filter.getName());
            }

            if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
                spec = spec.and(CategorySpecification.descriptionLike(filter.getDescription()));
            }
        }
        
        return categoryRepository.findAll(spec, PageRequest.of(page, size)).map(mapper::categoryToCategoryDTO);
    }
}
