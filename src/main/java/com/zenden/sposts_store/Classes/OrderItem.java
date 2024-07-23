package com.zenden.sposts_store.Classes;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

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
