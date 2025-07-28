package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.AbstractAuditableEntity;

// Ürünlerin incelenmelerini tutan tablo
@Entity(name = "ProductReview")
@Table(name = "product_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReview extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_reviews_id_sequence")
    @SequenceGenerator(name = "product_reviews_id_sequence", sequenceName = "product_reviews_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "reviewed_ip_address", nullable = false)
    private String ip;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
}