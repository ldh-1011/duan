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
@Table(name = "product")
public class Product extends PrimaryEntity implements Serializable {
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "processor_id")
    private Processor processor;

    @ManyToOne
    @JoinColumn(name = "iso_id")
    private Iso iso;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "resolution_id")
    private Resolution resolution;
}
