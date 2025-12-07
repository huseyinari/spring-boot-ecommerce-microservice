package tr.com.huseyinari.ecommerce.product.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantIndex;
import tr.com.huseyinari.ecommerce.product.enums.ProductVariantDataType;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantIndexMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantIndexRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantIndexCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeValueSearchResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantIndexCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantValueSearchResponse;
import tr.com.huseyinari.utils.DateUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Validated
public class ProductVariantIndexService {
    private final Logger logger = LoggerFactory.getLogger(ProductVariantIndexService.class);

    private final ProductVariantIndexRepository repository;
    private final ProductVariantIndexMapper mapper;
    private final ProductService productService;
    private final ProductVariantValueService productVariantValueService;
    private final ProductAttributeValueService productAttributeValueService;

    @Transactional
    public ProductVariantIndexCreateResponse createAll(@Valid ProductVariantIndexCreateRequest request) {
        final String productId = request.productId();
        ProductSearchResponse productSearchResponse = this.productService.findById(productId);

        if (productSearchResponse == null) {
            throw new RuntimeException("Ürün bulunamadı !");
        }

        List<ProductAttributeValueSearchResponse> productAttributeValueList = this.productAttributeValueService.findAllByProductIdOrderByProductAttributeId(productId);
        List<ProductVariantValueSearchResponse> productVariantValueList = this.productVariantValueService.findAllByProductIdOrderByProductVariantId(productId);

        for (List<Long> productVariantValueIdCombination : request.productVariantValueIdCombinations()) {
            Map<String, Object> indexJson = new HashMap<>();
            for (ProductAttributeValueSearchResponse productAttributeValue : productAttributeValueList) {
                String key = productAttributeValue.productAttribute().queryName();
                String value = productAttributeValue.attributeValue();

                indexJson.put(key, value);
            }

            List<ProductVariantValueSearchResponse> variantValues = productVariantValueList
                    .stream()
                    .filter(productVariantValue -> productVariantValueIdCombination.contains(productVariantValue.id()))
                    .sorted(Comparator.comparing(productVariantValue -> productVariantValue.productVariant().productVariantIndexJsonOrderNumber())) // productVariantIndexJsonOrderNumber alanına göre sırala
                    .toList();

            for (ProductVariantValueSearchResponse productVariantValue : variantValues) {
                String key = productVariantValue.productVariant().queryName();
                Object value = this.getData(productVariantValue.productVariant().dataType(), productVariantValue.variantValue());

                indexJson.put(key, value);
            }

            ProductVariantIndex productVariantIndex = new ProductVariantIndex();

            Product product = new Product();
            product.setId(productId);

            productVariantIndex.setProduct(product);
            productVariantIndex.setStock(request.stock());
            productVariantIndex.setPrice(request.price());
            productVariantIndex.setSkuCode(request.skuCode());
            productVariantIndex.setQueryOrder(request.queryOrder());

            if (request.discount() != null && request.discount().compareTo(BigDecimal.ZERO) > 0) {
                productVariantIndex.setDiscount(request.discount());
            } else {
                productVariantIndex.setDiscount(BigDecimal.ZERO);
            }

            productVariantIndex.setDiscountedPrice(productVariantIndex.getPrice().subtract(productVariantIndex.getDiscount()));
        }

        return null;
    }

    private Object getData(ProductVariantDataType dataType, String value) {
        switch (dataType) {
            case STRING -> {
                return value;
            }
            case INTEGER -> {
                return Integer.parseInt(value);
            }
            case BOOLEAN -> {
                return Boolean.parseBoolean(value);
            }
            case DECIMAL -> {
                return new BigDecimal(value);
            }
            case DATE -> {
                return DateUtils.parseDate(value);
            }
            case DATE_TIME -> {
                return DateUtils.parseDateTime(value);
            }
            default -> {
                return null;
            }
        }
    }
}
