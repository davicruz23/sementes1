package com.example.sementes1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;


import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Contato extends AbstractEntity implements Serializable {

    String telefone1;
    String email;

    //1-1
    @OneToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Contato contato) {
            this.telefone1 = contato.telefone1;
            this.email = contato.email;

        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Telefone em branco")
        String telefone1;
        @NotBlank(message = "Email em branco")
        String email;


        public static Contato convertToEntity(Contato.DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Contato.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String telefone1;
        String email;

        public static Contato.DtoResponse convertToDto(Contato c, ModelMapper mapper) {
            return mapper.map(c, Contato.DtoResponse.class);
        }
    }

}
