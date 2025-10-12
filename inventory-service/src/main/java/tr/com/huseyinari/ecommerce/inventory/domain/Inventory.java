package tr.com.huseyinari.ecommerce.inventory.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

@Entity(name = "Inventory")
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_sequence")
    @SequenceGenerator(name = "inventory_id_sequence", sequenceName = "inventory_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "sku_code", nullable = false, updatable = false, unique = true)
    private String skuCode;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
