package tr.com.huseyinari.ecommerce.product.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariant;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantValue;
import tr.com.huseyinari.ecommerce.product.domain.QProductVariant;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantOptionCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantUpdateRequest;
import tr.com.huseyinari.ecommerce.product.response.*;
import tr.com.huseyinari.utils.CollectionUtils;
import tr.com.huseyinari.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ProductVariantService {
    private static final Logger logger = LoggerFactory.getLogger(ProductVariantService.class);

    private final ProductVariantRepository repository;
    private final ProductVariantMapper mapper;
    private final ProductVariantOptionService productVariantOptionService;
    private final ProductVariantValueService productVariantValueService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public ProductVariantSearchPageableResponse search(String search, Pageable pageable) {
        QProductVariant qProductVariant = QProductVariant.productVariant;

        BooleanBuilder where = new BooleanBuilder();

        if (StringUtils.isNotBlank(search)) {
           where.and(qProductVariant.name.containsIgnoreCase(search))
            .or(qProductVariant.description.containsIgnoreCase(search))
            .or(qProductVariant.queryName.containsIgnoreCase(search));
        }

        JPAQueryFactory totalQuery = new JPAQueryFactory(this.entityManager);
        Long total = totalQuery
                .select(qProductVariant.count())
                .from(qProductVariant)
                .where(where)
                .fetchOne();

        JPAQueryFactory query = new JPAQueryFactory(this.entityManager);
        List<ProductVariant> productVariantList = query
                .select(qProductVariant)
                .from(qProductVariant)
                .where(where)
                .orderBy(this.getOrderSpecifier(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Page<ProductVariant> pageResult = new PageImpl<>(productVariantList, pageable, total != null ? total : 0L);
        return this.mapper.toSearchPageableResponse(pageResult);
    }

    @Transactional(readOnly = true)
    public List<ProductVariantSearchResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductVariantSearchResponse findOne(@NotNull Long id) {
        ProductVariant productVariant = this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ürün varyantı bulunamadı !"));

        return this.mapper.toSearchResponse(productVariant);
    }

    @Transactional
    public ProductVariantCreateResponse create(@Valid ProductVariantCreateRequest request) {
        ProductVariant productVariant = this.mapper.toEntity(request);
        productVariant = this.repository.save(productVariant);

        if (request.options() != null && !request.options().isEmpty()) {
            List<ProductVariantOptionCreateRequest> optionCreateRequestList = new ArrayList<>();

            for (String option : request.options()) {
                optionCreateRequestList.add(new ProductVariantOptionCreateRequest(productVariant.getId(), option));
            }

            this.productVariantOptionService.createAll(optionCreateRequestList);
        }

        // Oluşturulan option'lara entity üzerinden ulaşabilmek için flush işlemini yaparak veritabanından tekrar sorguluyorum
        this.entityManager.flush();
        this.entityManager.refresh(productVariant);

        return this.mapper.toCreateResponse(productVariant);
    }

    @Transactional
    public ProductVariantUpdateResponse update(@Valid ProductVariantUpdateRequest request) {
        ProductVariant exist = this.repository.findById(request.id()).orElseThrow(() -> new RuntimeException("Ürün varyantı bulunamadı"));

        if (!exist.getQueryName().equals(request.queryName())) {
            Optional<ProductVariant> optional = this.repository.findByQueryName(request.queryName());
            if (optional.isPresent()) {
                throw new RuntimeException("Sorgu adı daha önce kullanılmıştır !");
            }
        }

        this.mapper.fromUpdateRequestToEntity(request, exist);
        exist = this.repository.save(exist);

        // option'ların tamamını sil ve yeni gelenleri kaydet
        this.productVariantOptionService.deleteAllByProductVariantId(exist.getId());

        this.entityManager.flush();  // Silme sorguları DB'ye gönderilsin. Yeni oluştururken unique kısıtlamalarına takılmayalım.

        if (request.options() != null && !request.options().isEmpty()) {
            List<ProductVariantOptionCreateRequest> optionCreateRequestList = new ArrayList<>();

            for (String option : request.options()) {
                optionCreateRequestList.add(new ProductVariantOptionCreateRequest(exist.getId(), option));
            }
            this.productVariantOptionService.createAll(optionCreateRequestList);
        }

        this.entityManager.flush();
        this.entityManager.refresh(exist); // Güncellenen option'lar Entity -> mapper'a gitmeden önce alınsın.

        return this.mapper.toUpdateResponse(exist);
    }

    @Transactional
    public void delete(@NotNull Long id) {
        Optional<ProductVariant> optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("Ürün varyantı bulunamadı !");
        }

        ProductVariant productVariant = optional.get();

        List<ProductVariantValueSearchResponse> valueList = this.productVariantValueService.findAllByProductVariantId(productVariant.getId());
        if (!valueList.isEmpty() ) {
            throw new RuntimeException("Bu varyant ile eşleştirilmiş ürünler bulunduğu için silinemez.");
        }

        // Varsa option'ları sil
        if (CollectionUtils.isNotEmpty(productVariant.getOptions())) {
            this.productVariantOptionService.deleteAllByProductVariantId(productVariant.getId());

            this.entityManager.flush();
        }

        this.repository.delete(productVariant);
    }

    private OrderSpecifier<?>[] getOrderSpecifier(Pageable pageable) {
        QProductVariant qProductVariant = QProductVariant.productVariant;

        if (pageable.getSort().isEmpty()) {
            return new OrderSpecifier[]{new OrderSpecifier(Order.DESC, qProductVariant.createdDate)};
        }

        return pageable.getSort().stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            switch (property) {
                case "name":
                    return new OrderSpecifier(direction, qProductVariant.name);
                case "queryName":
                    return new OrderSpecifier(direction, qProductVariant.queryName);
                case "description":
                    return new OrderSpecifier(direction,qProductVariant.description);
                case "dataType":
                    return new OrderSpecifier(direction, qProductVariant.dataType);
                case "uiComponent":
                    return new OrderSpecifier(direction, qProductVariant.uiComponent);
                case "minValue":
                    return new OrderSpecifier(direction, qProductVariant.minValue);
                case "maxValue":
                    return new OrderSpecifier(direction, qProductVariant.maxValue);
                case "productVariantIndexJsonOrderNumber":
                    return new OrderSpecifier(direction, qProductVariant.productVariantIndexJsonOrderNumber);
                case "createdDate":
                    return new OrderSpecifier(direction, qProductVariant.createdDate);
                default:
//                    // Gönderilen property'ye göre uygun path'i belirleme
//                    PathBuilder<CategoryProductsFilterOption> pathBuilder = new PathBuilder<>(CategoryProductsFilterOption.class, "categoryProductsFilterOption");
//                    return new OrderSpecifier(direction, pathBuilder.get(property));
                    throw new RuntimeException("Geçersiz sıralama parametresi: " + property);
            }
        }).toArray(OrderSpecifier[]::new);
    }
}
