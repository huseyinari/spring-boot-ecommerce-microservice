package tr.com.huseyinari.ecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.order.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
