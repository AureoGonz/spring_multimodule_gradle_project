package lab.triceracode.product.service;

import lab.triceracode.core.service.CrudService;
import lab.triceracode.product.entity.Product;
import lab.triceracode.product.entity.dto.ProductDTO;

public interface SProduct extends CrudService<Product, ProductDTO, Long> {
}
