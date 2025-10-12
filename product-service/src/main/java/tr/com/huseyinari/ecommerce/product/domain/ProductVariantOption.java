package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

@Entity(name = "ProductVariantOption")
@Table(name = "product_variant_option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantOption extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_option_id_sequence")
    @SequenceGenerator(name = "product_variant_option_id_sequence", sequenceName = "product_variant_option_id_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_variant_id", referencedColumnName = "id", nullable = false)
    private ProductVariant productVariant;

    @Column(name = "option_value", nullable = false)
    private String optionValue;
}