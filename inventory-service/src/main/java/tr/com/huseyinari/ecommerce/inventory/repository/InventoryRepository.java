package tr.com.huseyinari.ecommerce.inventory.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.inventory.domain.Inventory;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends BaseJpaQueryDslRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);

    Optional<Inventory> findBySkuCode(String skuCode);

}
