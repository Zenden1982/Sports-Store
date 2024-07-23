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
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "products")
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, length=15)
    private String productName;

    @Column(nullable = false, length=100)
    private String productDescription;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @Column(nullable = false)
    private String image;
}
