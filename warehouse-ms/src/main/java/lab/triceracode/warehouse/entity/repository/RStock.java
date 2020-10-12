package lab.triceracode.warehouse.entity.repository;

import lab.triceracode.warehouse.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RStock extends JpaRepository<Stock, Long> {
    public Page<Stock> findByWarehouse(Pageable pageable, Long warehouse);
    public List<Stock> findByWarehouse(Long warehouse);
    public Optional<Stock> findByIdAndWarehouse(Long id, Long warehouse);
    public Long countByWarehouse(Long warehouse);
}
