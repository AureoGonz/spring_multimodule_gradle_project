package lab.triceracode.warehouse.service.implementation;

import lab.triceracode.core.entity.PageResponse;
import lab.triceracode.warehouse.entity.Stock;
import lab.triceracode.warehouse.entity.Warehouse;
import lab.triceracode.warehouse.entity.dto.StockDTO;
import lab.triceracode.warehouse.entity.repository.RStock;
import lab.triceracode.warehouse.entity.repository.RWarehouse;
import lab.triceracode.warehouse.feign.product.CProduct;
import lab.triceracode.warehouse.feign.product.model.Product;
import lab.triceracode.warehouse.service.SStock;
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

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SStockImpl implements SStock {

    private final RStock rStock;
    private final RWarehouse rWarehouse;
    private final CProduct cProduct;

    @Override
    public PageResponse<Stock> getAll(Long warehouse, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Stock> page = rStock.findByWarehouse(paging, warehouse);
        List<Stock> table = page.hasContent() ? page.getContent() : new ArrayList<>();
        if (!table.isEmpty())
            table.forEach(s -> {
                try {
                    s.setProductRef(cProduct.get(s.getProduct()).getBody());
                } catch (Exception e) {
                    s.setProductRef(null);
                }
            });
        return PageResponse
                .<Stock>builder()
                .page(pageNo)
                .rows(pageSize)
                .order(sortBy)
                .table(table)
                .total(rStock.countByWarehouse(warehouse))
                .build();
    }

    @Override
    public Stock get(Long warehouse, Long id) {
        Optional<Stock> found = rStock.findByIdAndWarehouse(id, warehouse);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Stock con id %d no encontrado", id));
        Stock stock = found.get();
        {
            try {
                stock.setProductRef(cProduct.get(stock.getProduct()).getBody());
            } catch (Exception e) {
                stock.setProductRef(null);
            }
        }
        return stock;
    }

    @Override
    public Stock create(Long warehouse, StockDTO stockDTO) {
        Product productRef;
        try {
            productRef = cProduct.get(stockDTO.getProduct()).getBody();
        } catch (Exception e) {
            productRef = null;
        }
        if (null == productRef)
            throw new EntityNotFoundException(String.format("El producto con id %d no encontrado", stockDTO.getProduct()));
        return rStock.save(Stock.builder()
                .product(stockDTO.getProduct())
                .productRef(productRef)
                .quantity(stockDTO.getQuantity())
                .warehouse(getWarehouse(warehouse))
                .build());
    }

    @SneakyThrows
    @Override
    public Stock update(Long warehouse, Long id, StockDTO stockDTO) {
        stockDTO.validate();
        Optional<Stock> found = rStock.findByIdAndWarehouse(id, warehouse);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Stock con id %d no encontrado", id));
        Stock update = found.get();
        if (null != stockDTO.getProduct()) {
            Product productRef;
            try {
                productRef = cProduct.get(stockDTO.getProduct()).getBody();
            } catch (Exception e) {
                productRef = null;
            }
            if (null == productRef)
                throw new EntityNotFoundException(String.format("El producto con id %d no encontrado", stockDTO.getProduct()));
            update.setProduct(stockDTO.getProduct());
            update.setProductRef(productRef);
        }
        if (null != stockDTO.getQuantity()) update.setQuantity(stockDTO.getQuantity());
        if (!update.getWarehouse().equals(warehouse)) update.setWarehouse(getWarehouse(warehouse));
        return rStock.save(update);
    }

    @Override
    public Stock delete(Long warehouse, Long id) {
        Optional<Stock> found = rStock.findByIdAndWarehouse(id, warehouse);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Producto con id %d no encontrado", id));
        Stock delete = found.get();
        delete.setDeletedAt(new Date());
        return rStock.save(delete);
    }

    private Long getWarehouse(Long id) {
        Optional<Warehouse> found = rWarehouse.findById(id);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Almacen con id %d no encontrado", id));
        return id;
    }
}
