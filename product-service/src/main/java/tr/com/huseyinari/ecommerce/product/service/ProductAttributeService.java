package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.mapper.ProductAttributeMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductAttributeRepository;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {
    private final Logger logger = LoggerFactory.getLogger(ProductAttributeService.class);

    private final ProductAttributeRepository repository;
    private final ProductAttributeMapper mapper;

}
