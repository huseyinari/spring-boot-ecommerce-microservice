package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

@Entity(name = "ProductAttributeValue")
@Table(name = "product_attribute_value")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeValue extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_attribute_value_id_sequence")
    @SequenceGenerator(name = "product_attribute_value_id_sequence", sequenceName = "product_attribute_value_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "attribute_value", nullable = false)
    private String attributeValue;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_attribute_id", referencedColumnName = "id", nullable = false)
    private ProductAttribute productAttribute;
}
