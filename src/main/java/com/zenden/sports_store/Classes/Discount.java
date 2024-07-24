package com.zenden.sports_store.Classes;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Data
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy=jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length=15)
    private String code;

    @Column(nullable = false)
    @Min(1)
    @Max(100)
    private int percentage;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = true, length=100)
    private String description;
}
