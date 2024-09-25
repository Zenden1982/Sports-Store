package com.zenden.sports_store.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenden.sports_store.Classes.Cart;
import com.zenden.sports_store.Classes.Order;
import com.zenden.sports_store.Classes.OrderItem;
import com.zenden.sports_store.Classes.PaymentInfo;
import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Classes.DTO.OrderCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.OrderReadDTO;
import com.zenden.sports_store.Classes.Enum.OrderStatus;
import com.zenden.sports_store.Filters.Order.OrderFilter;
import com.zenden.sports_store.Filters.Order.OrderSpecification;
import com.zenden.sports_store.Mapper.OrderMapper;
import com.zenden.sports_store.Repositories.CartItemRepository;
import com.zenden.sports_store.Repositories.CartRepository;
import com.zenden.sports_store.Repositories.OrderItemRepository;
import com.zenden.sports_store.Repositories.OrderRepository;
import com.zenden.sports_store.Repositories.PaymentRepository;
import com.zenden.sports_store.Repositories.ProductRepository;

import ru.loolzaaa.youkassa.model.Payment;

@Service
@Component
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderReadDTO create(OrderCreateUpdateDTO entity) {
        Order order = orderMapper.orderCreateUpdateDTOToOrder(entity);

        Payment payment = null;
        try {
            payment = paymentService.createPayment(order.getTotalPrice(), "RUB", order.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error creating payment for order" + order.getId(), e);
        }

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setId(payment.getId());
        paymentInfo.setStatus(payment.getStatus());
        paymentInfo.setUrl(payment.getConfirmation().getConfirmationUrl());
        paymentInfo.setOrder(order);

        order.setPayment(paymentInfo);
        orderRepository.saveAndFlush(order);
        paymentRepository.saveAndFlush(paymentInfo);

        Cart cart = cartRepository.findByUserId(entity.getUserId()).get();
        cart.setTotalPrice(0.0);
        cartRepository.saveAndFlush(cart);
        cartItemRepository.deleteByCartId(cart.getId());

        entity.getOrderItemIds().forEach(object -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            Product product = productRepository.findById(object.getProductId()).get();
            orderItem.setProduct(product);
            product.setStock(product.getStock() - object.getQuantity());
            if (product.getStock() < 0) {
                product.setStock(0);
            }
            productRepository.saveAndFlush(product);
            orderItem.setQuantity(object.getQuantity());
            orderItemRepository.saveAndFlush(orderItem);

        });

        return orderMapper.orderToOrderReadDTO(order);

    }

    public OrderReadDTO read(Long id) {
        return orderMapper.orderToOrderReadDTO(
                orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Error reading order" + id)));
    }

    public Page<OrderReadDTO> readAll(int page, int size, String sort, OrderFilter filter) {
        Specification<Order> spec = Specification.where(null);
        if (filter != null) {
            spec = spec.and(filter.getStatus() != null ? OrderSpecification.statusEquals(filter.getStatus()) : null);
            spec = spec.and(filter.getUserId() != null ? OrderSpecification.userEquals(filter.getUserId()) : null);
        }
        return orderRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort)))
                .map(orderMapper::orderToOrderReadDTO);
    }

    public OrderReadDTO update(Long id, OrderCreateUpdateDTO entity) {
        return orderRepository.findById(id).map(order -> {
            order.setTotalPrice(order.getTotalPrice());
            order.setStatus(entity.getStatus());

            if (entity.getStatus().equals(OrderStatus.CANCELED)) {
                List<Product> products = order.getOrderItems().stream().map(orderItem -> {
                    Product product = orderItem.getProduct();
                    product.setStock(product.getStock() + orderItem.getQuantity());
                    return product;
                }).toList();
                for (Product product : products) {
                    productRepository.save(product);
                }
            }
            order.setUser(order.getUser());
            return orderMapper.orderToOrderReadDTO(orderRepository.save(order));
        }).orElseThrow(() -> {
            throw new RuntimeException("Error updating order" + id);
        });
    }

    public void delete(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting order" + id, e);
        }
    }

}
