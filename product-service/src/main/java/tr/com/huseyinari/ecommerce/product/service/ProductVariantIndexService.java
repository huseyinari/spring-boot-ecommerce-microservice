package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantIndexMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantIndexRepository;

@Service
@RequiredArgsConstructor
public class ProductVariantIndexService {
    private final Logger logger = LoggerFactory.getLogger(ProductVariantIndexService.class);

    private final ProductVariantIndexRepository repository;
    private final ProductVariantIndexMapper mapper;
}
