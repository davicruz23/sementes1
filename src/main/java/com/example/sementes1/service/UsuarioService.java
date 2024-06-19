package com.example.sementes1.service;

import com.example.sementes1.domain.Contato;
import com.example.sementes1.domain.Endereco;
import com.example.sementes1.domain.Usuario;
import com.example.sementes1.repository.EnderecoRepository;
import com.example.sementes1.repository.UsuarioRepository;
import com.example.sementes1.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends GenericService<Usuario, UsuarioRepository> implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final ContatoRepository contatoRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EnderecoRepository enderecoRepository, ContatoRepository contatoRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.contatoRepository = contatoRepository;
    }

    public Usuario addEnderecoToUsuario(Long usuarioId, Endereco endereco) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + usuarioId));

        endereco.setUsuario(usuario); // Associa o endereço ao usuário
        usuario.setEndereco(endereco); // Associa o endereço ao usuário

        enderecoRepository.save(endereco); // Salva o endereço
        usuarioRepository.save(usuario); // Salva o usuário

        return usuario;
    }


    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = (Usuario) usuarioRepository.findByUsuario(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o nome de usuário: " + username);
        }
        return usuario;
    }
    public Usuario addContatoToUsuario(Long usuarioId, Contato contato) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + usuarioId));

        contato.setUsuario(usuario); // Associa o endereço ao usuário
        usuario.setContato(contato); // Associa o endereço ao usuário

        contatoRepository.save(contato); // Salva o endereço
        usuarioRepository.save(usuario); // Salva o usuário

        return usuario;
    }
}
