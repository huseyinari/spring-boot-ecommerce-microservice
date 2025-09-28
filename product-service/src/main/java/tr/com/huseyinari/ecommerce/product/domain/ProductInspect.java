package tr.com.huseyinari.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.AbstractAuditableEntity;

// Ürünlerin incelenmelerini tutan tablo
@Entity(name = "ProductInspect")
@Table(name = "product_inspect")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInspect extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_inspects_id_sequence")
    @SequenceGenerator(name = "product_inspects_id_sequence", sequenceName = "product_inspects_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "inspected_by_ip_address", nullable = false)
    private String ip;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
}