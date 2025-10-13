package udpm.hn.server.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import udpm.hn.server.test.entity.base.PrimaryEntity;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
@Entity
@Table(name = "rom")
public class rom  extends PrimaryEntity implements Serializable {
    @Column(name = "code_rom")
    private String codeRom;

    @Column(name = "name_rom")
    private String name;


}
