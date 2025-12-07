package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.ecommerce.product.domain.converter.ProductVariantIndexConverter;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

import java.math.BigDecimal;
import java.util.Map;

@Entity(name = "ProductVariantIndex")
@Table(name = "product_variant_index")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantIndex extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_index_id_sequence")
    @SequenceGenerator(name = "product_variant_index_id_sequence", sequenceName = "product_variant_index_id_sequence", allocationSize = 1)
    private Long id;

    @Convert(converter = ProductVariantIndexConverter.class)
    @Column(name = "variant_value_index", nullable = false)
    private Map<String, Object> variantValueIndex;

    @Column(name = "sku_code", nullable = false, unique = true)
    private String skuCode;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "discounted_price", nullable = false)
    private BigDecimal discountedPrice;

    @Column(name = "query_order", nullable = false)
    private Integer queryOrder;     // Ürüne ait arama yapıldığında gösterilme sırası

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
}
