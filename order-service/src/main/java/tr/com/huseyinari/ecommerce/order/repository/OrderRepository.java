package tr.com.huseyinari.ecommerce.order.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.order.domain.Order;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

@Repository
public interface OrderRepository extends BaseJpaQueryDslRepository<Order, Long> {
}
