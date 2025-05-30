package tr.com.huseyinari.ecommerce.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.storage.domain.StorageObject;

@Repository
public interface StorageObjectRepository extends JpaRepository<StorageObject, Long> {
}
