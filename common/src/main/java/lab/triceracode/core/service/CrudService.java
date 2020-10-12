package lab.triceracode.core.service;

import lab.triceracode.core.entity.PageResponse;
import lombok.SneakyThrows;

public interface CrudService<ENTITY, DTO, ID> {
    public PageResponse<ENTITY> getAll(Integer pageNo, Integer pageSize, String sortBy);
    public ENTITY get(ID id);
    public ENTITY create(DTO dto);
    public ENTITY update(ID id, DTO dto);
    public ENTITY delete(ID id);
}
