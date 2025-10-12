package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.product.mapper.ProductInspectMapper;
import tr.com.huseyinari.ecommerce.product.projection.MostInspectedProductProjection;
import tr.com.huseyinari.ecommerce.product.repository.ProductInspectRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInspectService {
    private final Logger logger = LoggerFactory.getLogger(ProductInspectService.class);

    private final ProductInspectRepository repository;
    private final ProductInspectMapper mapper;

    public List<MostInspectedProductProjection> getMostInspectedProductsToday() {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        Pageable limitFour = Pageable.ofSize(4);

        return this.repository.getMostInspectedProductsToday(startOfDay, endOfDay, limitFour);
    }
}
