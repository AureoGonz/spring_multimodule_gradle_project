package lab.triceracode.core.controller;

import lab.triceracode.core.entity.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CrudController<ENTITY, DTO, ID> {

    public ResponseEntity<PageResponse<ENTITY>> getAll(Integer pageNo, Integer pageSize, String sortBy);
    public ResponseEntity<ENTITY> get(ID id);
    public ResponseEntity<ENTITY> create(DTO dto);
    public ResponseEntity<ENTITY> update(ID id, DTO dto);
    public ResponseEntity<ENTITY> delete(ID id);

}
