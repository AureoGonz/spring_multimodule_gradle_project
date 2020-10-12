package lab.triceracode.warehouse.entity;

import lab.triceracode.warehouse.feign.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long product;
    @Transient
    private Product productRef;
    @Column(nullable = false)
    private Long quantity;
    @Column(nullable = false)
    private Long warehouse;
    private Date deletedAt;

}
