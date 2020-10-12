package lab.triceracode.warehouse.entity.repository;

import lab.triceracode.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RWarehouse extends JpaRepository<Warehouse, Long> {
}
