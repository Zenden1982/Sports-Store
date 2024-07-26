package com.zenden.sports_store.Interfaces;

import org.springframework.data.domain.Page;

public interface BaseService<T, S> {

    public T create(T entity);

    public T read(Long id);

    public Page<T> readAll(int page, int size, String sort, S filter);

    public T update(Long id, T entity);

    public void delete(Long id);
}
