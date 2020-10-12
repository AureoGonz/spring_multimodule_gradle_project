package lab.triceracode.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PageResponse<ENTITY> {
    private Integer page;
    private Integer rows;
    private Long total;
    private List<ENTITY> table;
    private String order;
}
