package lab.triceracode.product.controller;

import lab.triceracode.core.controller.CrudController;
import lab.triceracode.core.entity.PageResponse;
import lab.triceracode.product.entity.Product;
import lab.triceracode.product.entity.dto.ProductDTO;
import lab.triceracode.product.service.SProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CProduct implements CrudController<Product, ProductDTO, Long> {

    private final SProduct service;

    @GetMapping
    @Override
    public ResponseEntity<PageResponse<Product>> getAll(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(service.getAll(pageNo, pageSize, sortBy));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Product> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody ProductDTO dto) {
        return ResponseEntity.accepted().body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Product> delete(@PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(service.delete(id));
    }
}
