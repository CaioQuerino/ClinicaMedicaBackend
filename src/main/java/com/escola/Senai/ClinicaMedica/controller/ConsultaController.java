package com.escola.Senai.ClinicaMedica.controller;

import com.escola.Senai.ClinicaMedica.dto.ConsultaDTO;
import com.escola.Senai.ClinicaMedica.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {
    
    @Autowired
    private ConsultaService consultaService;
    
    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listarTodos() {
        return ResponseEntity.ok(consultaService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<ConsultaDTO> inserir(@Valid @RequestBody ConsultaDTO consultaDTO) {
        ConsultaDTO novaConsulta = consultaService.inserir(consultaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConsulta);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaDTO consultaDTO) {
        return ResponseEntity.ok(consultaService.atualizar(id, consultaDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}