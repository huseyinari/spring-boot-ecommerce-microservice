package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantValueRepository;

@Service
@RequiredArgsConstructor
public class ProductVariantValueService {
    private final ProductVariantValueRepository repository;


}
