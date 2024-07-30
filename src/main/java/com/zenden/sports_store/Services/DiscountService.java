package com.zenden.sports_store.Services;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class DiscountService implements TwoDtoService<DiscountReadDTO, DiscountCreateUpdateDTO, DiscountFilter> {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountMapper discountMapper;

    @Override
    public DiscountReadDTO create(DiscountCreateUpdateDTO entity) {
        try {
            return discountMapper.discountToDiscountDTO(discountRepository.save(discountMapper.discountDTOToDiscount(entity)));
            
        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating discount" + entity.getId());
        }
    }

    @Override
    public DiscountReadDTO read(Long id) {
        return discountMapper.discountToDiscountDTO(discountRepository.findById(id).orElseThrow(() -> new RuntimeException("Error reading discount" + id)));
    }

    @Override
    public Page<DiscountReadDTO> readAll(int page, int size, String sort, DiscountFilter filter) {
        Specification<Discount> spec = Specification.where(null);
        if (filter != null) {
            spec = spec.and(filter.getProductId() != null ? DiscountSpecification.productEquals(filter.getProductId()) : null)
            .and(filter.getPercengateLess() != null ? DiscountSpecification.percentageLess(filter.getPercengateLess()) : null)
            .and(filter.getPercengateGreater() != null ? DiscountSpecification.percentageGreaterThan(filter.getPercengateGreater()) : null);
        }

        return discountRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort))).map(discountMapper::discountToDiscountDTO);
    }

    @Override
    public DiscountReadDTO update(Long id, DiscountCreateUpdateDTO entity) {
        return discountRepository.findById(id).map(discount -> {
            Discount tempDiscount = discountMapper.discountDTOToDiscount(entity);
            discount.setCode(tempDiscount.getCode());
            discount.setPercentage(tempDiscount.getPercentage());
            discount.setExpiryDate(tempDiscount.getExpiryDate());
            discount.setDescription(tempDiscount.getDescription());
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
