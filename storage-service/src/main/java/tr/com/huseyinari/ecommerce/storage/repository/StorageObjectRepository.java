package tr.com.huseyinari.ecommerce.storage.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.storage.domain.StorageObject;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

@Repository
public interface StorageObjectRepository extends BaseJpaQueryDslRepository<StorageObject, Long> {
}
