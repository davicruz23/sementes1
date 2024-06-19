package com.example.sementes1.controller;

import com.example.sementes1.domain.AuthDto;
import com.example.sementes1.domain.LoginResponseDTO;
import com.example.sementes1.domain.RegisterDTO;
import com.example.sementes1.domain.Usuario;
import com.example.sementes1.infra.security.TokenService;
import com.example.sementes1.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDto data) {
        try {
            System.out.println("Received login request for user: " + data.usuario());

            // Verificação se o usuário existe
            Usuario usuario = (Usuario) repository.findByUsuario(data.usuario());
            if (usuario == null) {
                System.out.println("User not found: " + data.usuario());
                return ResponseEntity.status(401).body(new LoginResponseDTO("Invalid credentials", null));
            }

            // Verificação de senha manualmente
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(data.senha(), usuario.getSenha())) {
                System.out.println("Invalid password for user: " + data.usuario());
                return ResponseEntity.status(401).body(new LoginResponseDTO("Invalid credentials", null));
            }

            // Geração do token
            System.out.println("Generating token for user: " + data.usuario());
            String token = tokenService.generateToken(usuario);

            // Retornando a resposta com token e ID do usuário
            return ResponseEntity.ok(new LoginResponseDTO(token, usuario.getId()));
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
            return ResponseEntity.status(500).body(new LoginResponseDTO("An unexpected error occurred", null));
        }
    }





    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid RegisterDTO data) {
        System.out.println("Received registration request for user: " + data.usuario());

        Map<String, String> response = new HashMap<>();

        // Verifique se o nome de usuário já existe
        if (this.repository.findByUsuario(data.usuario()) != null) {
            response.put("error", "Usuário já está em uso");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }

        // Verifique se o CPF já existe
        if (this.repository.findByUsuario(data.usuario()) != null) {
            response.put("error", "CPF já está em uso");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }

        // Criptografa a senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.usuario(), encryptedPassword, data.role());

        // Adicione os campos adicionais ao novo usuário
        newUser.setNomecompleto(data.nomecompleto());

        this.repository.save(newUser);

        System.out.println("User registered successfully: " + data.usuario());

        return ResponseEntity.ok().build();
    }

}