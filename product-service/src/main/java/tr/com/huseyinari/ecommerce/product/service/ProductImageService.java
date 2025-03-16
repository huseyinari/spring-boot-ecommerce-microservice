package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;
import tr.com.huseyinari.ecommerce.product.repository.ProductImageRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository repository;

}
