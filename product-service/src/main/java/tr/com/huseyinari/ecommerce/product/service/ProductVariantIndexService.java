package tr.com.huseyinari.ecommerce.product.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.product.domain.*;
import tr.com.huseyinari.ecommerce.product.enums.ProductVariantDataType;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantIndexMapper;
import tr.com.huseyinari.ecommerce.product.projection.ProductVariantIndexGroupsByQueryName;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantIndexRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductSearchParameters;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantIndexCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.*;
import tr.com.huseyinari.utils.CollectionUtils;
import tr.com.huseyinari.utils.DateUtils;
import tr.com.huseyinari.utils.NumberUtils;
import tr.com.huseyinari.utils.StringUtils;

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

    @Transactional(readOnly = true)
    public ProductVariantIndexSearchPageableResponse searchProduct(ProductSearchParameters productSearchParameters, Pageable pageable) {
        QProductVariantIndex qProductVariantIndex = QProductVariantIndex.productVariantIndex;
        QProduct qProduct = QProduct.product;
        QProductImage qProductImage = QProductImage.productImage;

        BooleanBuilder where = new BooleanBuilder();
        where.and(qProductVariantIndex.queryOrder.eq(1));   // Yalnızca ilk sırada gösterilecek variant index'leri getir.

        if (StringUtils.isNotBlank(productSearchParameters.getName())) {
            where.and(qProductVariantIndex.product.name.likeIgnoreCase("%" + productSearchParameters.getName() + "%"));
        }
        if (NumberUtils.greaterThen(productSearchParameters.getCategoryId(), 0L)) {
            where.and(qProductVariantIndex.product.categoryId.eq(productSearchParameters.getCategoryId()));
        }
        if (productSearchParameters.getParams() != null) {
            for (Map.Entry<String, List<ProductSearchParameters.ProductSearchCompareValue>> entry : productSearchParameters.getParams().entrySet()) {
                String key = entry.getKey();
                List<ProductSearchParameters.ProductSearchCompareValue> valueList = entry.getValue();

                BooleanBuilder keyConditions = new BooleanBuilder();

                for (ProductSearchParameters.ProductSearchCompareValue compareValue : valueList) {
                    if (StringUtils.isNotBlank(key) && compareValue != null) {
                        BooleanBuilder valueCondition = new BooleanBuilder();

                        if (NumberUtils.greaterThen(compareValue.getMin(), BigDecimal.ZERO) && NumberUtils.greaterThen(compareValue.getMax(), BigDecimal.ZERO)) {
                            NumberTemplate<BigDecimal> numberTemplate =
                                    Expressions.numberTemplate(
                                            BigDecimal.class,
                                            "cast(function('jsonb_extract_path_text', {0}, {1}) as double)",
                                            qProductVariantIndex.variantValueIndex,
                                            Expressions.constant(key) // "price"
                                    );

                            valueCondition.or(
                                numberTemplate.between(
                                    compareValue.getMin().doubleValue(),
                                    compareValue.getMax().doubleValue()
                                )
                            );
                        }

                        if (compareValue.getValue() != null) {
                            StringTemplate stringTemplate = Expressions.stringTemplate(
                                    "jsonb_extract_path_text({0}, {1})",
                                    qProductVariantIndex.variantValueIndex,
                                    key
                            );

                            valueCondition.or(stringTemplate.eq(compareValue.getValue().toString()));
                        }

                        if (valueCondition.hasValue()) {
                            keyConditions.or(valueCondition);
                        }
                    }
                }

                if (keyConditions.hasValue()) {
                    where.and(keyConditions);
                }
            }
        }

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(this.entityManager);

        Long total = jpaQueryFactory
                .select(qProductVariantIndex.count())
                .from(qProductVariantIndex)
                .where(where)
                .fetchOne();

        OrderSpecifier[] orderList = this.getOrderSpecifier(pageable);

        List<ProductVariantIndex> productVariantIndexList = jpaQueryFactory
                .selectFrom(qProductVariantIndex)
                .innerJoin(qProduct).on(qProductVariantIndex.product.id.eq(qProduct.id))
                .where(where)
                .orderBy(orderList)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Ürünlerin resimlerini getir
        JPAQueryFactory productImageQuery = new JPAQueryFactory(this.entityManager);

        Set<String> productIdList = productVariantIndexList
                .stream()
                .map(productVariantIndex -> productVariantIndex.getProduct().getId())
                .collect(Collectors.toSet());

        List<ProductImage> productImages = productImageQuery
                .select(qProductImage)
                .from(qProductImage)
                .innerJoin(qProduct).on(qProductImage.product.id.eq(qProduct.id))
                .where(qProductImage.product.id.in(productIdList))
                .fetch();

        productVariantIndexList = productVariantIndexList
                .stream()
                .map(productVariantIndex ->  {
                    // Ürün varyantı farketmeksizin o ürüne ait bütün fotoğrafları client'a dön.
                    List<ProductImage> images = productImages
                            .stream()
                            .filter(productImage -> productImage.getProduct().getId().equals(productVariantIndex.getProduct().getId()))
                            .toList();

                    productVariantIndex.setImages(images);

                    return productVariantIndex;
                })
                .toList();

        Page<ProductVariantIndex> pageResult = new PageImpl<>(productVariantIndexList, pageable, total != null ? total : 0L);
        return this.mapper.toSearchPageableResponse(pageResult);
    }

    @Transactional(readOnly = true)
    public List<ProductVariantIndexGroupSearchResponse> findProductVariantIndexGroupsByQueryNameList(
            @NotEmpty(message = "Sorgu adı listesi boş olamaz.") List<String> queryNameList,
            Long categoryId
    ) {
        List<ProductVariantIndexGroupsByQueryName> productVariantIndexList = this.repository.findProductVariantIndexGroupsByQueryNameList(queryNameList, categoryId);
        return productVariantIndexList.stream()
                .map(productVariantIndexGroupsByQueryName ->
                     new ProductVariantIndexGroupSearchResponse(
                        productVariantIndexGroupsByQueryName.getQueryName(),
                        productVariantIndexGroupsByQueryName.getQueryValue(),
                        productVariantIndexGroupsByQueryName.getTotal()
                    )
                )
                .toList();
    }

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
        indexJson.put("price", product.getDiscountedPrice());

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

            productVariantIndex.getVariantValueIndex().put("price", productVariantIndex.getDiscountedPrice());

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

    private OrderSpecifier<?>[] getOrderSpecifier(Pageable pageable) {
        QProductVariantIndex qProductVariantIndex = QProductVariantIndex.productVariantIndex;

        if (pageable.getSort().isEmpty()) {
            return new OrderSpecifier[]{new OrderSpecifier(Order.ASC, qProductVariantIndex.product.name)};
        }

        return pageable.getSort().stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            switch (property) {
                case "productName":
                    return new OrderSpecifier(direction, qProductVariantIndex.product.name);
                case "price":
                    return new OrderSpecifier(direction, qProductVariantIndex.discountedPrice);
                case "date":
                    return new OrderSpecifier(direction, qProductVariantIndex.createdDate);
                default:
                    // Gönderilen property'ye göre uygun path'i belirleme
                    PathBuilder<ProductVariantIndex> pathBuilder = new PathBuilder<>(ProductVariantIndex.class, "productVariantIndex");
                    return new OrderSpecifier(direction, pathBuilder.get(property));
            }
        }).toArray(OrderSpecifier[]::new);
    }
}
