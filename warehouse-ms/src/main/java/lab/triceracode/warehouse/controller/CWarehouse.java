package lab.triceracode.warehouse.controller;

import lab.triceracode.core.controller.CrudController;
import lab.triceracode.core.entity.PageResponse;
import lab.triceracode.warehouse.entity.Warehouse;
import lab.triceracode.warehouse.entity.dto.WarehouseDTO;
import lab.triceracode.warehouse.service.SWarehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CWarehouse implements CrudController<Warehouse, WarehouseDTO,Long> {
    private final SWarehouse service;

    @GetMapping
    @Override
    public ResponseEntity<PageResponse<Warehouse>> getAll(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.accepted().body(service.getAll(pageNo, pageSize, sortBy));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Warehouse> get(@PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(service.get(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<Warehouse> create(@Valid @RequestBody WarehouseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Warehouse> update(@PathVariable("id") Long id, @RequestBody WarehouseDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Warehouse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
