package udpm.hn.server.test.infrastructure.constant;

import jakarta.persistence.PrePersist;
import udpm.hn.server.test.entity.base.PrimaryEntity;

import java.util.UUID;

public class CreatePrimaryEntityListener {
    @PrePersist
    private void onCreate(PrimaryEntity entity) {
        entity.setId(UUID.randomUUID().toString());
        entity.setStatus(EntityStatus.ACTIVE);
    }
}
