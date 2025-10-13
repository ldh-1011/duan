package udpm.hn.server.test.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import udpm.hn.server.test.infrastructure.constant.AuditEntityListener;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public class AuditEntity {
    @Column(updatable = false)
    private Long createdDate;

    @Column
    private Long lastModifiedDate;

}
