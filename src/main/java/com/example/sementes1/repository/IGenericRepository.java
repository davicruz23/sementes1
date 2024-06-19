package com.example.sementes1.repository;

import com.example.sementes1.domain.AbstractEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenericRepository<E extends AbstractEntity, L extends Number> extends ListCrudRepository<E, Long> {
}
