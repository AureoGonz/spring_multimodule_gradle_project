package lab.triceracode.core.entity.dto;

import lab.triceracode.core.exception.ConstraintParametersException;
import lombok.SneakyThrows;

public interface ValidDTO {

    public void validate() throws ConstraintParametersException;
    public boolean areAttributesNulls();
}
