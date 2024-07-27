package com.zenden.sports_store.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenden.sports_store.Classes.Category;
import com.zenden.sports_store.Classes.DTO.CategoryDTO;
import com.zenden.sports_store.Filters.Category.CategoryFilter;
import com.zenden.sports_store.Filters.Category.CategorySpecification;
import com.zenden.sports_store.Interfaces.OneDtoService;
import com.zenden.sports_store.Mapper.CategoryMapper;
import com.zenden.sports_store.Repositories.CategoryRepository;

@Service
@Component
public class CategoryService implements OneDtoService<CategoryDTO, CategoryFilter> {

    @Autowired
    private CategoryMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryDTO create(CategoryDTO entity) {
        try {
            return mapper.categoryToCategoryDTO(categoryRepository.saveAndFlush(mapper.categoryDTOtoCategory(entity)));
        } catch (Exception e) {
            throw new RuntimeException("Error creating category" + entity.getCategoryName());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDTO read(Long id) {
        return mapper.categoryToCategoryDTO(categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Error reading category" + id)));
    }

    @Transactional
    @Override
    public CategoryDTO update(Long id, CategoryDTO entity) {
        return categoryRepository.findById(id).map(category -> {
                Category tempCategory  = mapper.categoryDTOtoCategory(entity);
                category.setCategoryName(tempCategory.getCategoryName());
                category.setCategoryDescription(tempCategory.getCategoryDescription());
            return mapper.categoryToCategoryDTO(categoryRepository.saveAndFlush(category));
        })
        .orElseThrow(() -> new RuntimeException("Error updating category" + id));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting category" + id);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CategoryDTO> readAll(int page, int size, String sort, CategoryFilter filter) {
        Specification<Category> spec = Specification.where(null);
        if (filter != null) {
            spec.and(filter.getName() != null ? CategorySpecification.nameLike(filter.getName()) : null);
            spec.and(filter.getDescription() != null ? CategorySpecification.descriptionLike(filter.getDescription()) : null);
        }
        
        return categoryRepository.findAll(spec, PageRequest.of(page, size)).map(mapper::categoryToCategoryDTO);
    }
}
