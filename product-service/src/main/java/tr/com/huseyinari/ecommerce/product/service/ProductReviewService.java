package tr.com.huseyinari.ecommerce.product.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.ecommerce.product.domain.ProductReview;
import tr.com.huseyinari.ecommerce.product.mapper.ProductReviewMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductReviewRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductReviewCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductReviewCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductReviewSearchResponse;
import tr.com.huseyinari.springweb.rest.RequestUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ProductReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ProductReviewService.class);

    private final ProductReviewRepository repository;
    private final ProductReviewMapper mapper;

    @Transactional(readOnly = true)
    public ProductReviewSearchResponse findOne(Long id) {
        final String currentUsername = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_NAME).orElse(null);

        ProductReview productReview = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Ürün değerlendirmesi bulunamadı !"));
        return this.mapper.toSearchResponse(productReview, currentUsername);
    }

    @Transactional(readOnly = true)
    public List<ProductReviewSearchResponse> findAllByProductId(String productId) {
        final String currentUsername = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_NAME).orElse(null);

        return this.repository.findByProduct_Id(productId)
                .stream()
                .map(productReview -> this.mapper.toSearchResponse(productReview, currentUsername))
                .toList();
    }

    @Transactional
    public ProductReviewCreateResponse create(@Valid ProductReviewCreateRequest request) {
        final String currentUsername = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_NAME).orElse(null);

        ProductReview productReview = this.mapper.toEntity(request);
        productReview = this.repository.save(productReview);

        return this.mapper.toCreateResponse(productReview, currentUsername);
    }

    @Transactional
    public void delete(Long id) {
        ProductReviewSearchResponse productReview = this.findOne(id);

        if (!productReview.deletable()) {
            throw new RuntimeException("Bu değerlendirmeyi silme yetkiniz yok !");
        }

        this.repository.deleteById(id);
    }
}
