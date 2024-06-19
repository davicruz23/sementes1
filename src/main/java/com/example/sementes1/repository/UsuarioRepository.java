package com.example.sementes1.repository;

import com.example.sementes1.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends IGenericRepository<Usuario, Number>, JpaRepository<Usuario, Long> {

    UserDetails findByUsuario(String usuario);
}
