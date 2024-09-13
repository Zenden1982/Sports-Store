package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zenden.sports_store.Classes.DTO.OrderItemCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.OrderItemReadDTO;
import com.zenden.sports_store.Classes.Order;
import com.zenden.sports_store.Classes.OrderItem;
import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Repositories.OrderRepository;
import com.zenden.sports_store.Repositories.ProductRepository;

@Component
@Mapper(componentModel = "spring", uses = { OrderMapper.class, ProductMapper.class })
public abstract class OrderItemMapper {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Mapping(source = "order", target = "orderReadDTO")
    @Mapping(source = "product", target = "productReadDTO")
    public abstract OrderItemReadDTO orderItemToOrderItemReadDTO(OrderItem orderItem);

    @Mapping(source = "productId", target = "product", qualifiedByName = "product")
    public abstract OrderItem orderItemCreateUpdateDTOToOrderItem(OrderItemCreateUpdateDTO orderItemCreateUpdateDTO);

    @Named("order")
    public Order finOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Named("product")
    public Product fProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

}
