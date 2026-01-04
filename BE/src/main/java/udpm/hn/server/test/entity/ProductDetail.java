package udpm.hn.server.test.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import udpm.hn.server.test.entity.base.PrimaryEntity;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
@Entity
@Table(name = "product_detail")
public class ProductDetail extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_detail_name")
    private String productDetailName;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "quantity_seril")
    private Integer quantitySeril;

    @Column(name = "price", precision = 15, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "version_id")
    private Version version;

    @ManyToOne
    @JoinColumn(name = "storage_capacity_id")
    private StorageCapacity storageCapacity;
}
