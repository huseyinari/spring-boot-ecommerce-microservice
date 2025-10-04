package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantRepository;

@Service
@RequiredArgsConstructor
public class ProductVariantService {
    private ProductVariantRepository repository;


}
