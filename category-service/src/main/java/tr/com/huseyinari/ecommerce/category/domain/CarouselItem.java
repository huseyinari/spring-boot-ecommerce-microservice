package tr.com.huseyinari.ecommerce.category.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.domain.AbstractAuditableEntity;

@Entity(name = "CarouselItem")
@Table(name = "carousel_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarouselItem extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carousel_item_id_sequence")
    @SequenceGenerator(name = "carousel_item_id_sequence", sequenceName = "carousel_item_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "link")
    private String link;

    @Column(name = "link_title")
    private String linkTitle;

    @Column(name = "carousel_name", nullable = false) // Hangi sayfadaki carousel'in bir itemi olduğunu belli etmek için
    private String carouselName;

    @Column(name = "carousel_list_order", nullable = false) // carousel'de item'in sırası
    private Integer listOrder;

    @Column(name = "image_storage_object_id", nullable = false)
    private Long imageStorageObjectId;
}
