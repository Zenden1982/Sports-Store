package com.zenden.sports_store.Interfaces;

import org.springframework.data.domain.Page;

public interface TwoDtoService<ReadDTO, CreateUpdateDTO, Filter> extends BaseService{

    ReadDTO create(CreateUpdateDTO entity);
    ReadDTO read(Long id);
    Page<ReadDTO> readAll(int page, int size, String sort, Filter filter);
    ReadDTO update(Long id, CreateUpdateDTO entity);
    void delete(Long id);
}
