package com.example.sementes1.dto;

import com.example.sementes1.domain.Produtos;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsuarioProdutoDto {
    // Getters e Setters
    private String nome;
    private List<Produtos> produtos;

}