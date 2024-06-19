package com.example.sementes1.service;

import com.example.sementes1.domain.Contato;
import com.example.sementes1.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    // Método para adicionar um novo contato
    public Contato addContato(Contato contato) {
        return contatoRepository.save(contato);
    }

    // Método para atualizar um contato existente
    public Contato updateContato(Long id, Contato updatedContato) {
        Optional<Contato> optionalContato = contatoRepository.findById(id);
        if (optionalContato.isPresent()) {
            Contato existingContato = optionalContato.get();
            existingContato.setTelefone1(updatedContato.getTelefone1());
            existingContato.setEmail(updatedContato.getEmail());
            // Atualize outros campos conforme necessário

            return contatoRepository.save(existingContato);
        } else {
            throw new IllegalArgumentException("Contato não encontrado com o ID: " + id);
        }
    }

    // Método para deletar um contato
    public void deleteContato(Long id) {
        if (contatoRepository.existsById(id)) {
            contatoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Contato não encontrado com o ID: " + id);
        }
    }

    // Método para buscar um contato pelo ID
    public Contato getContatoById(Long id) {
        return contatoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado com o ID: " + id));
    }
}
