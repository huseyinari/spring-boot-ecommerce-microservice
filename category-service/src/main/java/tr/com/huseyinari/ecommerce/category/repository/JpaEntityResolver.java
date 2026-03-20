package tr.com.huseyinari.ecommerce.category.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.springdatajpa.BaseJpaEntityResolver;

@Component
public class JpaEntityResolver extends BaseJpaEntityResolver {
    public JpaEntityResolver(EntityManager entityManager) {
        super(entityManager);
    }
}