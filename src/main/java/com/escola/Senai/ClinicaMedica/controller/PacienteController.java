package com.escola.Senai.ClinicaMedica.controller;

import com.escola.Senai.ClinicaMedica.dto.PacienteDTO;
import com.escola.Senai.ClinicaMedica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;
    
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarTodos() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<PacienteDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(pacienteService.buscarPorNome(nome));
    }
    
    @PostMapping
    public ResponseEntity<PacienteDTO> inserir(@Valid @RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO novoPaciente = pacienteService.inserir(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(pacienteService.atualizar(id, pacienteDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}