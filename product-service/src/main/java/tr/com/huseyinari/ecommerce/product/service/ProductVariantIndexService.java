package tr.com.huseyinari.ecommerce.product.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantIndex;
import tr.com.huseyinari.ecommerce.product.enums.ProductVariantDataType;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantIndexMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantIndexRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantIndexCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.*;
import tr.com.huseyinari.utils.CollectionUtils;
import tr.com.huseyinari.utils.DateUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class ProductVariantIndexService {
    private final Logger logger = LoggerFactory.getLogger(ProductVariantIndexService.class);

    private final ProductVariantIndexRepository repository;
    private final ProductVariantIndexMapper mapper;
    private final ProductVariantValueService productVariantValueService;
    private final ProductAttributeValueService productAttributeValueService;
    @Autowired
    @Lazy private ProductService productService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ProductVariantIndexCreateResponse initProduct(ProductCreateResponse product) {
        ProductVariantIndex variantIndex = new ProductVariantIndex();

        variantIndex.setSkuCode(product.getSkuCode());
        variantIndex.setStock(0);
        variantIndex.setPrice(product.getPrice());
        variantIndex.setDiscount(product.getDiscount());
        variantIndex.setDiscountedPrice(product.getDiscountedPrice());
        variantIndex.setQueryOrder(1);

        List<Long> productAttributeValueIdList = product.getAttributeValues()
                .stream()
                .map(ProductAttributeValueCreateResponse::id)
                .toList();

        List<ProductAttributeValueSearchResponse> productAttributeValues = this.productAttributeValueService.findByIdInOrderById(productAttributeValueIdList);

        Map<String, Object> indexJson = new HashMap<>();
        for (ProductAttributeValueSearchResponse item : productAttributeValues) {
            String key = item.productAttribute().queryName();
            Object value = item.attributeValue();

            indexJson.put(key, value);
        }
        variantIndex.setVariantValueIndex(indexJson);

        Product productEntity = new Product();
        productEntity.setId(product.getId());
        variantIndex.setProduct(productEntity);

        variantIndex = this.repository.save(variantIndex);

        return this.mapper.toCreateResponse(variantIndex);
    }

    @Transactional
    public List<ProductVariantIndexCreateResponse> createAll(@Valid List<ProductVariantIndexCreateRequest> requestList) {
        if (CollectionUtils.isEmpty(requestList)) {
            return Collections.emptyList();
        }

        final String productId = requestList.get(0).productId();
        this.productService.findById(productId);    // Ürün var mı kontrolü ?

        // Ürün ilk kez oluşturulurken eklenen default ProductVariantIndex'i sil
        this.deleteByProductId(productId);
        this.entityManager.flush();

        // Gelen liste içerisindeki productVariantValueIdCombinations değerlerinde birbiriyle aynı değerleri içeren elemanlar bulunamaz. Her productVariantIndex farklı productVariantValue kombinasyonlarını tutmalı !!!
        Set<List<Long>> uniqueCombinations = new HashSet<>();

        for (ProductVariantIndexCreateRequest request : requestList) {
            // Kombinasyonları karşılaştırmadan önce sıralıyoruz ki aynı değerlerin farklı sırada olması durumunda da yakalayabilelim
            List<Long> sortedCombination = new ArrayList<>(request.productVariantValueIdCombination());
            Collections.sort(sortedCombination);

            // Eğer bu kombinasyon daha önce eklenmişse, hata fırlatıyoruz
            if (!uniqueCombinations.add(sortedCombination)) {
                throw new RuntimeException("Tekrar eden varyant değer kombinasyonu bulundu");
            }
        }

        List<ProductAttributeValueSearchResponse> productAttributeValueList = this.productAttributeValueService.findAllByProductIdOrderByProductAttributeId(productId);
        List<ProductVariantValueSearchResponse> productVariantValueList = this.productVariantValueService.findAllByProductIdOrderByProductVariantId(productId);

        Set<Long> uniqueVariantIds = productVariantValueList.stream()
                .map(productVariantValue -> productVariantValue.productVariant().id())
                .collect(Collectors.toSet());

        int i = 1;
        final List<ProductVariantIndexCreateResponse> responseList = new ArrayList<>();

        for (ProductVariantIndexCreateRequest request : requestList) {
            Map<String, Object> indexJson = new HashMap<>();
            for (ProductAttributeValueSearchResponse productAttributeValue : productAttributeValueList) {
                String key = productAttributeValue.productAttribute().queryName();
                String value = productAttributeValue.attributeValue();

                indexJson.put(key, value);
            }

            List<ProductVariantValueSearchResponse> variantValues = productVariantValueList
                    .stream()
                    .filter(productVariantValue -> request.productVariantValueIdCombination().contains(productVariantValue.id()))
                    .sorted(Comparator.comparing(productVariantValue -> productVariantValue.productVariant().productVariantIndexJsonOrderNumber())) // productVariantIndexJsonOrderNumber alanına göre sırala
                    .toList();

            // Variant değerlerinden productVariant id'lerini çıkarıp bir set'e atıyoruz
            Set<Long> variantIdsInList = variantValues.stream()
                    .map(value -> value.productVariant().id())
                    .collect(Collectors.toSet());

            if (!CollectionUtils.containsAll(variantIdsInList, uniqueVariantIds)) {
                throw new RuntimeException(i + ". sıradaki varyant kombinasyonu eksik değer bulundurmaktadır !");
            }

            for (ProductVariantValueSearchResponse productVariantValue : variantValues) {
                String key = productVariantValue.productVariant().queryName();
                Object value = this.getData(productVariantValue.productVariant().dataType(), productVariantValue.variantValue());

                indexJson.put(key, value);
            }

            ProductVariantIndex productVariantIndex = new ProductVariantIndex();
            productVariantIndex.setVariantValueIndex(indexJson);

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

            productVariantIndex = this.repository.save(productVariantIndex);
            responseList.add(this.mapper.toCreateResponse(productVariantIndex));

            i++;
        }

        return responseList;
    }

    @Transactional
    public void deleteByProductId(String productId) {
        this.repository.deleteByProductId(productId);
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
