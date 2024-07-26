package com.zenden.sports_store.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zenden.sports_store.Classes.Category;

@Repository
public interface  CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    
}
