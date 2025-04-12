package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity(name = "ProductImage")
@Table(name = "product_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@EqualsAndHashCode
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_id_sequence")
    @SequenceGenerator(name = "product_image_id_sequence", sequenceName = "product_image_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

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
