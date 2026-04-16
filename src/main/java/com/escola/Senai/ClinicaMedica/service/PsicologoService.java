package com.escola.Senai.ClinicaMedica.service;

import com.escola.Senai.ClinicaMedica.dto.PsicologoDTO;
import com.escola.Senai.ClinicaMedica.model.Psicologo;
import com.escola.Senai.ClinicaMedica.enums.TypeConselho;
import com.escola.Senai.ClinicaMedica.repository.PsicologoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PsicologoService {
    
    @Autowired
    private PsicologoRepository psicologoRepository;
    
    public List<PsicologoDTO> listarTodos() {
        return psicologoRepository.findAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }
    
    public PsicologoDTO buscarPorId(Long id) {
        Psicologo psicologo = psicologoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Psicologo não encontrado com ID: " + id));
        return converterParaDTO(psicologo);
    }
    
    public List<PsicologoDTO> buscarPorNome(String nome) {
        return psicologoRepository.findByNomeContaining(nome).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public PsicologoDTO inserir(PsicologoDTO psicologoDTO) {
        Psicologo psicologo = converterParaEntidade(psicologoDTO);
        psicologo = psicologoRepository.save(psicologo);
        return converterParaDTO(psicologo);
    }
    
    public PsicologoDTO atualizar(Long id, PsicologoDTO psicologoDTO) {
        Psicologo psicologo = psicologoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Psicologo não encontrado com ID: " + id));
        
        psicologo.setNome(psicologoDTO.getNome());
        psicologo.setNumeroRegistro(psicologoDTO.getNumeroRegistro());
        psicologo.setEspecialidade(psicologoDTO.getEspecialidade());
        psicologo.setTelefone(psicologoDTO.getTelefone());
        psicologo.setEmail(psicologoDTO.getEmail());
        
        psicologo = psicologoRepository.save(psicologo);
        return converterParaDTO(psicologo);
    }
    
    public void deletar(Long id) {
        if (!psicologoRepository.existsById(id)) {
            throw new EntityNotFoundException("Psicologo não encontrado com ID: " + id);
        }
        psicologoRepository.deleteById(id);
    }
    
    private PsicologoDTO converterParaDTO(Psicologo psicologo) {
        PsicologoDTO dto = new PsicologoDTO();
        dto.setId(psicologo.getId());
        dto.setNome(psicologo.getNome());
        dto.setNumeroRegistro(psicologo.getNumeroRegistro());
        dto.setEspecialidade(psicologo.getEspecialidade());
        dto.setTelefone(psicologo.getTelefone());
        dto.setEmail(psicologo.getEmail());
        return dto;
    }
    
    private Psicologo converterParaEntidade(PsicologoDTO dto) {
        Psicologo psicologo = new Psicologo();
        psicologo.setNome(dto.getNome());
        psicologo.setNumeroRegistro(dto.getNumeroRegistro());
        psicologo.setConselho(TypeConselho.CRP);
        psicologo.setEspecialidade(dto.getEspecialidade());
        psicologo.setTelefone(dto.getTelefone());
        psicologo.setEmail(dto.getEmail());
        return psicologo;
    }
}