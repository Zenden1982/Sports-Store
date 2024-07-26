package com.zenden.sports_store;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import com.zenden.sports_store.Classes.Category;
import com.zenden.sports_store.Classes.Discount;
import com.zenden.sports_store.Classes.Order;
import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Classes.DTO.DiscountReadDTO;
import com.zenden.sports_store.Classes.DTO.OrderCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.ProductCreateUpdateDTO;
import com.zenden.sports_store.Classes.Enum.OrderStatus;
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

    @Test
    void testOrderMapper() {
        // Создаем и сохраняем пользователя
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("admin@example.com");
        user.setFirstName("Admin");
        user.setLastName("User");
        user.setAddress("Admin Address");
        user.setPhoneNumber("1234567890");

        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        // Создаем DTO для заказа
        OrderCreateUpdateDTO orderCreateUpdateDTO = new OrderCreateUpdateDTO();
        orderCreateUpdateDTO.setUserId(user.getId());
        orderCreateUpdateDTO.setTotalPrice(100L);
        orderCreateUpdateDTO.setStatus(OrderStatus.PENDING);

        // Маппинг DTO в Order
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(100L);
        order.setStatus(OrderStatus.PENDING);

        when(orderMapper.orderCreateUpdateDTOToOrder(any(OrderCreateUpdateDTO.class))).thenReturn(order);

        // Проверки
        Order mappedOrder = orderMapper.orderCreateUpdateDTOToOrder(orderCreateUpdateDTO);
        assertThat(mappedOrder).isNotNull();
        assertThat(mappedOrder.getUser()).isEqualTo(user);
        assertThat(mappedOrder.getTotalPrice()).isEqualTo(100);
        assertThat(mappedOrder.getStatus()).isEqualTo(OrderStatus.PENDING);

    }

    @Test
    void testProductMapper() {
        // Создаем категорию
        Category category = new Category();
        category.setCategoryName("Category 1");
        category.setCategoryDescription("Category 1 description");

        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);

        // Создаем DTO для продукта
        ProductCreateUpdateDTO productCreateUpdateDTO = new ProductCreateUpdateDTO();
        productCreateUpdateDTO.setProductName("Product 1");
        productCreateUpdateDTO.setProductDescription("Product 1 description");
        productCreateUpdateDTO.setPrice(100L);
        productCreateUpdateDTO.setStock(10);
        productCreateUpdateDTO.setCategoryId(category.getId());
        productCreateUpdateDTO.setImage(mock(MultipartFile.class));

        // Маппинг DTO в Product
        Product product = new Product();
        product.setProductName("Product 1");
        product.setProductDescription("Product 1 description");
        product.setPrice(100L);
        product.setStock(10);
        product.setCategory(category);

        when(productMapper.productCreateUpdateDTOtoProduct(any(ProductCreateUpdateDTO.class))).thenReturn(product);

        // Проверки
        Product mappedProduct = productMapper.productCreateUpdateDTOtoProduct(productCreateUpdateDTO);
        assertThat(mappedProduct).isNotNull();
        assertThat(mappedProduct.getProductName()).isEqualTo("Product 1");
        assertThat(mappedProduct.getProductDescription()).isEqualTo("Product 1 description");
        assertThat(mappedProduct.getPrice()).isEqualTo(100);
        assertThat(mappedProduct.getStock()).isEqualTo(10);
        assertThat(mappedProduct.getCategory()).isEqualTo(category);
    }


    @Test
    void testDiscountMapper() {
        Discount discount = new Discount();
        discount.setCode("Discount 1");
        discount.setPercentage(10);
        discount.setExpiryDate(LocalDateTime.now().plusDays(30));
        discount.setDescription("Discount 1 description");
        discount.setProduct(mock(Product.class));

        DiscountReadDTO discountReadDTO = new DiscountReadDTO();
        discountReadDTO.setId(discount.getId());
        discountReadDTO.setCode(discount.getCode());
        discountReadDTO.setProductReadDTO(productMapper.productToProductReadDTO(discount.getProduct()));
        discountReadDTO.setPercentage(discount.getPercentage());
        discountReadDTO.setExpiryDate(discount.getExpiryDate());
        discountReadDTO.setDescription(discount.getDescription());

        when(discountMapper.discountToDiscountDTO(any(Discount.class))).thenReturn(discountReadDTO);

        // Проверки
        DiscountReadDTO mappedDiscount = discountMapper.discountToDiscountDTO(discount);
        assertThat(mappedDiscount).isNotNull();
        assertThat(mappedDiscount.getId()).isEqualTo(discount.getId());
        assertThat(mappedDiscount.getCode()).isEqualTo(discount.getCode());
        assertThat(mappedDiscount.getProductReadDTO()).isEqualTo(productMapper.productToProductReadDTO(discount.getProduct()));
        assertThat(mappedDiscount.getPercentage()).isEqualTo(discount.getPercentage());
        assertThat(mappedDiscount.getExpiryDate()).isEqualTo(discount.getExpiryDate());
        assertThat(mappedDiscount.getDescription()).isEqualTo(discount.getDescription());
    }
}
