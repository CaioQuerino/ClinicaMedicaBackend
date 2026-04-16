package com.escola.Senai.ClinicaMedica.controller;

import com.escola.Senai.ClinicaMedica.dto.PlanoDTO;
import com.escola.Senai.ClinicaMedica.service.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/planos")
@CrossOrigin(origins = "*")
public class PlanoController {
    
    @Autowired
    private PlanoService planoService;
    
    @GetMapping
    public ResponseEntity<List<PlanoDTO>> listarTodos() {
        return ResponseEntity.ok(planoService.listarTodos());
    }
    
    @GetMapping("/ativos")
    public ResponseEntity<List<PlanoDTO>> listarAtivos() {
        return ResponseEntity.ok(planoService.listarAtivos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PlanoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planoService.buscarPorId(id));
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<PlanoDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(planoService.buscarPorNome(nome));
    }
    
    @PostMapping
    public ResponseEntity<PlanoDTO> inserir(@Valid @RequestBody PlanoDTO planoDTO) {
        PlanoDTO novoPlano = planoService.inserir(planoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPlano);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PlanoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PlanoDTO planoDTO) {
        return ResponseEntity.ok(planoService.atualizar(id, planoDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        planoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}