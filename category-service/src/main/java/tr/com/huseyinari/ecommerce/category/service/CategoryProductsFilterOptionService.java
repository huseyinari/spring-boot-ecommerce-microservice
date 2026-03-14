package tr.com.huseyinari.ecommerce.category.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import feign.FeignException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.category.client.ProductVariantIndexClient;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.domain.CategoryProductsFilterOption;
import tr.com.huseyinari.ecommerce.category.domain.QCategoryProductsFilterOption;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterType;
import tr.com.huseyinari.ecommerce.category.exception.CategoryProductsFilterOptionNotFoundException;
import tr.com.huseyinari.ecommerce.category.mapper.CategoryProductsFilterOptionMapper;
import tr.com.huseyinari.ecommerce.category.repository.CategoryProductsFilterOptionRepository;
import tr.com.huseyinari.ecommerce.category.request.CategoryProductsFilterOptionCreateRequest;
import tr.com.huseyinari.ecommerce.category.request.CategoryProductsFilterOptionSearchRequest;
import tr.com.huseyinari.ecommerce.category.request.CategoryProductsFilterOptionUpdateRequest;
import tr.com.huseyinari.ecommerce.category.response.*;
import tr.com.huseyinari.ecommerce.category.shared.response.ProductVariantIndexGroupSearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;
import tr.com.huseyinari.utils.CollectionUtils;
import tr.com.huseyinari.utils.NumberUtils;
import tr.com.huseyinari.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class CategoryProductsFilterOptionService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryProductsFilterOptionService.class);

    private final CategoryProductsFilterOptionRepository repository;
    private final CategoryProductsFilterOptionMapper mapper;
    private final ProductVariantIndexClient productVariantIndexClient;
    private final CategoryService categoryService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public CategoryProductsFilterOptionSearchResponse findOne(Long id) {
        CategoryProductsFilterOption categoryProductsFilterOption = this.repository.findById(id).orElseThrow(CategoryProductsFilterOptionNotFoundException::new);
        return this.mapper.toSearchResponse(categoryProductsFilterOption);
    }

    @Transactional(readOnly = true)
    public CategoryProductsFilterOptionPageableResponse search(CategoryProductsFilterOptionSearchRequest request, Pageable pageable) {
        QCategoryProductsFilterOption qCategoryProductsFilterOption = QCategoryProductsFilterOption.categoryProductsFilterOption;

        BooleanBuilder where = new BooleanBuilder();

        if (StringUtils.isNotBlank(request.name())) {
            where.and(qCategoryProductsFilterOption.name.likeIgnoreCase("%" + request.name() + "%"));
        }
        if (StringUtils.isNotBlank(request.queryName())) {
            where.and(qCategoryProductsFilterOption.queryName.likeIgnoreCase("%" + request.queryName() + "%"));
        }
        if (NumberUtils.greaterThen(request.categoryId(), 0L)) {
            where.and(qCategoryProductsFilterOption.category.id.eq(request.categoryId()));
        }

        JPAQueryFactory totalQuery = new JPAQueryFactory(this.entityManager);
        Long total = totalQuery
                .select(qCategoryProductsFilterOption.count())
                .from(qCategoryProductsFilterOption)
                .where(where)
                .fetchOne();

        JPAQueryFactory query = new JPAQueryFactory(this.entityManager);
        List<CategoryProductsFilterOption> categoryProductsFilterOptionList = query
                .select(qCategoryProductsFilterOption)
                .from(qCategoryProductsFilterOption)
                .where(where)
                .orderBy(this.getOrderSpecifier(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Page<CategoryProductsFilterOption> pageResult = new PageImpl<>(categoryProductsFilterOptionList, pageable, total != null ? total : 0L);
        return this.mapper.toSearchPageableResponse(pageResult);
    }

    @Transactional(readOnly = true)
    public List<CategoryProductsFilterOptionSearchResponse> getFilterOptionsByCategoryId(Long categoryId) {
        List<CategoryProductsFilterOptionSearchResponse> categoryProductsFilterOptionSearchResponseList = this.repository.findByCategory_Id(categoryId)
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();

        List<String> queryNameList = categoryProductsFilterOptionSearchResponseList
                .stream()
                .map(CategoryProductsFilterOptionSearchResponse::getQueryName)
                .toList();

        if (CollectionUtils.isEmpty(queryNameList))
            return Collections.emptyList();

        SinhaRestApiResponse<List<ProductVariantIndexGroupSearchResponse>> productVariantIndexApiResponse;
        try {
            productVariantIndexApiResponse = this.productVariantIndexClient.findProductVariantIndexGroupsByQueryNameList(queryNameList, categoryId);
        } catch (FeignException.NotFound exception) {
            throw new RuntimeException("Ürün varyant kombinasyonları servisi bulunamadı.");
        } catch (Exception exception) {
            throw new RuntimeException("Ürün varyant kombinasyonları servisine erişilemedi. Lütfen daha sonra tekrar deneyiniz.");
        }

        List<ProductVariantIndexGroupSearchResponse> productVariantIndexGroupSearchResponseList = productVariantIndexApiResponse.getData();

        for (CategoryProductsFilterOptionSearchResponse categoryProductsFilterOptionSearchResponse : categoryProductsFilterOptionSearchResponseList) {
            List<CategoryProductsFilterOptionSearchResponseValue> values = productVariantIndexGroupSearchResponseList
                    .stream()
                    .filter(productVariantIndexGroupSearchResponse ->
                        productVariantIndexGroupSearchResponse.getQueryName().equals(categoryProductsFilterOptionSearchResponse.getQueryName())
                    )
                    .map(productVariantIndexGroupSearchResponse ->
                        new CategoryProductsFilterOptionSearchResponseValue(
                            productVariantIndexGroupSearchResponse.getQueryValue(),
                            BigDecimal.ZERO,
                            BigDecimal.ZERO,
                            productVariantIndexGroupSearchResponse.getTotal()
                        )
                    )
                    .toList();

            Integer maxOptions = categoryProductsFilterOptionSearchResponse.getMaxFilterOption();

            if (maxOptions != null && maxOptions > 0) {
                values = values.stream()    // maxFilterOption değerinden fazla sonuç varsa en fazla ürün içerenden başlayarak x tane al
                        .sorted((a, b) -> Long.compare(b.getTotal(), a.getTotal()))
                        .limit(maxOptions)
                        .toList();
            }

            if (CategoryProductsFilterType.RANGE.equals(categoryProductsFilterOptionSearchResponse.getFilterType())) {
                List<CategoryProductsFilterOptionSearchResponseValue> rangeValues = buildRangeValues(values, maxOptions);

                categoryProductsFilterOptionSearchResponse.getValues().addAll(rangeValues);
            } else {
                categoryProductsFilterOptionSearchResponse.getValues().addAll(values);
            }
        }

        return categoryProductsFilterOptionSearchResponseList;
    }

    @Transactional
    public CategoryProductsFilterOptionCreateResponse create(@Valid CategoryProductsFilterOptionCreateRequest request) {
        CategorySearchResponse category = this.categoryService.findOne(request.categoryId());

        CategoryProductsFilterOption categoryProductsFilterOption = this.mapper.toEntity(request);
        categoryProductsFilterOption = this.repository.save(categoryProductsFilterOption);

        return this.mapper.toCreateResponse(categoryProductsFilterOption, category);
    }

    @Transactional
    public CategoryProductsFilterOptionUpdateResponse update(@Valid CategoryProductsFilterOptionUpdateRequest request) {
        CategoryProductsFilterOption categoryProductsFilterOption = this.repository.findById(request.id())
                .orElseThrow(CategoryProductsFilterOptionNotFoundException::new);

        CategorySearchResponse category = this.categoryService.findOne(request.categoryId());

        this.mapper.fromUpdateRequestToEntity(request, categoryProductsFilterOption);
        categoryProductsFilterOption = this.repository.save(categoryProductsFilterOption);

        return this.mapper.toUpdateResponse(categoryProductsFilterOption, category);
    }

    @Transactional
    public void delete(Long id) {
        CategoryProductsFilterOptionSearchResponse categoryProductsFilterOption = this.findOne(id);
        this.repository.deleteById(id);
    }

    private List<CategoryProductsFilterOptionSearchResponseValue> buildRangeValues(
            List<CategoryProductsFilterOptionSearchResponseValue> values,
            int rangeCount
    ) {
        if (values == null || values.isEmpty() || rangeCount <= 0) {
            return List.of();
        }

        // 1️⃣ Fiyatları queryValue'dan al
        List<BigDecimal> prices = values.stream()
                .map(v -> new BigDecimal(v.getQueryValue()))
                .toList();

        BigDecimal minPrice = prices.stream().min(BigDecimal::compareTo).get();
        BigDecimal maxPrice = prices.stream().max(BigDecimal::compareTo).get();

        long minRaw = minPrice.longValue();
        long maxRaw = maxPrice.longValue();

        // 2️⃣ Step hesapla
        long rawStep = (maxRaw - minRaw) / rangeCount;
        if (rawStep == 0) rawStep = 1;

        long step = normalizeStep(rawStep);

        long min = (minRaw / step) * step;
        long max = ((maxRaw + step - 1) / step) * step;

        List<CategoryProductsFilterOptionSearchResponseValue> ranges = new ArrayList<>();

        // 3️⃣ Range üret
        for (int i = 0; i < rangeCount; i++) {
            long rangeMin = min + step * i;
            long rangeMax = (i == rangeCount - 1)
                    ? max
                    : rangeMin + step;

            CategoryProductsFilterOptionSearchResponseValue r =
                    new CategoryProductsFilterOptionSearchResponseValue();

            r.setQueryValue(null);
            r.setMinValue(BigDecimal.valueOf(rangeMin));
            r.setMaxValue(BigDecimal.valueOf(rangeMax));
            r.setTotal(0L);

            ranges.add(r);
        }

        // 4️⃣ Ürünleri range’lere dağıt
        for (CategoryProductsFilterOptionSearchResponseValue v : values) {
            long price = new BigDecimal(v.getQueryValue()).longValue();

            int index = (int) ((price - min) / step);
            if (index < 0) index = 0;
            if (index >= rangeCount) index = rangeCount - 1;

            ranges.get(index).setTotal(
                    ranges.get(index).getTotal() + v.getTotal()
            );
        }

        return ranges;
    }

    private long normalizeStep(long rawStep) {
        long magnitude = (long) Math.pow(10, String.valueOf(rawStep).length() - 1);

        if (rawStep <= magnitude) return magnitude;
        if (rawStep <= magnitude * 2) return magnitude * 2;
        if (rawStep <= magnitude * 5) return magnitude * 5;

        return magnitude * 10;
    }

    private OrderSpecifier<?>[] getOrderSpecifier(Pageable pageable) {
        QCategoryProductsFilterOption qCategoryProductsFilterOption = QCategoryProductsFilterOption.categoryProductsFilterOption;

        if (pageable.getSort().isEmpty()) {
            return new OrderSpecifier[]{ new OrderSpecifier(Order.DESC, qCategoryProductsFilterOption.createdDate) };
        }

        return pageable.getSort().stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            switch (property) {
                case "name":
                    return new OrderSpecifier(direction, qCategoryProductsFilterOption.name);
                case "queryName":
                    return new OrderSpecifier(direction, qCategoryProductsFilterOption.queryName);
                case "filterType":
                    return new OrderSpecifier(direction, qCategoryProductsFilterOption.filterType);
                case "uiComponent":
                    return new OrderSpecifier(direction, qCategoryProductsFilterOption.uiComponent);
                case "maxFilterOption":
                    return new OrderSpecifier(direction, qCategoryProductsFilterOption.maxFilterOption);
                case "category":
                    return new OrderSpecifier(direction, qCategoryProductsFilterOption.category.name);
                case "createdDate":
                    return new OrderSpecifier(direction, qCategoryProductsFilterOption.createdDate);
                default:
//                    // Gönderilen property'ye göre uygun path'i belirleme
//                    PathBuilder<CategoryProductsFilterOption> pathBuilder = new PathBuilder<>(CategoryProductsFilterOption.class, "categoryProductsFilterOption");
//                    return new OrderSpecifier(direction, pathBuilder.get(property));
                    throw new RuntimeException("Geçersiz sıralama parametresi: " + property);
            }
        }).toArray(OrderSpecifier[]::new);
    }
}
