package lab.triceracode.product.entity.dto;

import lab.triceracode.core.entity.dto.ValidDTO;
import lab.triceracode.core.exception.ConstraintParametersException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lab.triceracode.core.utils.ValidUtils.concatConditions;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO implements ValidDTO {

    private Long id;
    private String name;

    @Override
    public void validate() throws ConstraintParametersException {
    }

    @Override
    public boolean areAttributesNulls() {
        return concatConditions(id == null, name == null);
    }
}
