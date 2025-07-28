package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.projection.MostViewedProductProjection;
import tr.com.huseyinari.ecommerce.product.repository.ProductReviewRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReviewService {
    private final ProductReviewRepository repository;

    public List<MostViewedProductProjection> getMostViewedProductsToday() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        Pageable limitFour = Pageable.ofSize(4);

        return this.repository.findMostViewedProductsByDate(startOfDay, endOfDay, limitFour);
    }
}
