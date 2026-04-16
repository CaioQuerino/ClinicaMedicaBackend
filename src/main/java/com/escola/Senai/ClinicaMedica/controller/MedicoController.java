package com.escola.Senai.ClinicaMedica.controller;

import com.escola.Senai.ClinicaMedica.dto.MedicoDTO;
import com.escola.Senai.ClinicaMedica.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {
    
    @Autowired
    private MedicoService medicoService;
    
    @GetMapping
    public ResponseEntity<List<MedicoDTO>> listarTodos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<MedicoDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(medicoService.buscarPorNome(nome));
    }
    
    @PostMapping
    public ResponseEntity<MedicoDTO> inserir(@Valid @RequestBody MedicoDTO medicoDTO) {
        MedicoDTO novoMedico = medicoService.inserir(medicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedico);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MedicoDTO medicoDTO) {
        return ResponseEntity.ok(medicoService.atualizar(id, medicoDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}