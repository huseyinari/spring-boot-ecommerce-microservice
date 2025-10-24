package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

@Entity(name = "ProductVariantValue")
@Table(name = "product_variant_value")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantValue extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_value_id_sequence")
    @SequenceGenerator(name = "product_variant_value_id_sequence", sequenceName = "product_variant_value_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "variant_value", nullable = false)
    private String variantValue;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_variant_id", referencedColumnName = "id", nullable = false)
    private ProductVariant productVariant;
}
