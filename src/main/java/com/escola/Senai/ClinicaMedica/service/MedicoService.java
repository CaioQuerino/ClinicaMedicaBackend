package com.escola.Senai.ClinicaMedica.service;

import com.escola.Senai.ClinicaMedica.dto.MedicoDTO;
import com.escola.Senai.ClinicaMedica.model.Medico;
import com.escola.Senai.ClinicaMedica.enums.TypeConselho;
import com.escola.Senai.ClinicaMedica.repository.MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    public List<MedicoDTO> listarTodos() {
        return medicoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public MedicoDTO buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com ID: " + id));
        return converterParaDTO(medico);
    }
    
    public List<MedicoDTO> buscarPorNome(String nome) {
        return medicoRepository.findByNomeContaining(nome).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public MedicoDTO inserir(MedicoDTO medicoDTO) {
        Medico medico = converterParaEntidade(medicoDTO);
        medico = medicoRepository.save(medico);
        return converterParaDTO(medico);
    }
    
    public MedicoDTO atualizar(Long id, MedicoDTO medicoDTO) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com ID: " + id));
        
        medico.setNome(medicoDTO.getNome());
        medico.setNumeroRegistro(medicoDTO.getNumeroRegistro());
        medico.setEspecialidade(medicoDTO.getEspecialidade());
        medico.setTelefone(medicoDTO.getTelefone());
        medico.setEmail(medicoDTO.getEmail());
        
        medico = medicoRepository.save(medico);
        return converterParaDTO(medico);
    }
    
    public void deletar(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Médico não encontrado com ID: " + id);
        }
        medicoRepository.deleteById(id);
    }
    
    private MedicoDTO converterParaDTO(Medico medico) {
        MedicoDTO dto = new MedicoDTO();
        dto.setId(medico.getId());
        dto.setNome(medico.getNome());
        dto.setNumeroRegistro(medico.getNumeroRegistro());
        dto.setEspecialidade(medico.getEspecialidade());
        dto.setTelefone(medico.getTelefone());
        dto.setEmail(medico.getEmail());
        return dto;
    }
    
    private Medico converterParaEntidade(MedicoDTO dto) {
        Medico medico = new Medico();
        medico.setNome(dto.getNome());
        medico.setNumeroRegistro(dto.getNumeroRegistro());
        medico.setConselho(TypeConselho.CRM);
        medico.setEspecialidade(dto.getEspecialidade());
        medico.setTelefone(dto.getTelefone());
        medico.setEmail(dto.getEmail());
        return medico;
    }
}