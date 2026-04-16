package com.escola.Senai.ClinicaMedica.controller;

import com.escola.Senai.ClinicaMedica.dto.PsicologoDTO;
import com.escola.Senai.ClinicaMedica.service.PsicologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/psicologos")
@CrossOrigin(origins = "*")
public class PsicologoController {
    
    @Autowired
    private PsicologoService psicologoService;
    
    @GetMapping
    public ResponseEntity<List<PsicologoDTO>> listarTodos() {
        return ResponseEntity.ok(psicologoService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PsicologoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(psicologoService.buscarPorId(id));
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<PsicologoDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(psicologoService.buscarPorNome(nome));
    }
    
    @PostMapping
    public ResponseEntity<PsicologoDTO> inserir(@Valid @RequestBody PsicologoDTO psicologoDTO) {
        PsicologoDTO novoPsicologo = psicologoService.inserir(psicologoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPsicologo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PsicologoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PsicologoDTO psicologoDTO) {
        return ResponseEntity.ok(psicologoService.atualizar(id, psicologoDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        psicologoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}