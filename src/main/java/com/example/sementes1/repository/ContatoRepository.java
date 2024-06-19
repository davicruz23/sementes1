package com.example.sementes1.repository;

import com.example.sementes1.domain.Contato;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository extends IGenericRepository<Contato, Number> {

}