package udpm.hn.server.test.entity;

import jakarta.persistence.*;
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
@Table(name = "serial")
public class Serial extends PrimaryEntity implements Serializable {

    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
}
