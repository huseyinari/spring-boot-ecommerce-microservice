package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.repository.ProductAttributeValueRepository;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueService {
    private ProductAttributeValueRepository repository;


}
