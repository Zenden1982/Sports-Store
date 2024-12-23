package com.zenden.sports_store.Services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.Discount;
import com.zenden.sports_store.Classes.DTO.DiscountCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.DiscountReadDTO;
import com.zenden.sports_store.Filters.Discount.DiscountFilter;
import com.zenden.sports_store.Filters.Discount.DiscountSpecification;
import com.zenden.sports_store.Interfaces.TwoDtoService;
import com.zenden.sports_store.Mapper.DiscountMapper;
import com.zenden.sports_store.Repositories.DiscountRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscountService implements TwoDtoService<DiscountReadDTO, DiscountCreateUpdateDTO, DiscountFilter> {

    private final DiscountRepository discountRepository;

    private final DiscountMapper discountMapper;

    @Override
    public DiscountReadDTO create(DiscountCreateUpdateDTO entity) {
        return discountMapper
                .discountToDiscountDTO(discountRepository.save(discountMapper.discountDTOToDiscount(entity)));
    }

    @Override
    public DiscountReadDTO read(Long id) {
        CheckExpiryDate();
        return discountMapper.discountToDiscountDTO(
                discountRepository.findById(id).orElseThrow(() -> new RuntimeException("Error reading discount" + id)));
    }

    @Override
    public Page<DiscountReadDTO> readAll(int page, int size, String sort, DiscountFilter filter) {
        Specification<Discount> spec = Specification.where(null);
        if (filter != null) {
            spec = spec
                    .and(filter.getProductId() != null ? DiscountSpecification.productEquals(filter.getProductId())
                            : null)
                    .and(filter.getPercengateLess() != null
                            ? DiscountSpecification.percentageLess(filter.getPercengateLess())
                            : null)
                    .and(filter.getPercengateGreater() != null
                            ? DiscountSpecification.percentageGreaterThan(filter.getPercengateGreater())
                            : null);
        }
        CheckExpiryDate();
        return discountRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort)))
                .map(discountMapper::discountToDiscountDTO);
    }

    private void CheckExpiryDate() {
        discountRepository.findByExpiryDateBefore(LocalDateTime.now())
                .forEach(discount -> discountRepository.delete(discount));
    }

    @Override
    public DiscountReadDTO update(Long id, DiscountCreateUpdateDTO entity) {
        return discountRepository.findById(id).map(discount -> {
            Discount tempDiscount = discountMapper.discountDTOToDiscount(entity);
            discount.setPercentage(tempDiscount.getPercentage());
            discount.setExpiryDate(tempDiscount.getExpiryDate());
            discount.setProduct(tempDiscount.getProduct());
            return discountMapper.discountToDiscountDTO(discountRepository.save(discount));
        }).orElseThrow(() -> new RuntimeException("Error updating discount" + id));
    }

    @Override
    public void delete(Long id) {
        try {
            discountRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting discount" + id);
        }
    }

}
