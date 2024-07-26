package com.zenden.sports_store.Tests;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.zenden.sports_store.Classes.Category;
import com.zenden.sports_store.Classes.DTO.CategoryDTO;
import com.zenden.sports_store.Filters.Category.CategoryFilter;
import com.zenden.sports_store.Mapper.CategoryMapper;
import com.zenden.sports_store.Repositories.CategoryRepository;
import com.zenden.sports_store.Services.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setup() {
        category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");
        category.setCategoryDescription("Test Description");

        categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category");
        categoryDTO.setCategoryDescription("Test Description");
    }

    @Test
    public void testCreateCategory() {
        when(mapper.categoryDTOtoCategory(any(CategoryDTO.class))).thenReturn(category);
        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);
        when(mapper.categoryToCategoryDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO createdCategory = categoryService.create(categoryDTO);

        assertNotNull(createdCategory);
        assertEquals("Test Category", createdCategory.getCategoryName());
    }

    @Test
    public void testReadCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(mapper.categoryToCategoryDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO foundCategory = categoryService.read(1L);

        assertNotNull(foundCategory);
        assertEquals("Test Category", foundCategory.getCategoryName());
    }

    @Test
    public void testUpdateCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);
        when(mapper.categoryToCategoryDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO updatedCategory = categoryService.update(1L, categoryDTO);

        assertNotNull(updatedCategory);
        assertEquals("Test Category", updatedCategory.getCategoryName());
    }

    @Test
    public void testDeleteCategory() {
        categoryService.delete(1L);
        // No exception means the test passed
    }

    @Test
    public void testReadAllCategories() {
        CategoryFilter filter = new CategoryFilter();
        filter.setName("Test");

        Page<Category> page = new PageImpl<>(Collections.singletonList(category));
        Pageable pageable = PageRequest.of(0, 10);

        when(categoryRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.categoryToCategoryDTO(any(Category.class))).thenReturn(categoryDTO);

        Page<CategoryDTO> categories = categoryService.readAll(0, 10, "categoryName", filter);

        assertNotNull(categories);
        assertEquals(1, categories.getTotalElements());
        assertEquals("Test Category", categories.getContent().get(0).getCategoryName());
    }
}