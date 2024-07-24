package com.zenden.sports_store.Classes;

import org.springframework.format.annotation.NumberFormat;

import com.zenden.sports_store.Classes.Enum.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "orders")
public class Order extends Auditable {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)

    private long totalPrice;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
}