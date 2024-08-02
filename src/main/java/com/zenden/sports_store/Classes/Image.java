package com.zenden.sports_store.Classes;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "images")
@ToString(exclude = "product")
public class Image extends BaseEntity{
    
    private String image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
