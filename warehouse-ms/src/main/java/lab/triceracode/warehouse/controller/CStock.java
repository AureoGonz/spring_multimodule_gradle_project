package lab.triceracode.warehouse.controller;

import lab.triceracode.core.entity.PageResponse;
import lab.triceracode.warehouse.entity.Stock;
import lab.triceracode.warehouse.entity.dto.StockDTO;
import lab.triceracode.warehouse.service.SStock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CStock {

    private final SStock service;

    @GetMapping("/{warehouse}/stocks")
    public ResponseEntity<PageResponse<Stock>> getAll(@PathVariable("warehouse") Long warehouse, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.accepted().body(service.getAll(warehouse,pageNo, pageSize, sortBy));
    }

    @GetMapping("/{warehouse}/stocks/{id}")
    public ResponseEntity<Stock> get(@PathVariable("warehouse") Long warehouse,@PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(service.get(warehouse,id));
    }

    @PostMapping("/{warehouse}/stocks")
    public ResponseEntity<Stock> create(@PathVariable("warehouse") Long warehouse,@Valid @RequestBody StockDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(warehouse,dto));
    }

    @PutMapping("/{warehouse}/stocks/{id}")
    public ResponseEntity<Stock> update(@PathVariable("warehouse") Long warehouse,@PathVariable("id") Long id, @RequestBody StockDTO dto) {
        return ResponseEntity.ok(service.update(warehouse,id, dto));
    }

    @DeleteMapping("/{warehouse}/stocks/{id}")
    public ResponseEntity<Stock> delete(@PathVariable("warehouse") Long warehouse,@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.delete(warehouse,id));
    }
}
