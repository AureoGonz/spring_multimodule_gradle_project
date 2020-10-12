package lab.triceracode.warehouse.service;

import lab.triceracode.core.entity.PageResponse;
import lab.triceracode.core.service.CrudService;
import lab.triceracode.warehouse.entity.Stock;
import lab.triceracode.warehouse.entity.dto.StockDTO;

public interface SStock {

    public PageResponse<Stock> getAll(Long warehouse, Integer pageNo, Integer pageSize, String sortBy);
    public Stock get(Long warehouse, Long aLong);
    public Stock create(Long warehouse, StockDTO dto);
    public Stock update(Long warehouse, Long aLong, StockDTO dto);
    public Stock delete(Long warehouse, Long aLong);
}
