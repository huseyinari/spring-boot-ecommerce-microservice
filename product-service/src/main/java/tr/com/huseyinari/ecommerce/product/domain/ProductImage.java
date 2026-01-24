package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

import java.util.Objects;

@Entity(name = "ProductImage")
@Table(name = "product_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@EqualsAndHashCode
public class ProductImage extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_id_sequence")
    @SequenceGenerator(name = "product_image_id_sequence", sequenceName = "product_image_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "storage_object_id", nullable = false)
    private Long storageObjectId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_variant_index_id", referencedColumnName = "id")
    private ProductVariantIndex productVariantIndex;

    // ------

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductImage productImage = (ProductImage) obj;
        return Objects.equals(this.id, productImage.id);
    }
}
