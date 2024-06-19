package com.example.sementes1.service;

import com.example.sementes1.domain.AbstractEntity;

import java.util.List;

public interface IGenericService<E extends AbstractEntity> {

    E create(E e);
    E update(E e, Long id);
    void delete(Long id);
    List<E> list();
    E getById(Long id);
}
