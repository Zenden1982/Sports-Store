package com.zenden.sports_store.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenden.sports_store.Classes.DTO.OrderCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.OrderReadDTO;
import com.zenden.sports_store.Classes.Order;
import com.zenden.sports_store.Filters.Order.OrderFilter;
import com.zenden.sports_store.Filters.Order.OrderSpecification;
import com.zenden.sports_store.Interfaces.TwoDtoService;
import com.zenden.sports_store.Mapper.OrderMapper;
import com.zenden.sports_store.Repositories.OrderRepository;

@Service
@Component
@Transactional
public class OrderService implements TwoDtoService<OrderReadDTO, OrderCreateUpdateDTO, OrderFilter>{

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderReadDTO create(OrderCreateUpdateDTO entity) {
        try {

            return orderMapper.orderToOrderReadDTO(orderRepository.saveAndFlush(orderMapper.orderCreateUpdateDTOToOrder(entity)));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating order" + entity.getId());
        }
    }

    @Override
    public OrderReadDTO read(Long id) {
        return orderMapper.orderToOrderReadDTO(orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Error reading order" + id)));
    }

    @Override
    public Page<OrderReadDTO> readAll(int page, int size, String sort, OrderFilter filter) {
        Specification<Order> spec = Specification.where(null);
        if (filter != null){
            spec = spec.and(filter.getStatus() != null ? OrderSpecification.statusEquals(filter.getStatus()) : null);
            spec = spec.and(filter.getUserId() != null ? OrderSpecification.userEquals(filter.getUserId()) : null);
        }
        return orderRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort))).map(orderMapper::orderToOrderReadDTO);
    }

    @Override
    public OrderReadDTO update(Long id, OrderCreateUpdateDTO entity) {
        return orderRepository.findById(id).map(order -> {
            Order newOrder = orderMapper.orderCreateUpdateDTOToOrder(entity);
            order.setTotalPrice(newOrder.getTotalPrice());
            order.setStatus(newOrder.getStatus());
            order.setUser(newOrder.getUser());
            return orderMapper.orderToOrderReadDTO(orderRepository.save(order));
        }).orElseThrow(() -> {
            throw new RuntimeException("Error updating order" + id);
        });
    }

    @Override
    public void delete(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting order" + id, e);
        }
    }


}
