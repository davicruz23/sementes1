package com.example.sementes1.service;

import com.example.sementes1.domain.Endereco;
import com.example.sementes1.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Método para adicionar um novo endereço
    public Endereco addEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // Método para atualizar um endereço existente
    public Endereco updateEndereco(Long id, Endereco updatedEndereco) {
        Optional<Endereco> optionalEndereco = enderecoRepository.findById(id);
        if (optionalEndereco.isPresent()) {
            Endereco existingEndereco = optionalEndereco.get();
            existingEndereco.setRua(updatedEndereco.getRua());
            existingEndereco.setCidade(updatedEndereco.getCidade());
            existingEndereco.setEstado(updatedEndereco.getEstado());
            existingEndereco.setCep(updatedEndereco.getCep());
            // Atualize outros campos conforme necessário

            return enderecoRepository.save(existingEndereco);
        } else {
            throw new IllegalArgumentException("Endereço não encontrado com o ID: " + id);
        }
    }

    // Método para deletar um endereço
    public void deleteEndereco(Long id) {
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Endereço não encontrado com o ID: " + id);
        }
    }

    // Método para buscar um endereço pelo ID
    public Endereco getEnderecoById(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado com o ID: " + id));
    }
}
