package lab.triceracode.warehouse.feign.product.fallback;

import lab.triceracode.warehouse.feign.product.CProduct;
import lab.triceracode.warehouse.feign.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductFallbackFactory implements CProduct {
    @Override
    public ResponseEntity<Product> get(Long id) {
        return ResponseEntity.ok(Product.builder().name("none").id(id).build());
    }
}
