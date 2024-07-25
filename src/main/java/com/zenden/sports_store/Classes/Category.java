package com.zenden.sports_store.Classes;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity{

    @Column(unique = true, nullable = false, length=15)
    private String categoryName;

    @Column(nullable = false, length=100)
    private String categoryDescription;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
