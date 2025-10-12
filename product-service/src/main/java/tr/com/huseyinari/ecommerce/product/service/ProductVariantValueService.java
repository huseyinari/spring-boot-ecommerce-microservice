package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantValueMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantValueRepository;

@Service
@RequiredArgsConstructor
public class ProductVariantValueService {
    private final Logger logger = LoggerFactory.getLogger(ProductVariantValueService.class);

    private final ProductVariantValueRepository repository;
    private final ProductVariantValueMapper mapper;

}
