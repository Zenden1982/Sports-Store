package com.zenden.sports_store.Handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.zenden.sports_store.Classes.DTO.ProductReadDTO;
import com.zenden.sports_store.Repositories.DiscountRepository;


//Позволяет выводить цену продукта со скидкой и без через Handler
@ControllerAdvice
public class DiscountHandler implements ResponseBodyAdvice<Object>{ 

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> parameterType = returnType.getParameterType();
        return HttpEntity.class.isAssignableFrom(parameterType);
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null) {
            List<String> discountHeaders = request.getHeaders().get("Discount");
            boolean applyDiscount = discountHeaders != null && discountHeaders.stream().anyMatch("true"::equalsIgnoreCase);
            if (applyDiscount) {
                if (body instanceof ProductReadDTO) {
                    applyDiscount((ProductReadDTO) body);
                } else if (body instanceof Page) {
                    Page<?> page = (Page<?>) body;
                    page.forEach(item -> {
                        if (item instanceof ProductReadDTO) {
                            applyDiscount((ProductReadDTO) item);
                        }
                    });
                }
            }
        }
        return body;
    }

    public void applyDiscount(ProductReadDTO productReadDTO) {
        int discount = discountRepository.findByProductId(productReadDTO.getId()).get().getPercentage();
        productReadDTO.setPrice(productReadDTO.getPrice() - (productReadDTO.getPrice() * discount / 100));
    }

}