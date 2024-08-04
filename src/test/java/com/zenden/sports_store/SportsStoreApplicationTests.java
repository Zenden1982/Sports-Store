package com.zenden.sports_store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.zenden.sports_store.Mapper.DiscountMapper;
import com.zenden.sports_store.Mapper.OrderMapper;
import com.zenden.sports_store.Mapper.ProductMapper;
import com.zenden.sports_store.Repositories.CategoryRepository;
import com.zenden.sports_store.Repositories.UserRepository;

@SpringBootTest(classes = SportsStoreApplication.class)
class SportsStoreApplicationTests {

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private DiscountMapper discountMapper;

    @InjectMocks
    private SportsStoreApplicationTests sportsStoreApplicationTests;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contextLoads() {
        // No specific test, just checking if context loads
    }


}
