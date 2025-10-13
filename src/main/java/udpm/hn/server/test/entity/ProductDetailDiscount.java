package udpm.hn.server.test.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "product_detail_discount")
public class ProductDetailDiscount extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;
}
