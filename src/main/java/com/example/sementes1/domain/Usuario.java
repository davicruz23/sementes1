package com.example.sementes1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.sementes1.controller.UsuarioController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Usuario extends AbstractEntity implements UserDetails {

    private String nomecompleto;
    @Column(unique = true)
    private String usuario;
    private String senha;
    private Role role;

    // 1-1
    @JsonIgnore
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Endereco endereco;

    // 1-1
    @JsonIgnore
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Contato contato;

    // 1-N
    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produtos> produtos;

    public Usuario(String usuario, String senha, Role role) {
        this.usuario = usuario;
        this.senha = senha;
        this.role = role;
    }

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Usuario usuario) {
            this.nomecompleto = usuario.nomecompleto;
            this.senha = usuario.senha;
        }
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.USER)
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return this.senha;
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return this.usuario;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", nomecompleto='" + nomecompleto + '\'' +
                ", usuario='" + usuario + '\'' +
                ", role=" + role +
                '}';
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Usuário com nome em branco")
        private String nomecompleto;
        @NotBlank(message = "Usuario em branco")
        private String usuario;
        @NotBlank(message = "Senha em branco")
        private String senha;
        Endereco endereco;
        Contato contato;

        public static Usuario convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Usuario.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        private String nomecompleto;
        private String usuario;
        private String senha;
        Endereco endereco;
        Contato contato;

        public static DtoResponse convertToDto(Usuario p, ModelMapper mapper) {
            return mapper.map(p, DtoResponse.class);
        }

        @JsonIgnore
        public void generateLinks(Long id) {
            add(linkTo(UsuarioController.class).slash(id).withSelfRel());
            add(linkTo(UsuarioController.class).withRel("pessoas"));
            add(linkTo(UsuarioController.class).slash(id).withRel("delete"));
        }
    }
}
