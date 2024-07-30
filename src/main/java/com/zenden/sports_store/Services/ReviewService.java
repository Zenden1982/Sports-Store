package com.zenden.sports_store.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.Review;
import com.zenden.sports_store.Classes.DTO.ReviewCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.ReviewReadDTO;
import com.zenden.sports_store.Filters.Review.ReviewFilter;
import com.zenden.sports_store.Filters.Review.ReviewSpecification;
import com.zenden.sports_store.Interfaces.TwoDtoService;
import com.zenden.sports_store.Mapper.ReviewMapper;
import com.zenden.sports_store.Repositories.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewService implements TwoDtoService<ReviewReadDTO, ReviewCreateUpdateDTO, ReviewFilter> {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public ReviewReadDTO create(ReviewCreateUpdateDTO entity) {
        try {
            return reviewMapper.reviewToReviewReadDTO(reviewRepository.save(reviewMapper.reviewCreateUpdateDTOToReview(entity)));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating review" + entity.getId());
        }
    }

    @Override
    public ReviewReadDTO read(Long id) {
        return reviewMapper.reviewToReviewReadDTO(reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Error reading review" + id)));
    }

    @Override
    public Page<ReviewReadDTO> readAll(int page, int size, String sort, ReviewFilter filter) {
        Specification<Review> spec = Specification.where(null);
        if (filter != null) {
            spec = spec.and(filter.getProductId() != null ? ReviewSpecification.productIdEquals(filter.getProductId()) : null)
            .and(filter.getRatingLess() != null ? ReviewSpecification.ratingLess(filter.getRatingLess()) : null)
            .and(filter.getRatingGreater() != null ? ReviewSpecification.ratingGreaterThan(filter.getRatingGreater()) : null)
            .and(filter.getUserId() != null ? ReviewSpecification.userIdEquals(filter.getUserId()) : null);
        }

        return reviewRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort))).map(reviewMapper::reviewToReviewReadDTO);
    }

    @Override
    public ReviewReadDTO update(Long id, ReviewCreateUpdateDTO entity) {
        return reviewRepository.findById(id).map(review -> {
            Review tempReview = reviewMapper.reviewCreateUpdateDTOToReview(entity);
            review.setUser(tempReview.getUser());
            review.setProduct(tempReview.getProduct());
            review.setRating(tempReview.getRating());
            review.setComment(tempReview.getComment());
            return reviewMapper.reviewToReviewReadDTO(reviewRepository.save(review));
        }).orElseThrow(()-> new RuntimeException("Error updating review" + id));
    }

    @Override
    public void delete(Long id) {
        try {
            reviewRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting review" + id);
        }
    }


}
