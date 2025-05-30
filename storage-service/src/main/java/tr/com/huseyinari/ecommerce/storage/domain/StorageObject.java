package tr.com.huseyinari.ecommerce.storage.domain;

import jakarta.persistence.*;
import lombok.*;
import tr.com.huseyinari.springdatajpa.AbstractAuditableEntity;

@Entity(name = "StorageObject")
@Table(name = "storage_objects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageObject extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_objects_id_sequence")
    @SequenceGenerator(name = "storage_objects_id_sequence", sequenceName = "storage_objects_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Column(name = "storage_name", nullable = false)
    private String storageName;     // Amazon karşılığı bucket adı

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "is_private_access", nullable = false)
    private boolean privateAccess;

    @Column(name = "owner_id")
    private String ownerId;
}
