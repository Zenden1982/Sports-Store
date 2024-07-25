package com.zenden.sposts_store;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.zenden.sports_store.Classes.DTO.OrderCreateUpdateDTO;
import com.zenden.sports_store.Classes.Enum.OrderStatus;
import com.zenden.sports_store.Classes.Order;
import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Mapper.OrderMapper;
import com.zenden.sports_store.Repositories.UserRepository;
import com.zenden.sports_store.SpostsStoreApplication;

@SpringBootTest(classes = SpostsStoreApplication.class)
@Transactional
class SpostsStoreApplicationTests {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        // Создаем и сохраняем пользователя
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("admin@example.com");
        user.setFirstName("Admin");
        user.setLastName("User");
        user.setAddress("Admin Address");
        user.setPhoneNumber("1234567890");
        userRepository.saveAndFlush(user);

        // Создаем DTO для заказа
        OrderCreateUpdateDTO orderCreateUpdateDTO = new OrderCreateUpdateDTO();
        orderCreateUpdateDTO.setUserId(user.getId());
        orderCreateUpdateDTO.setTotalPrice(100);
        orderCreateUpdateDTO.setStatus(OrderStatus.PENDING);

        // Маппинг DTO в Order
        Order order = orderMapper.orderCreateUpdateDTOToOrder(orderCreateUpdateDTO);

        // Проверки
        assertThat(order).isNotNull();
        assertThat(order.getUserId()).isEqualTo(user);
        assertThat(order.getTotalPrice()).isEqualTo(100);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);

        System.out.println(order);
    }
}

