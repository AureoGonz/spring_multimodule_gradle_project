package lab.triceracode.product.entity.repository;

import lab.triceracode.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RProduct extends JpaRepository<Product, Long> {
}
