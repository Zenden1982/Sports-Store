package com.zenden.sports_store.Classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "discounts")
public class Discount extends BaseEntity {

    @Column(nullable = false)
    @Min(1)
    @Max(100)
    private int percentage;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
