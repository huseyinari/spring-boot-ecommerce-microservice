package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantOptionMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantOptionRepository;

@Service
@RequiredArgsConstructor
public class ProductVariantOptionService {
    private final Logger logger = LoggerFactory.getLogger(ProductVariantOptionService.class);

    private final ProductVariantOptionRepository repository;
    private final ProductVariantOptionMapper mapper;

}
