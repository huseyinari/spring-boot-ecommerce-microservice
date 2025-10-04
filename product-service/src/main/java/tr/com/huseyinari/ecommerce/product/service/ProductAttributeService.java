package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.repository.ProductAttributeRepository;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {
    private final ProductAttributeRepository repository;


}
