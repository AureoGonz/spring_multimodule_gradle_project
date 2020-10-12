package lab.triceracode.warehouse.entity.dto;

import lab.triceracode.core.entity.dto.ValidDTO;
import lab.triceracode.core.exception.ConstraintParametersException;

import static lab.triceracode.core.utils.ValidUtils.concatConditions;

import lombok.*;

import javax.validation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseDTO implements ValidDTO {

    @NotEmpty(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre debe ser más que espacios en blanco")
    private String name;
    @Valid
    private List<StockDTO> stocks;


    @SneakyThrows
    @Override
    public void validate() {
        if (areAttributesNulls())
            throw new ConstraintParametersException(Collections.singletonList("No hay datos para el Almacen"));
        if (null != name && name.trim().isEmpty())
            throw new ConstraintParametersException(Collections.singletonList("El nombre debe contener datos"));
        if (null != stocks && !stocks.isEmpty())
            stocks.forEach(e -> {
                if (null != e.getId()) e.validate();
                else {
                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                    Validator validator = factory.getValidator();
                    Set<ConstraintViolation<StockDTO>> violations = validator.validate(e);
                    if (!violations.isEmpty())
                        throw new ConstraintViolationException(new HashSet<>(violations));
                }
            });
    }

    @Override
    public boolean areAttributesNulls() {
        return concatConditions(null == name, null == stocks);
    }
}
