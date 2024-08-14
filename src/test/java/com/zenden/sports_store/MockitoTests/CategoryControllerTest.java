package com.zenden.sports_store.MockitoTests;


import java.util.Collections;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zenden.sports_store.Classes.DTO.CategoryDTO;
import com.zenden.sports_store.Controllers.CategoryController;
import com.zenden.sports_store.Filters.Category.CategoryFilter;
import com.zenden.sports_store.Services.CategoryService;

@WebMvcTest(CategoryController.class)
@ContextConfiguration(classes = {CategoryController.class})
public class CategoryControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CategoryService categoryService;
    
    @Test
    @WithMockUser(roles = "ROLE_USER")
    void testCreateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category");
        
        when(categoryService.create(any(CategoryDTO.class))).thenReturn(categoryDTO);
        
        mockMvc.perform(post("/api/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"categoryName\": \"Test Category\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.categoryName").value("Test Category"));
    }
    
    @Test
    @WithMockUser
    void testReadCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category");
        
        when(categoryService.read(anyLong())).thenReturn(categoryDTO);
        
        mockMvc.perform(get("/api/categories/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.categoryName").value("Test Category"));
    }
    
    @Test
    @WithMockUser
    void testReadAllCategories() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category");
        Page<CategoryDTO> page = new PageImpl<>(Collections.singletonList(categoryDTO));
        
        when(categoryService.readAll(anyInt(), anyInt(), anyString(), any(CategoryFilter.class))).thenReturn(page);
        
        mockMvc.perform(post("/api/categories/all")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].categoryName").value("Test Category"));
    }
    
    @Test
    @WithMockUser
    void testUpdateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Updated Category");
        
        when(categoryService.update(anyLong(), any(CategoryDTO.class))).thenReturn(categoryDTO);
        
        mockMvc.perform(put("/api/categories/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"categoryName\": \"Updated Category\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.categoryName").value("Updated Category"));
    }
    
    @Test
    @WithMockUser

    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).delete(anyLong());
        
        mockMvc.perform(delete("/api/categories/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
}
