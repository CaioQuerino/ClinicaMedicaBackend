package com.escola.Senai.ClinicaMedica.service;

import com.escola.Senai.ClinicaMedica.dto.PacienteDTO;
import com.escola.Senai.ClinicaMedica.model.Paciente;
import com.escola.Senai.ClinicaMedica.model.Plano;
import com.escola.Senai.ClinicaMedica.repository.PacienteRepository;
import com.escola.Senai.ClinicaMedica.repository.PlanoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private PlanoRepository planoRepository;
    
    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public PacienteDTO buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com ID: " + id));
        return converterParaDTO(paciente);
    }
    
    public List<PacienteDTO> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContaining(nome).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public PacienteDTO inserir(PacienteDTO pacienteDTO) {
        Paciente paciente = converterParaEntidade(pacienteDTO);
        
        if (pacienteDTO.getId()!= null) {
            Plano plano = planoRepository.findById(pacienteDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado"));
            paciente.setPlano(plano);
        }
        
        paciente = pacienteRepository.save(paciente);
        return converterParaDTO(paciente);
    }
    
    public PacienteDTO atualizar(Long id, PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com ID: " + id));
        
        paciente.setNome(pacienteDTO.getNome());
        paciente.setCpf(pacienteDTO.getCpf());
        paciente.setDataNascimento(pacienteDTO.getDataNascimento());
        paciente.setTelefone(pacienteDTO.getTelefone());
        paciente.setEndereco(pacienteDTO.getEndereco());
        
        if (pacienteDTO.getId() != null) {
            Plano plano = planoRepository.findById(pacienteDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado"));
            paciente.setPlano(plano);
        } else {
            paciente.setPlano(null);
        }
        
        paciente = pacienteRepository.save(paciente);
        return converterParaDTO(paciente);
    }
    
    public void deletar(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Paciente não encontrado com ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }
    
    private PacienteDTO converterParaDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setCpf(paciente.getCpf());
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setTelefone(paciente.getTelefone());
        dto.setEndereco(paciente.getEndereco());
        
        if (paciente.getPlano() != null) {
            dto.setId(paciente.getPlano().getId());
            dto.setNome(paciente.getPlano().getNome());
        }
        
        return dto;
    }
    
    private Paciente converterParaEntidade(PacienteDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setTelefone(dto.getTelefone());
        paciente.setEndereco(dto.getEndereco());
        return paciente;
    }
}