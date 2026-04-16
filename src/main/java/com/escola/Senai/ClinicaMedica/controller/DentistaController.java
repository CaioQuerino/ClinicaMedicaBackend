package com.escola.Senai.ClinicaMedica.controller;

import com.escola.Senai.ClinicaMedica.dto.DentistaDTO;
import com.escola.Senai.ClinicaMedica.service.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/dentistas")
@CrossOrigin(origins = "*")
public class DentistaController {
    
    @Autowired
    private DentistaService dentistaService;
    
    @GetMapping
    public ResponseEntity<List<DentistaDTO>> listarTodos() {
        return ResponseEntity.ok(dentistaService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DentistaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(dentistaService.buscarPorId(id));
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<DentistaDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(dentistaService.buscarPorNome(nome));
    }
    
    @PostMapping
    public ResponseEntity<DentistaDTO> inserir(@Valid @RequestBody DentistaDTO dentistaDTO) {
        DentistaDTO novoDentista = dentistaService.inserir(dentistaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDentista);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DentistaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody DentistaDTO dentistaDTO) {
        return ResponseEntity.ok(dentistaService.atualizar(id, dentistaDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dentistaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}