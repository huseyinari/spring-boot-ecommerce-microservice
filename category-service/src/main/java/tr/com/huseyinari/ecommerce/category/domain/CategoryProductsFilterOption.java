package tr.com.huseyinari.ecommerce.category.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterType;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterUiComponent;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

@Entity(name = "CategoryProductsFilterOption")
@Table(name = "category_products_filter_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryProductsFilterOption extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_products_filter_options_id_sequence")
    @SequenceGenerator(name = "category_products_filter_options_id_sequence", sequenceName = "category_products_filter_options_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "query_name", nullable = false, unique = true)
    private String queryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "filter_type", nullable = false)
    private CategoryProductsFilterType filterType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ui_component", nullable = false)
    private CategoryProductsFilterUiComponent uiComponent;

    @Column(name = "max_filter_option")
    private Integer maxFilterOption;    // En fazla kaç adet seçenek olabilir ?

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;
}
