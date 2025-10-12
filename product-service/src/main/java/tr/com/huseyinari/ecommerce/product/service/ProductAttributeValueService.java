package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.mapper.ProductAttributeValueMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductAttributeValueRepository;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueService {
    private final Logger logger = LoggerFactory.getLogger(ProductAttributeValueService.class);

    private final ProductAttributeValueRepository repository;
    private final ProductAttributeValueMapper mapper;
}
