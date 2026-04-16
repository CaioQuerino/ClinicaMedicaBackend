package com.escola.Senai.ClinicaMedica.service;

import com.escola.Senai.ClinicaMedica.dto.ConsultaDTO;
import com.escola.Senai.ClinicaMedica.model.Consulta;
import com.escola.Senai.ClinicaMedica.model.Profissional;
import com.escola.Senai.ClinicaMedica.model.Paciente;
import com.escola.Senai.ClinicaMedica.repository.ConsultaRepository;
import com.escola.Senai.ClinicaMedica.repository.ProfissionalRepository;
import com.escola.Senai.ClinicaMedica.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {
    
    @Autowired
    private ConsultaRepository consultaRepository;
    
    @Autowired
    private ProfissionalRepository profissionalRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    public List<ConsultaDTO> listarTodos() {
        return consultaRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public ConsultaDTO buscarPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + id));
        return converterParaDTO(consulta);
    }
    
    public ConsultaDTO inserir(ConsultaDTO consultaDTO) {
        Profissional profissional = profissionalRepository.findById(consultaDTO.getProfissionalId())
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado"));
        
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
        
        Consulta consulta = new Consulta();
        consulta.setProfissional(profissional);
        consulta.setPaciente(paciente);
        consulta.setDataHora(consultaDTO.getDataHora());
        consulta.setObservacao(consultaDTO.getObservacao());
        consulta.setStatus(consultaDTO.getStatus() != null ? consultaDTO.getStatus() : "AGENDADA");
        
        consulta = consultaRepository.save(consulta);
        return converterParaDTO(consulta);
    }
    
    public ConsultaDTO atualizar(Long id, ConsultaDTO consultaDTO) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + id));
        
        if (consultaDTO.getProfissionalId() != null) {
            Profissional profissional = profissionalRepository.findById(consultaDTO.getProfissionalId())
                    .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado"));
            consulta.setProfissional(profissional);
        }
        
        if (consultaDTO.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
            consulta.setPaciente(paciente);
        }
        
        if (consultaDTO.getDataHora() != null) {
            consulta.setDataHora(consultaDTO.getDataHora());
        }
        
        if (consultaDTO.getObservacao() != null) {
            consulta.setObservacao(consultaDTO.getObservacao());
        }
        
        if (consultaDTO.getStatus() != null) {
            consulta.setStatus(consultaDTO.getStatus());
        }
        
        consulta = consultaRepository.save(consulta);
        return converterParaDTO(consulta);
    }
    
    public void deletar(Long id) {
        if (!consultaRepository.existsById(id)) {
            throw new EntityNotFoundException("Consulta não encontrada com ID: " + id);
        }
        consultaRepository.deleteById(id);
    }
    
    private ConsultaDTO converterParaDTO(Consulta consulta) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setId(consulta.getId());
        dto.setProfissionalId(consulta.getProfissional().getId());
        dto.setNomeProfissional(consulta.getProfissional().getNome());
        dto.setPacienteId(consulta.getPaciente().getId());
        dto.setNomePaciente(consulta.getPaciente().getNome());
        dto.setDataHora(consulta.getDataHora());
        dto.setObservacao(consulta.getObservacao());
        dto.setStatus(consulta.getStatus());
        return dto;
    }
}