package lab.triceracode.product.entity.repository;

import lab.triceracode.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RCategory extends JpaRepository<Category, Long> {
    public Optional<Category> findByName(String name);
}
