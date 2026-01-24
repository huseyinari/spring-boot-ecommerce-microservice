package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.ecommerce.product.enums.ProductStatus;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Product")
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends AbstractAuditableEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "sku_code", nullable = false, unique = true)
    private String skuCode;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "discounted_price", nullable = false)
    private BigDecimal discountedPrice;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @Column(name = "failure_description")
    private String failureDescription;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> productImages = new HashSet<>();
}
