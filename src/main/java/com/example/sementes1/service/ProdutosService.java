package com.example.sementes1.service;

import com.example.sementes1.domain.Produtos;
import com.example.sementes1.domain.Usuario;
import com.example.sementes1.repository.ProdutosRepository;
import com.example.sementes1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutosService {

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Produtos save(Produtos produto, Long usuarioId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            produto.setUsuario(usuarioOptional.get());
            return produtosRepository.save(produto);
        } else {
            throw new RuntimeException("Usuario not found");
        }
    }

    public Produtos update(Long id, Produtos produto) {
        Produtos existingProduto = produtosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto not found"));
        existingProduto.setNome(produto.getNome());
        existingProduto.setTipo(produto.getTipo());
        existingProduto.setQuantidade(produto.getQuantidade());
        return produtosRepository.save(existingProduto);
    }

    public void delete(Long id) {
        produtosRepository.deleteById(id);
    }

    public Produtos findById(Long id) {
        return produtosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto not found"));
    }

    public List<Produtos> findAllByUsuarioId(Long usuarioId) {
        return produtosRepository.findByUsuarioId(usuarioId);
    }

    public ProdutosService(ProdutosRepository repository) {
        this.produtosRepository = repository;
    }

}