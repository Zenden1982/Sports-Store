package com.zenden.sports_store.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenden.sports_store.Classes.DTO.ProductCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.ProductReadDTO;
import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Filters.Product.ProductFiler;
import com.zenden.sports_store.Filters.Product.ProductSpecification;
import com.zenden.sports_store.Mapper.ProductMapper;
import com.zenden.sports_store.Repositories.ProductRepository;

@Transactional
@Component
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @CacheEvict(value = "products", allEntries = true)
    public ProductReadDTO create(ProductCreateUpdateDTO entity) {
        return Optional
                .ofNullable(productRepository.saveAndFlush(productMapper.productCreateUpdateDTOToProduct(entity)))
                .map(product -> {
                    return productMapper.productToProductReadDTO(product);
                }).orElseThrow(() -> new RuntimeException("Error creating product"));
    }

    // @Cacheable(value = "products", key = "#id")
    public ProductReadDTO read(Long id, String currency) {
        return productRepository.findById(id).map(productMapper::productToProductReadDTO).map(product -> {
            product.setPrice(exchangeRateService.getActualExchangeRate(product.getPrice(), currency));
            return product;
        })
                .orElseThrow(() -> new RuntimeException("Error reading product" + id));
    }

    // TODO Поставить size, который будет стоять по умолчанию

    // @Cacheable(value = "products", key = "#page == 0 && #size == 10 ?
    // 'firstPage-size10-' + #sort + '-' +
    // T(org.springframework.util.DigestUtils).md5DigestAsHex(#filter.toString().bytes)
    // : null", condition = "#page == 0 && #size == 10")
    public Page<ProductReadDTO> readAll(int page, int size, String sort, ProductFiler filter, String currency) {
        Specification<Product> spec = Specification.where(null);
        if (filter != null) {
            spec = spec.and(filter.getName() != null && !filter.getName().isEmpty()
                    ? ProductSpecification.nameLike(filter.getName())
                    : null);
            spec = spec.and(filter.getDescription() != null && !filter.getDescription().isEmpty()
                    ? ProductSpecification.descriptionLike(filter.getDescription())
                    : null);
            spec = spec.and(filter.getPriceLess() != null
                    ? ProductSpecification.priceLessThan(filter.getPriceLess())
                    : null);
            spec = spec.and(filter.getPriceGreater() != null
                    ? ProductSpecification.priceGreaterThan(filter.getPriceGreater())
                    : null);
            spec = spec.and(filter.getCategoryId() != null
                    ? ProductSpecification.categoryEquals(filter.getCategoryId())
                    : null);
        }
        try {
            return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort)))
                    .map(productMapper::productToProductReadDTO).map(product -> {
                        product.setPrice(exchangeRateService.getActualExchangeRate(product.getPrice(), currency));
                        return product;
                    });
        } catch (RuntimeException e) {
            throw new RuntimeException("Error reading all products", e);
        }
    }

    @CacheEvict(value = "products", allEntries = true)

    public ProductReadDTO update(Long id, ProductCreateUpdateDTO entity) {
        return productRepository.findById(id).map(product -> {
            Product tempProduct = productMapper.productCreateUpdateDTOToProduct(entity);
            product.setProductName(tempProduct.getProductName());
            product.setProductDescription(tempProduct.getProductDescription());
            product.setPrice(tempProduct.getPrice());
            product.setStock(tempProduct.getStock());
            product.setCategory(tempProduct.getCategory());
            return productMapper.productToProductReadDTO(productRepository.saveAndFlush(product));
        }).orElseThrow(() -> new RuntimeException("Error updating product" + id));
    }

    @CacheEvict(value = "products", key = "#id", allEntries = true)

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting product" + id, e);
        }
    }

}
