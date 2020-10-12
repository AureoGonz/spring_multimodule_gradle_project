package lab.triceracode.warehouse.feign.product.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Set<Category> categories;
    private Date deletedAt;
}
