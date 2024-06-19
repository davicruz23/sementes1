package com.example.sementes1.repository;

import com.example.sementes1.domain.Endereco;
import com.example.sementes1.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Optional<Endereco> findByUsuario(Usuario usuario);
}
