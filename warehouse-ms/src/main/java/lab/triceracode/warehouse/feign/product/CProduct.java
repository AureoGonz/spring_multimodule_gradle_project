package lab.triceracode.warehouse.feign.product;

import lab.triceracode.warehouse.feign.product.fallback.ProductFallbackFactory;
import lab.triceracode.warehouse.feign.product.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-ms", fallback = ProductFallbackFactory.class)
public interface CProduct {

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable("id") Long id);
}
