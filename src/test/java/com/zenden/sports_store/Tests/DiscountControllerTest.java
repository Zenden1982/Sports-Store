package com.zenden.sports_store.Tests;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenden.sports_store.Classes.DTO.DiscountCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.DiscountReadDTO;
import com.zenden.sports_store.Filters.Discount.DiscountFilter;
import com.zenden.sports_store.Services.DiscountService;

@SpringBootTest
public class DiscountControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DiscountService discountService;

    private DiscountCreateUpdateDTO discountCreateUpdateDTO;
    private DiscountReadDTO discountReadDTO;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        discountCreateUpdateDTO = new DiscountCreateUpdateDTO();
        discountCreateUpdateDTO.setPercentage(10);
        discountCreateUpdateDTO.setDescription("Test Discount");

        discountReadDTO = new DiscountReadDTO();
        discountReadDTO.setId(1L);
        discountReadDTO.setPercentage(10);
        discountReadDTO.setDescription("Test Discount");
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testCreateDiscount() throws Exception {
        when(discountService.create(ArgumentMatchers.any(DiscountCreateUpdateDTO.class)))
                .thenReturn(discountReadDTO);

        mockMvc.perform(post("/api/discounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(discountCreateUpdateDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(discountReadDTO)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testReadAllDiscounts() throws Exception {
        Page<DiscountReadDTO> page = new PageImpl<>(Collections.singletonList(discountReadDTO));
        when(discountService.readAll(anyInt(), anyInt(), anyString(), ArgumentMatchers.any(DiscountFilter.class)))
                .thenReturn(page);

        DiscountFilter filter = new DiscountFilter();
        mockMvc.perform(post("/api/discounts/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filter))
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testReadDiscount() throws Exception {
        when(discountService.read(1L)).thenReturn(discountReadDTO);

        mockMvc.perform(get("/api/discounts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(discountReadDTO)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateDiscount() throws Exception {
        when(discountService.update(eq(1L), ArgumentMatchers.any(DiscountCreateUpdateDTO.class)))
                .thenReturn(discountReadDTO);

        mockMvc.perform(put("/api/discounts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(discountCreateUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(discountReadDTO)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteDiscount() throws Exception {
        doNothing().when(discountService).delete(1L);

        mockMvc.perform(delete("/api/discounts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
