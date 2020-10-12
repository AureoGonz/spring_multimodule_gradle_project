package lab.triceracode.product.service.implementation;

import lab.triceracode.core.entity.PageResponse;
import lab.triceracode.core.exception.ConstraintParametersException;
import lab.triceracode.product.entity.Category;
import lab.triceracode.product.entity.Product;
import lab.triceracode.product.entity.dto.CategoryDTO;
import lab.triceracode.product.entity.dto.ProductDTO;
import lab.triceracode.product.entity.repository.RCategory;
import lab.triceracode.product.entity.repository.RProduct;
import lab.triceracode.product.service.SProduct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SProductImpl implements SProduct {

    private final RProduct rProduct;
    private final RCategory rCategory;

    @Override
    public PageResponse<Product> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Product> page = rProduct.findAll(paging);
        List<Product> table = page.hasContent() ? page.getContent() : new ArrayList<>();
        return PageResponse
                .<Product>builder()
                .page(pageNo)
                .rows(pageSize)
                .order(sortBy)
                .table(table)
                .total(rProduct.count())
                .build();
    }

    @Override
    public Product get(Long id) {
        Optional<Product> found = rProduct.findById(id);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Producto con id %d no encontrado", id));
        return found.get();
    }

    @SneakyThrows
    @Override
    public Product create(ProductDTO productDTO) {
        Set<Category> categories = new HashSet<>();
        if (null != productDTO.getCategories())
            for (CategoryDTO category : productDTO.getCategories()) {
                if (!category.areAttributesNulls()) {
                    Optional<Category> found = Optional.empty();
                    if (null != category.getId())
                        found = rCategory.findById(category.getId());
                    else if (null != category.getName())
                        found = rCategory.findByName(category.getName());
                    if (!found.isPresent())
                        throw new EntityNotFoundException("Categoria con " + (
                                null != category.getId() ?
                                        String.format("id %d no encontrado", category.getId())
                                        : String.format(" nombre %s no encontrado", category.getName())));
                    categories.add(found.get());
                }
            }
        if (categories.isEmpty())
            throw new ConstraintParametersException(Collections.singletonList("No hay datos para las categorias"));
        return rProduct.save(Product
                .builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .categories(categories)
                .build());
    }

    @SneakyThrows
    @Override
    public Product update(Long id, ProductDTO productDTO) {
        productDTO.validate();
        Optional<Product> found = rProduct.findById(id);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Producto con id %d no encontrado", id));
        Product update = found.get();
        if (null != productDTO.getName()) update.setName(productDTO.getName());
        if (null != productDTO.getDescription()) update.setDescription(productDTO.getDescription());
        if (null != productDTO.getPrice()) update.setPrice(productDTO.getPrice());
        if (null != productDTO.getCategories() && !productDTO.getCategories().isEmpty()) {
            Set<Category> categories = new HashSet<>();
            for (CategoryDTO category : productDTO.getCategories()) {
                if (!category.areAttributesNulls()) {
                    Optional<Category> cFound = Optional.empty();
                    if (null != category.getId())
                        cFound = rCategory.findById(category.getId());
                    else if (null != category.getName())
                        cFound = rCategory.findByName(category.getName());
                    if (!cFound.isPresent())
                        throw new EntityNotFoundException("Categoria con " + (
                                null != category.getId() ?
                                        String.format("id %d no encontrado", category.getId())
                                        : String.format(" nombre %s no encontrado", category.getName())));
                    categories.add(cFound.get());
                }
            }
            update.setCategories(categories);
        }
        return rProduct.save(update);
    }

    @Override
    public Product delete(Long id) {
        Optional<Product> found = rProduct.findById(id);
        if (!found.isPresent())
            throw new EntityNotFoundException(String.format("Producto con id %d no encontrado", id));
        Product delete = found.get();
        delete.setDeletedAt(new Date());
        return rProduct.save(delete);
    }
}
