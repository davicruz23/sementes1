package com.example.sementes1.controller;

import com.example.sementes1.domain.Contato;
import com.example.sementes1.service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contato")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @PostMapping
    public ResponseEntity<Contato> addContato(@RequestBody Contato contato) {
        Contato novoContato = contatoService.addContato(contato);
        return ResponseEntity.ok(novoContato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> updateContato(@PathVariable Long id, @RequestBody Contato contato) {
        Contato updatedContato = contatoService.updateContato(id, contato);
        return ResponseEntity.ok(updatedContato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        contatoService.deleteContato(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contato> getContatoById(@PathVariable Long id) {
        Contato contato = contatoService.getContatoById(id);
        return ResponseEntity.ok(contato);
    }
}
