package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.ecommerce.product.enums.ProductVariantDataType;
import tr.com.huseyinari.ecommerce.product.enums.ProductVariantUiComponent;
import tr.com.huseyinari.springdatajpa.AbstractAuditableEntity;

@Entity(name = "ProductVariant")
@Table(name = "product_variant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_id_sequence")
    @SequenceGenerator(name = "product_variant_id_sequence", sequenceName = "product_variant_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private ProductVariantDataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ui_component", nullable = false)
    private ProductVariantUiComponent uiComponent;

    @Column(name = "min_value", nullable = false)
    private String minValue;

    @Column(name = "max_value", nullable = false)
    private String maxValue;
}
