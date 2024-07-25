package com.zenden.sports_store.Classes;

import java.util.List;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "products")
public class Product extends BaseEntity {



    @Column(unique = true, nullable = false, length=15)
    private String productName;

    @Column(nullable = false, length=100)
    private String productDescription;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String image;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @OneToMany(mappedBy = "productId")
    private List<Review> reviews;

    @OneToMany(mappedBy = "productId")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "productId")
    private List<Discount> discounts;


}
