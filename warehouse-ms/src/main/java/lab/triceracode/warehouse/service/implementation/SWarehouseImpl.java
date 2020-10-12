package lab.triceracode.warehouse.service.implementation;

import lab.triceracode.core.entity.PageResponse;
import lab.triceracode.warehouse.entity.Warehouse;
import lab.triceracode.warehouse.entity.dto.WarehouseDTO;
import lab.triceracode.warehouse.entity.repository.RStock;
import lab.triceracode.warehouse.entity.repository.RWarehouse;
import lab.triceracode.warehouse.feign.product.CProduct;
import lab.triceracode.warehouse.service.SStock;
import lab.triceracode.warehouse.service.SWarehouse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SWarehouseImpl implements SWarehouse {

    private final RWarehouse rWarehouse;
    private final SStock sStock;
    private final RStock rStock;
    private final CProduct cProduct;

    @Override
    public PageResponse<Warehouse> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Warehouse> page = rWarehouse.findAll(paging);
        List<Warehouse> table = page.hasContent() ? page.getContent() : new ArrayList<>();
        table.forEach(w -> w.getStocks().forEach(s -> {
            try {
                s.setProductRef(cProduct.get(s.getProduct()).getBody());
            }catch (Exception e) {
                s.setProductRef(null);
            }
        }));
        return PageResponse
                .<Warehouse>builder()
                .page(pageNo)
                .rows(pageSize)
                .order(sortBy)
                .table(table)
                .total(rWarehouse.count())
                .build();
    }

    @Override
    public Warehouse get(Long id) {
        Optional<Warehouse> found = rWarehouse.findById(id);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Almacen con id %d no encontrado", id));
        Warehouse warehouse = found.get();
        warehouse.getStocks().forEach(s ->{
            try {
                s.setProductRef(cProduct.get(s.getProduct()).getBody());
            }catch (Exception e) {
                s.setProductRef(null);
            }
        });
        return warehouse;
    }

    @Override
    public Warehouse create(WarehouseDTO warehouseDTO) {
        Warehouse create = rWarehouse.save(Warehouse.builder().name(warehouseDTO.getName()).build());
        if (null != create.getId() && null != warehouseDTO.getStocks() && warehouseDTO.getStocks().size() > 0)
            create.setStocks(warehouseDTO.getStocks().stream().map(s -> sStock.create(create.getId(), s)
            ).collect(Collectors.toList()));
        return create;
    }

    @SneakyThrows
    @Override
    public Warehouse update(Long id, WarehouseDTO warehouseDTO) {
        warehouseDTO.validate();
        Optional<Warehouse> found = rWarehouse.findById(id);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Almacen con id %d no encontrado", id));
        Warehouse update = found.get();
        if (null != warehouseDTO.getName()) update.setName(warehouseDTO.getName());
        if (null != warehouseDTO.getStocks() && !warehouseDTO.getStocks().isEmpty())
            warehouseDTO.getStocks().forEach(s -> {
                if (null != s.getId()) sStock.update(id, s.getId(), s);
                else sStock.create(id, s);
            });
        rWarehouse.save(update);
        update.setStocks(rStock.findByWarehouse(id));
        return update;
    }

    @Override
    public Warehouse delete(Long id) {
        Optional<Warehouse> found = rWarehouse.findById(id);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Almacen con id %d no encontrado", id));
        Warehouse delete = found.get();
        delete.setDeletedAt(new Date());
        return rWarehouse.save(delete);
    }
}
