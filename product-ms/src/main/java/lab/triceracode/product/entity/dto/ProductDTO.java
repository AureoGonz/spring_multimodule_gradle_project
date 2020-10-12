package lab.triceracode.product.entity.dto;

import lab.triceracode.core.entity.dto.ValidDTO;
import lab.triceracode.core.exception.ConstraintParametersException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Collections;
import java.util.List;

import static lab.triceracode.core.utils.ValidUtils.concatConditions;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO implements ValidDTO {

    @NotEmpty(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre debe ser más que espacios en blanco")
    private String name;
    private String description;
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio tiene que ser mayor que 0")
    private Double price;

    @NotNull(message = "Las categorias son obligatorias")
    @Size(min = 1, message = "Almenos tiene que haber una categoria")
    private List<CategoryDTO> categories;

    @Override
    public void validate() throws ConstraintParametersException {
        if (areAttributesNulls())
            throw new ConstraintParametersException(Collections.singletonList("No hay datos para el producto"));
        ConstraintParametersException exception = new ConstraintParametersException();
        if (null != name && name.trim().isEmpty()) exception.add("El nombre contener datos");
        if (null != price && price.isNaN()) exception.add("El precio no es un numero valido");
        if (null != price && price < 0) exception.add("El precio no puede ser negativo");
        if (exception.canThrows()) throw exception;
    }

    @Override
    public boolean areAttributesNulls() {
        return concatConditions(null==name, null==description, null==price, (null==categories || categories.isEmpty()));
    }
}
