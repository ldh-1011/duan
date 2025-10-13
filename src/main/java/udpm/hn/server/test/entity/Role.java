package udpm.hn.server.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import udpm.hn.server.test.entity.base.PrimaryEntity;
import udpm.hn.server.test.infrastructure.constant.EntityProperties;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
@Entity
@Table(name = "role")
public class Role extends PrimaryEntity implements Serializable {
    @Column(name = "name",length = EntityProperties.LENGTH_NAME)
    private String name;

    @Column(name = "code",length = EntityProperties.LENGTH_NAME)
    private String code;
}

