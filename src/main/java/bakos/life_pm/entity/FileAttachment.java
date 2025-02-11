package bakos.life_pm.entity;

import bakos.life_pm.enums.EntityType;
import bakos.life_pm.enums.StorageStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "file_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileAttachment extends TimestampedEntity implements CustomerRelated {

    @Column(nullable = false)
    private String fileName;

    @Column
    private String filePath;

    @Lob
    @Column
    private byte[] fileData;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private UUID parentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityType parentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorageStrategy storageStrategy;

}
