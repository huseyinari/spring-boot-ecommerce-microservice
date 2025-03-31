package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.AbstractAuditableEntity;

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

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "failure_description")
    private String failureDescription;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductImage> productImages = new HashSet<>();
}
