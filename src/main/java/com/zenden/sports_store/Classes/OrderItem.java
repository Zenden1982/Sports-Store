package com.zenden.sports_store.Classes;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "order_items")
public class OrderItem extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @Column(nullable = false)
    private int quantity;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Column(nullable = false)
    private long price;
}
