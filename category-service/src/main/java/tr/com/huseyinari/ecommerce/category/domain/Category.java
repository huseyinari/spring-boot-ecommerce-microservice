package tr.com.huseyinari.ecommerce.category.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.AbstractAuditableEntity;

@Entity(name = "Category")
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_sequence")
    @SequenceGenerator(name = "category_id_sequence", sequenceName = "category_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "category_name", nullable = false, unique = true)
    private String name;

    @Column(name = "image_url", nullable = false, unique = true)
    private String imageUrl;

    @Column(name = "total_product_count", nullable = false)
    private Integer totalProductCount;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_id", referencedColumnName = "id")
//    private Category parent;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "image_storage_object_id", nullable = false)
    private Long imageStorageObjectId;
}
