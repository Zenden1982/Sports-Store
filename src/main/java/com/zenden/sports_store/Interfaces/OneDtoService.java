package com.zenden.sports_store.Interfaces;

import org.springframework.data.domain.Page;

public interface OneDtoService<DTO, Filter> extends BaseService{

    public DTO create(DTO entity);

    public DTO read(Long id);

    public Page<DTO> readAll(int page, int size, String sort, Filter filter);

    public DTO update(Long id, DTO entity);

    public void delete(Long id);
    
}
