package lab.triceracode.warehouse.feign.product.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
    private Long id;
    private String name;
}
