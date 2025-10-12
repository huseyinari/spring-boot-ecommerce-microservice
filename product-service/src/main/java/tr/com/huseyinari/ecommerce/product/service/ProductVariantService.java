package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantRepository;

@Service
@RequiredArgsConstructor
public class ProductVariantService {
    private final Logger logger = LoggerFactory.getLogger(ProductVariantService.class);

    private final ProductVariantRepository repository;
    private final ProductVariantMapper mapper;

}
