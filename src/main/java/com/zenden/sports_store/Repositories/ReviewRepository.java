package com.zenden.sports_store.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zenden.sports_store.Classes.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>,JpaSpecificationExecutor<Review> {

}
