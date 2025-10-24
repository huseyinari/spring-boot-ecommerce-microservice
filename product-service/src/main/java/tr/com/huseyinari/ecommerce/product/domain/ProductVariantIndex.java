package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

import java.math.BigDecimal;

@Entity(name = "ProductVariantIndex")
@Table(name = "product_variant_index")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantIndex extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_index_id_sequence")
    @SequenceGenerator(name = "product_variant_index_id_sequence", sequenceName = "product_variant_index_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "variant_value_index", nullable = false) // TODO: JSON OLACAK
    private String variantValueIndex;

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
    private Integer queryOrder;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
}
