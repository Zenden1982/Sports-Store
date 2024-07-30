package com.zenden.sports_store.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.OrderItem;
import com.zenden.sports_store.Classes.DTO.OrderItemCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.OrderItemReadDTO;
import com.zenden.sports_store.Filters.OrderItem.OrderItemFilter;
import com.zenden.sports_store.Filters.OrderItem.OrderItemSpecification;
import com.zenden.sports_store.Interfaces.TwoDtoService;
import com.zenden.sports_store.Mapper.OrderItemMapper;
import com.zenden.sports_store.Repositories.OrderItemRepository;

@Service
public class OrderItemService implements TwoDtoService<OrderItemReadDTO, OrderItemCreateUpdateDTO, OrderItemFilter> {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Override
    public OrderItemReadDTO create(OrderItemCreateUpdateDTO entity) {
        try {
            return orderItemMapper.orderItemToOrderItemReadDTO(orderItemRepository.save(orderItemMapper.orderItemCreateUpdateDTOToOrderItem(entity)));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating order item" + entity.getId());
        }
    }

    @Override
    public OrderItemReadDTO read(Long id) {
        return  orderItemRepository.findById(id).map(orderItemMapper::orderItemToOrderItemReadDTO).orElseThrow(() -> new RuntimeException("Error reading order item" + id));
    }

    @Override
    public Page<OrderItemReadDTO> readAll(int page, int size, String sort, OrderItemFilter filter) {
        Specification<OrderItem> spec = Specification.where(filter.getOrderId() !=null
            ? OrderItemSpecification.orderEquals(filter.getOrderId()) : null);
        return orderItemRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort))).map(orderItemMapper::orderItemToOrderItemReadDTO);
    }

    @Override
    public OrderItemReadDTO update(Long id, OrderItemCreateUpdateDTO entity) {
        return orderItemRepository.findById(id).map(orderItem -> {
            OrderItem tempOrderItem = orderItemMapper.orderItemCreateUpdateDTOToOrderItem(entity);
            orderItem.setProduct(tempOrderItem.getProduct());
            orderItem.setQuantity(tempOrderItem.getQuantity());
            orderItem.setOrder(tempOrderItem.getOrder());
            orderItem.setPrice(tempOrderItem.getPrice());
            orderItemRepository.save(tempOrderItem);
            return orderItemMapper.orderItemToOrderItemReadDTO(tempOrderItem);
        }).orElseThrow(() -> {
            throw new RuntimeException("Error updating order item" + id);
        });
    }

    @Override
    public void delete(Long id) {
        try {
            orderItemRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting order item" + id);
        }
    }

}
