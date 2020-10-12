package lab.triceracode.warehouse.entity.dto;

import lab.triceracode.core.entity.dto.ValidDTO;
import lab.triceracode.core.exception.ConstraintParametersException;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import java.util.Collections;

import static lab.triceracode.core.utils.ValidUtils.concatConditions;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDTO implements ValidDTO {

    private Long id;
    @NotNull(message = "El producto es obligatorio")
    private Long product;
    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "La cantidad en stock no puede ser negativa")
    private Long quantity;

    @SneakyThrows
    @Override
    public void validate() {
        if (areAttributesNulls())
            throw new ConstraintParametersException(Collections.singletonList("No hay datos para el Stock"));
        if (null==id)
            throw new ConstraintParametersException(Collections.singletonList("Se necesita un id para actualzar"));
        if (null != quantity && quantity < 0)
            throw new ConstraintParametersException(Collections.singletonList("La cantidad en stock no puede ser negativa"));
    }

    @Override
    public boolean areAttributesNulls() {
        return concatConditions(null!=id,null == product, null == quantity);
    }
}
